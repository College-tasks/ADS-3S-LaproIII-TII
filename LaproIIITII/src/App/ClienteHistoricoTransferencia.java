package App;

import DAO.LogTransferenciaDAO;
import DAO.UsuarioDAO;
import Model.LogTransferencia;
import Model.Usuario;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de histórico de transferências do cliente
 * @author Simor / Daniel L.
 */
public class ClienteHistoricoTransferencia extends Stage {
    private ListView<String> lstHistorico;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public ClienteHistoricoTransferencia() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initLists();
        initStage();
    }

    /**
     * Inicializa as Listas
     */
    private void initLists() {
        ArrayList<LogTransferencia> lstTransf = (new LogTransferenciaDAO()).selectAllByUserId(Global.Global.userLogado.getId());
        ObservableList lstExib = FXCollections.observableArrayList();
        lstHistorico = new ListView();
        
        // Popula ListView
        for (LogTransferencia item : lstTransf) {
            // Recupera Objeto
            Usuario user1 = (new UsuarioDAO()).selectById(item.getIdRemetente());
            Usuario user2 = (new UsuarioDAO()).selectById(item.getIdDestinatario());
            
            lstExib.add(user1.getLogin() +
                    " -> " + user2.getLogin() +
                    " - R$ " + item.getValor()+
                    " - " + item.getDataTransferencia());
        }
        
        lstHistorico.setItems(lstExib);
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(lstHistorico, 0, 0);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 300, 250);
        
        this.root.requestFocus();
        this.setTitle("Histórico de Transferências");
        this.setScene(scene);
        this.showAndWait();
    }
}
