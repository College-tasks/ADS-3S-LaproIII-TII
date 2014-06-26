package App;

import DAO.UsuarioDAO;
import Model.Usuario;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Simor, Daniel L.
 */
public class Login extends Application {
    private TextField txtLogin;
    private PasswordField txtSenha;
    private Button btnLogin;
    private GridPane grid;
    private StackPane root;
    private Stage stage;
    
    /**
     * Application Start
     * @param primaryStage Stage principal
     */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        initComponentes();
    }

    /**
     * Inicia os componentes
     * @param primaryStage Stage principal
     */
    private void initComponentes(){
        initButtons();
        initTexts();
        initApp();
    }
    
    /**
     * Inicia o App (Grid, StackPane, Scene)
     * @param primaryStage Stage principal
     */
    private void initApp(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtLogin, 0, 0);
        grid.add(txtSenha, 0, 1);
        grid.add(btnLogin, 0, 2);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        root.requestFocus();
        stage.setTitle("Efetue o Login!");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Inicializa os TextFields
     */
    private void initTexts(){
        this.txtLogin = new TextField();
        this.txtSenha = new PasswordField();
        
        // PromptText
        this.txtLogin.setPromptText("Login");
        this.txtSenha.setPromptText("Senha");
    }
    
    /**
     * Main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons(){
        this.btnLogin = new Button("Login");
        
        // Event Handler btnLogin
        this.btnLogin.setOnAction((ActionEvent event) -> {
            efetuaLogin(txtLogin.getText().trim(), txtSenha.getText().trim());
        });
    }
    
    /**
     * Tenta efetuar login
     * @param login Login do usuário
     * @param senha Senha do usuário
     */
    private void efetuaLogin(String login, String senha) {
        // Verifica os campos
        if (!verificaTextFields()) {
            return;
        }
        
        // Efetua login
        Usuario user = (new UsuarioDAO()).logIn(login, senha);
        if (user.getLogin()== null) {
            Utils.Utils.showMsg("Login ou senha incorretos!");
            return;
        }
        
        Global.Global.userLogado = user;
        // Mostra Stage
        if (user.getMatricula() == null) {
            new FuncionarioHome();
        }
        else {
            new HomeCliente();
        }
        
        stage.close();
    }
    
    /**
     * Verifica se os campos estão com valores válidos
     * @return Valido ou não
     */
    private boolean verificaTextFields(){
        // txtLogin
        if (txtLogin.getText().trim().length() == 0) {
            Utils.Utils.showMsg("Preencha o campo de login!");
            return false;
        }
                
        // txtSenha
        if (txtSenha.getText().trim().length() == 0) {
            Utils.Utils.showMsg("Preencha o campo de senha!");
            return false;
        }
                
        return true;
    }
}
