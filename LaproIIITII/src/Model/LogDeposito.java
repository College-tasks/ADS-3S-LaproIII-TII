package Model;

/**
 * Classe modelo de LogDeposito
 * @author Simor / Daniel L.
 */
public class LogDeposito {
    private int id;
    private int idFuncionario;
    private int idCliente;
    private double valor;
    private String dataDeposito;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idFuncionario
     */
    public int getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * @param idFuncionario the idFuncionario to set
     */
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the dataDeposito
     */
    public String getDataDeposito() {
        return dataDeposito;
    }

    /**
     * @param dataDeposito the dataDeposito to set
     */
    public void setDataDeposito(String dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
