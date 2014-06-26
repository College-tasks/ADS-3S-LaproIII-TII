package Model;

/**
 * Classe modelo de LogTransferencia
 * @author Simor / Daniel L.
 */
public class LogTransferencia {
    private int id;
    private int idRemetente;
    private int idDestinatario;
    private double valor;
    private String dataTransferencia;

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
     * @return the idRemetente
     */
    public int getIdRemetente() {
        return idRemetente;
    }

    /**
     * @param idRemetente the idRemetente to set
     */
    public void setIdRemetente(int idRemetente) {
        this.idRemetente = idRemetente;
    }

    /**
     * @return the idDestinatario
     */
    public int getIdDestinatario() {
        return idDestinatario;
    }

    /**
     * @param idDestinatario the idDestinatario to set
     */
    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
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
     * @return the dataTransferencia
     */
    public String getDataTransferencia() {
        return dataTransferencia;
    }

    /**
     * @param dataTransferencia the dataTransferencia to set
     */
    public void setDataTransferencia(String dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
}
