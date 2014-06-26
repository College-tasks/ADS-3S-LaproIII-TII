package App;

import DAO.LogTransferenciaDAO;
import DAO.UsuarioDAO;
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
 * Tela de transferência do cliente
 * @author Simor / Daniel L.
 */
public class ClienteTransferencia extends Stage {
    private TextField txtMatricula, txtValor;
    private Button btnTransferir;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public ClienteTransferencia() {
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
        grid.add(btnTransferir, 0, 2);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Efetuar Transferência");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnTransferir = new Button("Transferir");
        
        // Handler
        this.btnTransferir.setOnAction((ActionEvent event) -> {
            transfere();
        });
    }
    
    /**
     * Tenta efetuar a transferência
     */
    private void transfere() {
        double valor;
        
        // Verifica se o valor é válido
        try {
            valor = Double.parseDouble(txtValor.getText());
        } catch (Exception e) {
            Utils.Utils.showMsg("Valor inválido!");
            return;
        }
        
        // Verifica se a matrícula é válida
        if (txtMatricula.getText().equals(Global.Global.userLogado.getMatricula())){
            Utils.Utils.showMsg("Matrícula inválida!");
            return;
        }
        
        if ((new UsuarioDAO()).selectByMatricula(txtMatricula.getText()).getMatricula() == null) {
            Utils.Utils.showMsg("Matrícula inválida!");
            return;
        }
        
        // Verifica se o saldo é maior que o valor digitado
        if (valor > new UsuarioDAO().selectById(Global.Global.userLogado.getId()).getSaldo()) {
            Utils.Utils.showMsg("Você não possui saldo suficiente!");
            return;
        }
        
        // Transferir
        int idRemetente = Global.Global.userLogado.getId();
        int idDestinatario = new UsuarioDAO().selectByMatricula(txtMatricula.getText()).getId();
        if (new LogTransferenciaDAO().transfere(idRemetente, idDestinatario, valor)) {            
            Utils.Utils.showMsg("Transferencia efetuada com sucesso!");
            Global.Global.userLogado.setSaldo(Global.Global.userLogado.getSaldo() - valor);
            this.close();
        } else {
            Utils.Utils.showMsg("Não transferido!");
        }
    }
}
