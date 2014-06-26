package App;

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
 * Tela de cadastro de Cliente
 * @author Simor / Daniel L.
 */
public class FuncionarioCadastroCliente extends Stage {
    private TextField txtMatricula, txtEmail, txtLogin;
    private Button btnCadastra;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioCadastroCliente() {
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
        this.txtEmail = new TextField();
        this.txtLogin = new TextField();
        
        // PrompText
        this.txtMatricula.setPromptText("Matrícula");
        this.txtEmail.setPromptText("Email");
        this.txtLogin.setPromptText("Login");
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtMatricula, 0, 0);
        grid.add(txtEmail, 0, 1);
        grid.add(txtLogin, 0, 2);
        grid.add(btnCadastra, 0, 3);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Cadastro de Cliente");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnCadastra = new Button("Cadastrar");
        
        // Handler
        this.btnCadastra.setOnAction((ActionEvent event) -> {
            cadastraCliente();
        });
    }
    
    /**
     * Tenta cadastrar um cliente
     */
    private void cadastraCliente(){
        // Verifica
        if (!verificaCampos()) {
            Utils.Utils.showMsg("Preencha todos campos!");
            return;
        }
        if (!verificaDados()) return;
        
        // Cadastra
        Usuario user = new Usuario();
        user.setEmail(txtEmail.getText());
        user.setLogin(txtLogin.getText());
        user.setMatricula(txtMatricula.getText());
        user.setSaldo(0);
        user.setSenha("senha");
        
        if (new UsuarioDAO().insere(user)) {
            Utils.Utils.showMsg("Cadastrado com sucesso!");
            this.close();
        }
        else {
            Utils.Utils.showMsg("Cliente NÃO cadastrado!");
        }
    }
    
    /**
     * Verifica se os campos estão preenchidos
     * @return Campos válidos ou não
     */
    private boolean verificaCampos() {
        if (this.txtEmail.getText().trim().equals("")) return false;
        if (this.txtLogin.getText().trim().equals("")) return false;
        return !this.txtMatricula.getText().trim().equals("");
    }
    
    /**
     * Verifica se os dados são válidos e únicos
     * @return Dados válidos ou não
     */
    private boolean verificaDados() {
        // Login
        Usuario user = new UsuarioDAO().selectByLogin(txtLogin.getText());
        if (user.getLogin() != null) {
            Utils.Utils.showMsg("Login já está em uso!");
            return false;
        }
        
        // Matrícula
        user = new UsuarioDAO().selectByMatricula(txtMatricula.getText());
        if (user.getMatricula() != null) {
            Utils.Utils.showMsg("Matrícula já está em uso!");
            return false;
        }
        
        // Email
        user = new UsuarioDAO().selectByEmail(txtEmail.getText());
        if (user.getEmail() != null) {
            Utils.Utils.showMsg("Email já está em uso!");
            return false;
        }
        
        return true;
    }
}
