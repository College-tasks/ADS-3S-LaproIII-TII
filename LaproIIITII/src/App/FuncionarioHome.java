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
 * Home do Funcionário
 * @author Simor / Daniel L.
 */
public class FuncionarioHome extends Stage {
    private Button btnCadastrarCliente, btnCadastrarFuncionario, btnCadastrarProduto,
            btnEditarProduto, btnRealizarDeposito, btnRealizarVenda, btnRelatorios, btnAtualizaDados, btnLogDeposito;
    private Label lblInfos;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioHome() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initLabels();
        initButtons();
        initStage();
    }

    /**
     * Inicializa os Labels
     */
    private void initLabels() {
        this.lblInfos = new Label("Bem-vindo(a), " + Global.Global.userLogado.getLogin());
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
         // Grid
        this.grid = new GridPane();
        grid.add(lblInfos, 0, 0);
        grid.add(btnAtualizaDados, 0, 1);
        grid.add(btnCadastrarCliente, 0, 2);
        grid.add(btnCadastrarFuncionario, 0, 3);
        grid.add(btnCadastrarProduto, 0, 4);
        grid.add(btnEditarProduto, 0,5);
        grid.add(btnLogDeposito, 0, 6);
        grid.add(btnRealizarDeposito, 0, 7);
        grid.add(btnRealizarVenda, 0, 8);
        grid.add(btnRelatorios, 0, 9);
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
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnCadastrarCliente = new Button("Cadastrar Cliente");
        this.btnCadastrarFuncionario = new Button("Cadastrar Funcionário");
        this.btnCadastrarProduto = new Button("Cadastrar Produto");
        this.btnEditarProduto = new Button("Editar Produto");
        this.btnRealizarDeposito = new Button("Realizar Depósito");
        this.btnRealizarVenda = new Button("Realizar Venda");
        this.btnRelatorios = new Button("Relatórios");
        this.btnAtualizaDados = new Button("Atualizar dados");
        this.btnLogDeposito = new Button("Log de depósitos");
        
        // Handlers
        this.btnCadastrarCliente.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioCadastroCliente();
            this.grid.setDisable(false);
        });
        
        this.btnCadastrarFuncionario.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioCadastroFuncionario();
            this.grid.setDisable(false);
        });
        
        this.btnCadastrarProduto.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioCadastroProduto();
            this.grid.setDisable(false);
        });
        
        this.btnEditarProduto.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioEditaProduto();
            this.grid.setDisable(false);
        });
        
        this.btnRealizarDeposito.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioCriaDeposito();
            this.grid.setDisable(false);
        });
        
        this.btnRealizarVenda.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioCriaVenda();
            this.grid.setDisable(false);
        });
        
        this.btnRelatorios.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioRelatorios();
            this.grid.setDisable(false);
        });
        
        // AtualizaDados Handler
        this.btnAtualizaDados.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new ClienteAtualizaDados();
            this.grid.setDisable(false);
        });
        
        this.btnLogDeposito.setOnAction((ActionEvent event) -> {
            this.grid.setDisable(true);
            new FuncionarioHistoricoDeposito();
            this.grid.setDisable(false);
        });
    }
}
