package App;

import DAO.LogDepositoDAO;
import DAO.UsuarioDAO;
import Model.LogDeposito;
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
 * Histórico de depósitos
 * @author Simor / Daniel L.
 */
public class FuncionarioHistoricoDeposito extends Stage  {
    private ListView<String> lstHistorico;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioHistoricoDeposito() {
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
        ArrayList<LogDeposito> lstTransf = (new LogDepositoDAO()).selectAll();
        ObservableList lstExib = FXCollections.observableArrayList();
        lstHistorico = new ListView();
        
        // Popula ListView
        for (LogDeposito item : lstTransf) {
            // Recupera Objeto
            Usuario user1 = (new UsuarioDAO()).selectById(item.getIdFuncionario());
            Usuario user2 = (new UsuarioDAO()).selectById(item.getIdCliente());
            
            lstExib.add(user1.getLogin() +
                    " -> " + user2.getLogin() +
                    " - R$ " + item.getValor()+
                    " - " + item.getDataDeposito());
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
        this.setTitle("Histórico de Depósitos");
        this.setScene(scene);
        this.showAndWait();
    }
}
