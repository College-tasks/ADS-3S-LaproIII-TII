package DAO;

import Model.LogDeposito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class LogDepositoDAO extends DAOSuper {

    /**
     * Construtor
     */
    public LogDepositoDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
   public boolean transfere(int idFuncionario, int idCliente, double valor) {
        conecta();
        int linhas = 0;

        // Cria Query
        String query = "CALL lp3_deposita_cliente("+idCliente+", "+idFuncionario+", "+valor+");";

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
    public ArrayList<LogDeposito> selectAll() {
        conecta();
        ArrayList<LogDeposito> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idFuncionario, idCliente, dataDeposito, valor FROM lp3_LogDeposito ORDER BY id ASC";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {
                LogDeposito l = new LogDeposito();
                l.setId(set.getInt("id"));
                l.setIdFuncionario(set.getInt("idFuncionario"));
                l.setIdCliente(set.getInt("idCliente"));
                l.setDataDeposito(set.getString("dataDeposito"));
                l.setValor(set.getDouble("valor"));

                ret.add(l);
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
    public LogDeposito selectById(int id) {
        conecta();
        LogDeposito ret = new LogDeposito();

        // Cria Query
        String query = "SELECT id, idFuncionario, dataDeposito, valor FROM lp3_LogDeposito WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setIdFuncionario(set.getInt("IdFuncionario"));
                ret.setDataDeposito(set.getString("DataDeposito"));
                ret.setValor(set.getDouble("Valor"));
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