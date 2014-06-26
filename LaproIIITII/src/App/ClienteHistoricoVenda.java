package App;

import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Usuario;
import Model.Venda;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de histórico de compras do Cliente
 * @author Simor / Daniel L.
 */
public class ClienteHistoricoVenda extends Stage {
    private ListView<String> lstVendas;
    private Button btnDetalhes;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public ClienteHistoricoVenda() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initLists();
        initButtons();
        initStage();
    }

    /**
     * Inicializa as Listas
     */
    private void initLists() {
        ArrayList<Venda> lstVenda = (new VendaDAO()).selectAllByUserId(Global.Global.userLogado.getId());
        ObservableList lstExib = FXCollections.observableArrayList();
        lstVendas = new ListView();
        
        // Popula ListView
        for (Venda item : lstVenda) {
            // Recupera Objetos
            Usuario func = (new UsuarioDAO()).selectById(item.getIdFuncionario());
            
            lstExib.add(item.getId() +
                    " -> " + func.getLogin() +
                    " - R$ " + item.getTotalVenda()+
                    " - " + item.getDataVenda());
        }
        
        lstVendas.setItems(lstExib);
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(lstVendas, 0, 0);
        grid.add(btnDetalhes, 0, 1);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Histórico de Vendas");
        this.setScene(scene);
        this.showAndWait();
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnDetalhes = new Button("Exibir detalhes");
        
        // Handler
        this.btnDetalhes.setOnAction((ActionEvent event) -> {
            String[] itemSplit = lstVendas.getSelectionModel().getSelectedItem().split("->");
            String item = itemSplit[0].trim();
            Global.Global.idVenda = Integer.parseInt(item);
            
            new ClienteDetalheVenda();
        });
    }
}
