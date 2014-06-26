package App;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Home do cliente
 * @author Simor / Daniel L.
 */
public class HomeCliente extends Stage {
    private Button btnTransferencia, btnHistoricoVenda, btnHistoricoTransferencia, btnAtualizaDados, btnLogoff;
    private Label lblSaldo, lblInfo;
    private GridPane grid;
    private StackPane root;
    
    /**
     * Construtor
     */
    public HomeCliente() {
        initComponentes();
    }
    
    /**
     * Inicia os componentes
     */
    private void initComponentes(){
        initButtons();
        initLabels();
        initStage();
    }
    
    /**
     * Inicia o Stage (Grid, StackPane, Scene)
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(lblInfo, 0, 0);
        grid.add(lblSaldo, 0, 1);
        grid.add(btnAtualizaDados, 0, 2);
        grid.add(btnTransferencia, 0, 3);
        grid.add(btnHistoricoTransferencia, 0, 4);
        grid.add(btnHistoricoVenda, 0, 5);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 350);
        
        this.root.requestFocus();
        this.setTitle("Home");
        this.setScene(scene);
        this.show();
    }
    
    /**
     * Inicializa os Labels
     */
    private void initLabels(){
        // Recupera o saldo do banco e insere no Label
        lblSaldo = new Label("Saldo: R$ " + Global.Global.userLogado.getSaldo());
        lblInfo = new Label("Bem-vindo(a), " + Global.Global.userLogado.getLogin());
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons(){
        this.btnAtualizaDados = new Button("Atualizar Dados");
        this.btnHistoricoTransferencia = new Button("Histórico de Transferências");
        this.btnHistoricoVenda = new Button("Histórico de Vendas");
        this.btnTransferencia = new Button("Efetuar transferência");
        
        // AtualizaDados Handler
        this.btnAtualizaDados.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new ClienteAtualizaDados();
            this.grid.setDisable(false);
        });
        
        // HistoricoTransferencia Handler
        this.btnHistoricoTransferencia.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new ClienteHistoricoTransferencia();
            this.grid.setDisable(false);
        });
        
        // HistoricoVenda Handler
        this.btnHistoricoVenda.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new ClienteHistoricoVenda();
            this.grid.setDisable(false);
        });
        
        // Transferencia Handler
        this.btnTransferencia.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new ClienteTransferencia();
            this.lblSaldo.setText("Saldo: R$ " + Global.Global.userLogado.getSaldo());
            this.grid.setDisable(false);
        });
    }
}
