package DAO;

import Model.LogTransferencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class LogTransferenciaDAO extends DAOSuper {

    /**
     * Construtor
     */
    public LogTransferenciaDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param idRemetente Id do remetente
     * @param idDestinatario Id do Destinatário
     * @param valor Valor a ser depositado
     * @return Sucesso ou falha da operação
     */
    public boolean transfere(int idRemetente, int idDestinatario, double valor) {
        conecta();
        int linhas = 0;

        // Cria Query
        String query = "CALL lp3_transfere_cliente("+idRemetente+", "+idDestinatario+", "+valor+");";

        // Executa
        try {
            linhas = cmd.executeUpdate(query);
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
        } finally {
            desconecta();
        }

        return linhas > 0;
    }

    /**
     * Seleciona todas linhas do banco de dados
     *
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<LogTransferencia> selectAll() {
        conecta();
        ArrayList<LogTransferencia> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idRemetente, idDestinatario, valor, dataTransferencia FROM lp3_LogTransferencia ORDER BY id ASC";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                LogTransferencia t = new LogTransferencia();
                t.setId(set.getInt("Id"));
                t.setIdRemetente(set.getInt("IdRemetente"));
                t.setIdDestinatario(set.getInt("Destinatario"));
                t.setValor(set.getDouble("Valor"));
                t.setDataTransferencia(set.getString("DataTransferencia"));

                ret.add(t);
            }
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
        } finally {
            desconecta();
        }

        // Retorna
        return ret;
    }

    /**
     * Seleciona a linha com o Id escolhido
     *
     * @param id ID da linha
     * @return Objeto populado (Caso encontrado)
     */
    public LogTransferencia selectById(int id) {
        conecta();
        LogTransferencia ret = new LogTransferencia();

        // Cria Query
        String query = "SELECT id, idRemetente, idDestinatario, valor, dataTransferencia FROM lp3_LogTransferencia WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setIdRemetente(set.getInt("IdRemetente"));
                ret.setIdDestinatario(set.getInt("Destinatario"));
                ret.setValor(set.getDouble("Valor"));
                ret.setDataTransferencia(set.getString("DataTransferencia"));

            }
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
        } finally {
            desconecta();
        }

        // Retorna
        return ret;
    }
    
    /**
     * Seleciona todas linhas do banco de dados do Usuário
     *
     * @param id Id do usuário
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<LogTransferencia> selectAllByUserId(int id) {
        conecta();
        ArrayList<LogTransferencia> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idRemetente, idDestinatario, valor, dataTransferencia FROM lp3_LogTransferencia WHERE idRemetente = "+id+" OR idDestinatario = "+id+" ORDER BY id ASC";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                LogTransferencia t = new LogTransferencia();
                t.setId(set.getInt("Id"));
                t.setIdRemetente(set.getInt("idRemetente"));
                t.setIdDestinatario(set.getInt("idDestinatario"));
                t.setValor(set.getDouble("Valor"));
                t.setDataTransferencia(set.getString("DataTransferencia"));

                ret.add(t);
            }
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
        } finally {
            desconecta();
        }

        // Retorna
        return ret;
    }
}
