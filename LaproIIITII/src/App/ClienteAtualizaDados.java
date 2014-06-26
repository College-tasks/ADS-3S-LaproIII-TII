package App;

import DAO.UsuarioDAO;
import Model.Usuario;
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
 * Tela de atualização de dados do Cliente
 * @author Simor / Daniel L.
 */
public class ClienteAtualizaDados extends Stage {
    private TextField txtEmail;
    private PasswordField txtNovaSenha;
    private Button btnAtualizar;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public ClienteAtualizaDados() {
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
        this.txtEmail = new TextField();
        this.txtNovaSenha = new PasswordField();
        
        // PromptText
        this.txtEmail.setPromptText("Email");
        this.txtNovaSenha.setPromptText("Nova Senha");
        
        // Valores
        this.txtEmail.setText(Global.Global.userLogado.getEmail());
        this.txtNovaSenha.setText(Global.Global.userLogado.getSenha());
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtEmail, 0, 0);
        grid.add(txtNovaSenha, 0, 1);
        grid.add(btnAtualizar, 0, 2);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Atualizar Dados");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnAtualizar = new Button("Atualizar");
        
        // btnAtualizar Handler
        this.btnAtualizar.setOnAction((ActionEvent event) -> {
            atualizaUsuario();
        });
    }
    
    /**
     * Atualiza o usuário
     */
    private void atualizaUsuario(){
        if (!verificaTexts()) {
            Utils.Utils.showMsg("Atualização NÃO concluída!");
            return;
        }
        
        // Cria objeto para atualização
        int idUser = Global.Global.userLogado.getId();
        Usuario user = new Usuario();
        user.setEmail(txtEmail.getText().trim());
        user.setId(idUser);
        user.setLogin(Global.Global.userLogado.getLogin());
        user.setMatricula(Global.Global.userLogado.getMatricula());
        user.setSenha(txtNovaSenha.getText().trim());
        user.setSaldo(Global.Global.userLogado.getSaldo());
        
        // Atualiza
        if ((new UsuarioDAO()).edita(user, idUser)) {
            Utils.Utils.showMsg("Atualizado com sucesso!");
            Global.Global.userLogado.setEmail(txtEmail.getText());
            Global.Global.userLogado.setSenha(txtNovaSenha.getText());
            this.close();
        } else {
            Utils.Utils.showMsg("Não atualizado!");
        }
    }
    
    /**
     * Verifica os TextFields
     * @return Validade da operação
     */
    private boolean verificaTexts(){
        if (txtEmail.getText().trim().length() == 0) return false;
        if (txtNovaSenha.getText().trim().length() == 0) return false;
        
        // Verifica se Email já existe
        if (txtEmail.getText().toLowerCase().equals(Global.Global.userLogado.getEmail().toLowerCase())) return true;
        Usuario user = (new UsuarioDAO()).selectByEmail(txtEmail.getText().trim());
        if (user.getLogin() != null) {
            Utils.Utils.showMsg("Email já em uso!");
            return false;
        }
        
        return true;
    }
}
