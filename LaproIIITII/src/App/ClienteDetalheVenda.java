package App;

import DAO.ProdutoDAO;
import DAO.ProdutoVendaDAO;
import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Produto;
import Model.ProdutoVenda;
import Model.Venda;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de detalhes da venda selecionada
 * @author Simor / Daniel L.
 */
public class ClienteDetalheVenda extends Stage {
    private Label lblIdVenda, lblDataVenda, lblNomeVendedor;
    private ListView<String> lstDetalhes;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    * @param idVenda Id da Venda
    */
    public ClienteDetalheVenda() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initLabels();
        initLists();
        initStage();
    }

    /**
     * Inicializa as Listas
     */
    private void initLists() {
        ArrayList<ProdutoVenda> lstProdutos = (new ProdutoVendaDAO()).selectAllByVendaId(Global.Global.idVenda);
        Venda venda = (new VendaDAO()).selectById(Global.Global.idVenda);
        ObservableList lstExib = FXCollections.observableArrayList();
        lstDetalhes = new ListView();
        
        // Recupera dados da venda
        lblDataVenda.setText("Data da Venda: " + venda.getDataVenda());
        lblNomeVendedor.setText("Vendedor: " + (new UsuarioDAO()).selectById(venda.getIdFuncionario()).getLogin());
        lblIdVenda.setText(String.valueOf(Global.Global.idVenda));
        
        // Popula ListView
        for (ProdutoVenda item : lstProdutos) {
            // Recupera Objeto
            Produto obj = new ProdutoDAO().selectById(item.getIdProduto());
            lstExib.add(item.getIdProduto() +
                    " - " + obj.getNome() +
                    " - Qtd: " + item.getQuantidade() +
                    " - P.Un.: R$ " + item.getPreco());
        }
        
        lstDetalhes.setItems(lstExib);
    }

    /**
     * Inicializa os Labels
     */
    private void initLabels() {
        this.lblDataVenda = new Label("");
        this.lblIdVenda = new Label("");
        this.lblNomeVendedor = new Label("");
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(lblIdVenda, 0, 0);
        grid.add(lblDataVenda, 0, 1);
        grid.add(lblNomeVendedor, 0, 2);
        grid.add(lstDetalhes, 0, 3);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Detalhes da Venda");
        this.setScene(scene);
        this.showAndWait();
    }
}
