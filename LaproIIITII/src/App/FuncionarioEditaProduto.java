package App;

import DAO.CategoriaProdutoDAO;
import DAO.ProdutoDAO;
import Model.CategoriaProduto;
import Model.Produto;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de edição de produto
 * @author Simor / Daniel L.
 */
public class FuncionarioEditaProduto extends Stage {
    private TextField txtNome, txtPreco;
    private ComboBox<String> ddlCategoria, ddlProduto;
    private CheckBox chkAtivo;
    private Button btnEditar;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioEditaProduto() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initTextFields();
        initButtons();
        initComboBoxes();
        initCheckBox();
        initStage();
    }

    /**
     * Inicializa os TextFields
     */
    private void initTextFields() {
        this.txtPreco = new TextField();
        this.txtNome = new TextField();
        
        // PrompText
        this.txtPreco.setPromptText("Preço");
        this.txtNome.setPromptText("Nome Produto");
    }

    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(ddlProduto, 0, 0);
        grid.add(txtNome, 0, 1);
        grid.add(ddlCategoria, 1, 1);
        grid.add(txtPreco, 0, 2);
        grid.add(chkAtivo, 0, 3);
        grid.add(btnEditar, 0, 4);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Editar Produto");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Checkboxes
     */
    private void initCheckBox() {
        this.chkAtivo = new CheckBox("Ativo");
    }
    
    /**
     * Inicializa a combobox
     */
    private void initComboBoxes() {
        // Categorias
        ArrayList<CategoriaProduto> lstCategorias = new CategoriaProdutoDAO().selectAll();
        ObservableList lstExib = FXCollections.observableArrayList();
        
        for (CategoriaProduto item : lstCategorias) {
            lstExib.add(item.getNome());
        }
        
        ddlCategoria = new ComboBox(lstExib);
        
        // Produtos
        ArrayList<Produto> lstProdutos = new ProdutoDAO().selectAll();
        lstExib = FXCollections.observableArrayList();
        
        for (Produto item : lstProdutos) {
            lstExib.add(item.getId() + "#" + item.getNome());
        }
        
        ddlProduto = new ComboBox(lstExib);
        ddlProduto.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String[] idString = t1.split("#");
                int id = Integer.parseInt(idString[0]);
                
                Produto prod = new ProdutoDAO().selectById(id);
                txtNome.setText(prod.getNome());
                txtPreco.setText(String.valueOf(prod.getPreco()));
                ddlCategoria.setValue(new CategoriaProdutoDAO().selectById(prod.getIdCategoria()).getNome());
                chkAtivo.setSelected(prod.isAtivo());
            }    
        });
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnEditar = new Button("Editar");
        
        // Handler
        this.btnEditar.setOnAction((ActionEvent event) -> {
            edita();
        });
    }
    
    /**
     * Tenta Editar um Produto
     */
    private void edita(){
        double valor;
        // Verifica se o valor é válido
        try {
            valor = Double.parseDouble(txtPreco.getText());
        } catch (Exception e) {
            Utils.Utils.showMsg("Preço inválido!");
            return;
        }
        
        // Verifica
        if (!verificaCampos()) {
            Utils.Utils.showMsg("Preencha todos campos!");
            return;
        }
        
        // Edita
        String[] idSplit = ddlProduto.getSelectionModel().getSelectedItem().split("#");
        int id = Integer.parseInt(idSplit[0]);
        
        Produto prod = new Produto();
        prod.setNome(txtNome.getText());
        prod.setPreco(valor);
        prod.setIdCategoria(new CategoriaProdutoDAO().selectByNome(ddlCategoria.getSelectionModel().getSelectedItem().toString()).getId());
        prod.setAtivo(chkAtivo.isSelected());
        
        if (new ProdutoDAO().update(prod, id)) {
            Utils.Utils.showMsg("Editado com sucesso!");
            this.close();
        }
        else {
            Utils.Utils.showMsg("Produto NÃO Editado!");
        }
    }
    
    /**
     * Verifica se os campos estão preenchidos
     * @return Campos válidos ou não
     */
    private boolean verificaCampos() {
        if (this.txtNome.getText().trim().equals("")) return false;
        if (this.ddlCategoria.getSelectionModel().getSelectedItem() == null) return false;
        if (this.ddlProduto.getSelectionModel().getSelectedItem() == null) return false;
        return !this.txtPreco.getText().trim().equals("");
    }
}
