package App;

import DAO.CategoriaProdutoDAO;
import DAO.ProdutoDAO;
import Model.CategoriaProduto;
import Model.Produto;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Tela de cadastro de Produto
 * @author Simor / Daniel L.
 */
public class FuncionarioCadastroProduto extends Stage {
    private TextField txtNome, txtPreco;
    private ComboBox<String> ddlCategoria;
    private Button btnCadastra;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioCadastroProduto() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initTextFields();
        initButtons();
        initComboBox();
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
        this.txtNome.setPromptText("Nome");
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtNome, 0, 0);
        grid.add(ddlCategoria, 1, 0);
        grid.add(txtPreco, 0, 1);
        grid.add(btnCadastra, 0, 2);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Cadastro de Produto");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa a combobox
     */
    private void initComboBox() {
        ArrayList<CategoriaProduto> lstCategorias = new CategoriaProdutoDAO().selectAll();
        ObservableList lstExib = FXCollections.observableArrayList();
        
        for (CategoriaProduto item : lstCategorias) {
            lstExib.add(item.getNome());
        }
        
        ddlCategoria = new ComboBox(lstExib);
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnCadastra = new Button("Cadastrar");
        
        // Handler
        this.btnCadastra.setOnAction((ActionEvent event) -> {
            cadastra();
        });
    }
    
    /**
     * Tenta cadastrar um cliente
     */
    private void cadastra(){
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
        
        // Cadastra
        Produto prod = new Produto();
        prod.setNome(txtNome.getText());
        prod.setPreco(valor);
        prod.setIdCategoria(new CategoriaProdutoDAO().selectByNome(ddlCategoria.getSelectionModel().getSelectedItem().toString()).getId());
        
        if (new ProdutoDAO().insere(prod)) {
            Utils.Utils.showMsg("Cadastrado com sucesso!");
            this.close();
        }
        else {
            Utils.Utils.showMsg("Produto NÃO cadastrado!");
        }
    }
    
    /**
     * Verifica se os campos estão preenchidos
     * @return Campos válidos ou não
     */
    private boolean verificaCampos() {
        if (this.txtNome.getText().trim().equals("")) return false;
        if (this.ddlCategoria.getSelectionModel().getSelectedItem() == null) return false;
        return !this.txtPreco.getText().trim().equals("");
    }
}
