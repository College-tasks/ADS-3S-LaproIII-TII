package App;

import DAO.ProdutoDAO;
import DAO.ProdutoVendaDAO;
import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Produto;
import Model.ProdutoVenda;
import Model.Usuario;
import Model.Venda;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de Venda
 * @author Simor / Daniel L.
 */
public class FuncionarioCriaVenda extends Stage {
    private TextField txtMatricula;
    private ComboBox cbbProdutos, cbbQuantidade;
    private ListView<String> lstCarrinho;
    private ArrayList<ProdutoVenda> lstProdutosCarrinho;
    private Label lblTotal;
    private Button btnVender, btnAdicionar;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioCriaVenda() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initTextFields();
        initLabels();
        initButtons();
        initList();
        initCombo();
        initStage();
    }

    /**
     * Inicializa os TextFields
     */
    private void initTextFields() {
        this.txtMatricula = new TextField();
        
        // PrompText
        this.txtMatricula.setPromptText("Matrícula");
    }

    /**
     * Inicializa os Labels
     */
    private void initLabels() {
        this.lblTotal = new Label("Total: R$ 0");
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(txtMatricula, 0, 0);
        grid.add(cbbProdutos, 0, 1);
        grid.add(cbbQuantidade, 0, 2);
        grid.add(btnAdicionar, 0, 3);
        grid.add(lstCarrinho, 0, 4);
        grid.add(lblTotal, 0, 5);
        grid.add(btnVender, 0, 6);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 400);
        
        this.root.requestFocus();
        this.setTitle("Vender produtos");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa o ListView e ArrayList
     */
    private void initList(){
        this.lstCarrinho = new ListView();
        this.lstProdutosCarrinho = new ArrayList();
    }
    
    private void initCombo(){
        // Produtos
        ArrayList<Produto> lstProdutos = new ProdutoDAO().selectAllActive();
        ObservableList lstExib = FXCollections.observableArrayList();
        
        for (Produto item : lstProdutos) {
            lstExib.add(item.getId() + "#" + item.getNome());
        }
        
        cbbProdutos = new ComboBox(lstExib);
        cbbProdutos.getSelectionModel().select(0);
        
        // Quantidade
        lstExib = FXCollections.observableArrayList();
        for (int i = 1; i < 50; i++) {
            lstExib.add(String.valueOf(i));
        }
        
        cbbQuantidade = new ComboBox(lstExib);
        cbbQuantidade.getSelectionModel().select(0);
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnAdicionar = new Button("Adicionar Produto");
        this.btnVender = new Button("Efetuar venda");
        
        // Handler
        this.btnAdicionar.setOnAction((ActionEvent event) -> {
            adicionaProduto();
        });
        
        this.btnVender.setOnAction((ActionEvent event) -> {
            efetuaVenda();
        });
    }
    
    /**
     * Adiciona um produto na lista de produtos para vender
     */
    private void adicionaProduto() {
        double total = 0;
        String produtoS = cbbProdutos.getSelectionModel().getSelectedItem().toString();
        String qtdS = cbbQuantidade.getSelectionModel().getSelectedItem().toString();
        
        // Cria ProdutoVenda
        int qtd = Integer.parseInt(qtdS);
        ProdutoVenda prod = new ProdutoVenda();
        prod.setIdProduto(Integer.parseInt(produtoS.split("#")[0]));
        prod.setPreco(new ProdutoDAO().selectById(prod.getIdProduto()).getPreco());
        prod.setQuantidade(qtd);
        
        // Adiciona na lista
        this.lstProdutosCarrinho.add(prod);
        
        // Recarrega ListView
        ObservableList lstExib = FXCollections.observableArrayList();
        
        for (ProdutoVenda item : lstProdutosCarrinho) {
            lstExib.add(new ProdutoDAO().selectById(item.getIdProduto()).getNome() + 
                    " -> " + item.getQuantidade() + " - R$ " + item.getQuantidade() * item.getPreco());
        }
        
        this.lstCarrinho.setItems(lstExib);
        
        // Atualiza o total
        for (ProdutoVenda item : this.lstProdutosCarrinho){
           total += item.getPreco() * item.getQuantidade();
        }
        lblTotal.setText("Total: R$ " + total);
    }
    
    /**
     * Tenta efetuar a venda
     */
    private void efetuaVenda(){
        Usuario user;
        double total = 0;
        
        // Verifica se existem itens no carrinho
        if (this.lstProdutosCarrinho.size() < 1) {
            Utils.Utils.showMsg("Nenhum produto selecionado!");
            return;
        }
        
        // Verifica matrícula
        if (txtMatricula.getText().trim().equals("")) {
            Utils.Utils.showMsg("Matrícula vazia!");
            return;
        }
        
        user = new UsuarioDAO().selectByMatricula(txtMatricula.getText().trim());
        if (user.getMatricula() == null) {
            Utils.Utils.showMsg("Matrícula inválida!");
            return;
        }
        
        // Verifica se usuário possui saldo
        for (ProdutoVenda item : this.lstProdutosCarrinho){
           total += item.getPreco() * item.getQuantidade();
        }
        
        if (user.getSaldo() < total) {
            Utils.Utils.showMsg("Usuário não possui saldo suficiente!");
            return;
        }
        
        // Efetua venda
        Venda venda = new Venda();
        venda.setTotalVenda(total);
        venda.setIdFuncionario(Global.Global.userLogado.getId());
        venda.setIdCliente(user.getId());
        
        // Data
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        venda.setDataVenda(sdf.format(data));
        
        if (new VendaDAO().insere(venda, lstProdutosCarrinho)){
            Utils.Utils.showMsg("Venda efetuada com sucesso!");
            this.close();
        } else {
            Utils.Utils.showMsg("Venda não efetuada!");
        }
    }
}
