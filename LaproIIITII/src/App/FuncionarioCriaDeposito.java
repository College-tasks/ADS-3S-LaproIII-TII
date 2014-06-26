package App;

import DAO.LogDepositoDAO;
import DAO.UsuarioDAO;
import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela para realizar um depósito para um cliente
 * @author Simor / Daniel L.
 */
public class FuncionarioCriaDeposito extends Stage {
    private TextField txtMatricula, txtValor;
    private Button btnDepositar;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioCriaDeposito() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initTextFields();
        initButtons();
        initStage();
    }

    /**
     * Inicializa os TextFields
     */
    private void initTextFields() {
        this.txtMatricula = new TextField();
        this.txtValor = new TextField();
        
        // PrompText
        this.txtMatricula.setPromptText("Matrícula");
        this.txtValor.setPromptText("Valor");
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtMatricula, 0, 0);
        grid.add(txtValor, 0, 1);
        grid.add(btnDepositar, 0, 2);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Efetua Depósito");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnDepositar = new Button("Depositar");
        
        // Handler
        this.btnDepositar.setOnAction((ActionEvent event) -> {
            deposita();
        });
    }
    
    private void deposita() {
        double valor;
        
        // Verifica se o valor é válido
        try {
            valor = Double.parseDouble(txtValor.getText());
        } catch (Exception e) {
            Utils.Utils.showMsg("Valor inválido!");
            return;
        }
        
        // Verifica campos
        if (!verificaCampos()) {
            Utils.Utils.showMsg("Preencha todos campos!");
            return;
        }
        if (!verificaDados()) return;
        
        // Deposita
        int idCliente = new UsuarioDAO().selectByMatricula(txtMatricula.getText()).getId();
        if (new LogDepositoDAO().transfere(Global.Global.userLogado.getId(), idCliente, valor)) {
            Utils.Utils.showMsg("Depósito efetuado com sucesso!");
            this.close();
        } else {
            Utils.Utils.showMsg("Não depositado!");
        }
    }
    
    /**
     * Verifica se os campos estão preenchidos
     * @return Campos válidos ou não
     */
    private boolean verificaCampos() {
        if (this.txtValor.getText().trim().equals("")) return false;
        return !this.txtMatricula.getText().trim().equals("");
    }
    
    /**
     * Verifica se os dados são válidos e únicos
     * @return Dados válidos ou não
     */
    private boolean verificaDados() {
        // Matrícula
        Usuario user = new UsuarioDAO().selectByMatricula(txtMatricula.getText());
        user = new UsuarioDAO().selectByMatricula(txtMatricula.getText());
        if (user.getMatricula() == null) {
            Utils.Utils.showMsg("Matrícula inválida!");
            return false;
        }
        
        return true;
    }
}
