package App;

import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Tela de relatórios
 * @author Simor / Daniel L.
 */
public class FuncionarioRelatorios extends Stage {
    private ComboBox<String> ddlCliente, ddlFuncionario;
    private PieChart tortaMes, tortaTudo;
    private Button btnGerar;
    private GridPane grid;
    private StackPane root;
    
    /**
    * Construtor
    */
    public FuncionarioRelatorios() {
        initComponentes();
    }
    
    /**
    * Inicia os componentes
    */
    private void initComponentes(){
        initButtons();
        initComboBox();
        loadCharts();
        initStage();
    }
    
    /**
     * Inicializa o Stage
     */
    private void initStage(){
        // Grid
        this.grid = new GridPane();
        grid.add(ddlFuncionario, 0, 0);
        grid.add(ddlCliente, 0, 1);
        grid.add(btnGerar, 0, 2);
        grid.add(tortaMes, 1, 3);
        grid.add(tortaTudo, 0, 3);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        // Root
        root = new StackPane();
        root.getChildren().add(grid);
        
        // Scene
        Scene scene = new Scene(root, 450, 400);
        
        this.root.requestFocus();
        this.setTitle("Relatórios");
        this.setScene(scene);
        this.showAndWait();
    }
    
    private void initComboBox() {
        this.ddlCliente = new ComboBox();
        this.ddlFuncionario = new ComboBox();
        
        // Popula ComboBox Cliente e Funcionários
        ArrayList<Usuario> lstUsuarios = new UsuarioDAO().selectAll();
        ObservableList lstExibFunc = FXCollections.observableArrayList();
        ObservableList lstExibCli = FXCollections.observableArrayList();
        
        lstExibCli.add("0#Todos Clientes");
        lstExibFunc.add("0#Todos Funcionários");
        
        // Separa Clientes e Funcionários
        lstUsuarios.stream().map((item) -> {
            if (item.getMatricula() == null) lstExibFunc.add(item.getId() + "#" + item.getLogin());
            return item;
        }).filter((item) -> (item.getMatricula() != null)).forEach((item) -> {
            lstExibCli.add(item.getId() + "#" + item.getLogin());
        });
        
        // Popula
        ddlCliente = new ComboBox(lstExibCli);
        ddlFuncionario = new ComboBox(lstExibFunc);
        
        // Seleciona primeira opção
        ddlCliente.getSelectionModel().select(0);
        ddlFuncionario.getSelectionModel().select(0);
    }
    
    /**
     * Carrega os Charts
     */
    private void loadCharts() {
        // Data
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String dataS = sdf.format(data);
        
        double total = new VendaDAO().getTotal(0, 0, null);
        double totalMes = new VendaDAO().getTotal(0, 0, dataS);
        
        // Total
        ObservableList<PieChart.Data> tortaTotal =
                FXCollections.observableArrayList(
                new PieChart.Data("Total: " + total, 100));
        this.tortaTudo = new PieChart(tortaTotal);
        this.tortaTudo.setTitle("Total Geral");
        
        // Total Mês
        ObservableList<PieChart.Data> tortaTotalMes =
                FXCollections.observableArrayList(
                new PieChart.Data("Total: " + totalMes, 100));
        this.tortaMes = new PieChart(tortaTotalMes);
        this.tortaMes.setTitle("Total Mês");
    }
    
    /**
     * Inicializa os Buttons
     */
    private void initButtons() {
        this.btnGerar = new Button("Gerar");
        
        // Handler
        this.btnGerar.setOnAction((ActionEvent event) -> {
            geraCharts();
        });
    }
    
    /**
     * Gera os gráficos de acordo com as opções escolhidas
     */
    private void geraCharts() {
        int func = Integer.parseInt(ddlFuncionario.getSelectionModel().getSelectedItem().split("#")[0]);
        int cli = Integer.parseInt(ddlCliente.getSelectionModel().getSelectedItem().split("#")[0]);
        
        // Data
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String dataS = sdf.format(data);
        
        // Totais
        double selecionadoGeral = new VendaDAO().getTotal(func, cli, null);
        double selecionadoMes = new VendaDAO().getTotal(func, cli, dataS);
        
        // Popula gráficos
        if (!(func == 0 && cli == 0)) {
            double totalGeral = new VendaDAO().getTotal(0, 0, null);
            double totalMes = new VendaDAO().getTotal(0, 0, dataS);
            double pGeral = (selecionadoGeral * 100) / totalGeral;
            double pMes = (selecionadoMes * 100) / totalMes;
            
            // Total
            ObservableList<PieChart.Data> tortaTotal = FXCollections.observableArrayList(
                    new PieChart.Data("Selecionado: " + selecionadoGeral, pGeral),
                    new PieChart.Data("Outros: " + (totalGeral - selecionadoGeral), 100 - pGeral));
            this.tortaTudo.setData(tortaTotal);
            this.tortaTudo.setTitle("Total Geral");

            // Total Mês
            ObservableList<PieChart.Data> tortaTotalMes = FXCollections.observableArrayList(
                    new PieChart.Data("Selecionado: " + selecionadoMes, pMes),
                    new PieChart.Data("Outros: " + (totalMes - selecionadoMes), 100 - pMes));
            this.tortaMes.setData(tortaTotalMes);
            this.tortaMes.setTitle("Total Mês");
        }
        else {
            // Total
            ObservableList<PieChart.Data> tortaTotal = FXCollections.observableArrayList(
                    new PieChart.Data("Total: " + selecionadoGeral, 100));
            this.tortaTudo.setData(tortaTotal);
            this.tortaTudo.setTitle("Total Geral");

            // Total Mês
            ObservableList<PieChart.Data> tortaTotalMes = FXCollections.observableArrayList(
                    new PieChart.Data("Total: " + selecionadoMes, 100));
            this.tortaMes.setData(tortaTotalMes);
            this.tortaMes.setTitle("Total Mês");
        }
    }
}
