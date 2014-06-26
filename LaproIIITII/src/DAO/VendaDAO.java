
package DAO;

import Model.ProdutoVenda;
import Model.Usuario;
import Model.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class VendaDAO extends DAOSuper{
    
    /**
     * Construtor
     */
    public VendaDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @param produtos Lista de produtos
     * @return Sucesso ou falha da operação
     */
    public boolean insere(Venda obj, ArrayList<ProdutoVenda> produtos) {
        conecta();
        int linhas = 0;
        int idVenda = 0;

        // Cria Query Venda
        String query = "INSERT INTO lp3_Venda(IdCliente, IdFuncionario, DataVenda, TotalVenda) VALUES('" + obj.getIdCliente() +"','"+
                obj.getIdFuncionario() +"','"+ obj.getDataVenda() +"','"+ obj.getTotalVenda() + "');";
        
        // Executa Venda
        try {
            cmd.executeUpdate(query);

            idVenda = lastId(obj.getIdFuncionario());
                
            // Query produtos
            for (ProdutoVenda item : produtos) {
                query = "INSERT INTO lp3_produtovenda(idProduto, idVenda, preco, quantidade) VALUES ('"+item.getIdProduto()+"', '"+idVenda+"', '"+item.getPreco()+"', '"+item.getQuantidade()+"');";
                conecta();
                linhas = cmd.executeUpdate(query);
            }
            
            // Altera o saldo do usuário
            Usuario user = new UsuarioDAO().selectById(obj.getIdCliente());
            user.setSaldo(user.getSaldo() - obj.getTotalVenda());
            new UsuarioDAO().edita(user, obj.getIdCliente());
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
            return false;
        } finally {
            desconecta();
        }
        
        return linhas > 0;
    }
    
    /**
     * Retorna a última venda feita por um funcionário
     * @param idFunc Id Funcionário
     * @return Último Id inserido
     */
    private int lastId(int idFunc) {
        
        int lastId = 0;
        
        String query = "SELECT id FROM lp3_venda WHERE idFuncionario = "+idFunc+" ORDER BY id DESC LIMIT 1";
        try {
            // Preenche retorno
            conecta();
            ResultSet set = cmd.executeQuery(query);
            set.next();
            lastId = set.getInt("id");
        } catch (SQLException ex) {
            Utils.Utils.showMsg(ex.getMessage());
        }
        
        return lastId;
    }
   
    /**
     * Seleciona todas linhas do banco de dados
     *
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<Venda> selectAll() {
        conecta();
        ArrayList<Venda> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idCliente, idFuncionario, DataVenda, TotalVenda FROM lp3_Venda ORDER BY id ASC";
        
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                Venda v = new Venda();
                v.setId(set.getInt("Id"));
                v.setIdCliente(set.getInt("IdCliente"));
                v.setIdFuncionario(set.getInt("IdFuncionario"));
                v.setDataVenda(set.getString("DataVenda"));
                v.setTotalVenda(set.getDouble("TotalVenda"));

                ret.add(v);
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
    public Venda selectById(int id) {
        conecta();
        Venda ret = new Venda();

        // Cria Query
        String query = "SELECT id, idCliente, idFuncionario, DataVenda, TotalVenda FROM lp3_Venda WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setIdCliente(set.getInt("IdCliente"));
                ret.setIdFuncionario(set.getInt("IdFuncionario"));
                ret.setDataVenda(set.getString("DataVenda"));
                ret.setTotalVenda(set.getDouble("TotalVenda"));
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
     * Seleciona todas linhas do banco de dados pelo Id do Usuário
     *
     * @param id Id do usuário
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<Venda> selectAllByUserId(int id) {
        conecta();
        ArrayList<Venda> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idCliente, idFuncionario, DataVenda, TotalVenda FROM lp3_Venda WHERE idCliente = "+id+" ORDER BY id ASC";
        
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {
                Venda v = new Venda();
                v.setId(set.getInt("Id"));
                v.setIdCliente(set.getInt("IdCliente"));
                v.setIdFuncionario(set.getInt("IdFuncionario"));
                v.setDataVenda(set.getString("DataVenda"));
                v.setTotalVenda(set.getDouble("TotalVenda"));

                ret.add(v);
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
     * Retorna o total
     *
     * @param idFunc Id do Funcionário
     * @param idCli Id do Cliente
     * @param data Data da consulta
     * @return ArrayList de objetos selecionados
     */
    public double getTotal(int idFunc, int idCli, String data) {
        conecta();
        double ret = 0;

        // Cria Query
        String query = "SELECT SUM(TotalVenda) AS total FROM lp3_Venda ";
        query += criaWhere(idFunc, idCli, data);
                
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret = set.getDouble("total");
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
     * Cria um Where para os relatórios
     * @param idFunc Id do funcionário
     * @param idCli Id do cliente
     * @param data Mês/Ano da consulta
     * @return 
     */
    private String criaWhere(int idFunc, int idCli, String data) {
        if (idFunc == 0 && idCli == 0 && data == null){
            return "";
        }
        boolean func = idFunc != 0;
        boolean cli = idCli != 0;
        boolean bData = data != null;
        
        String ret = "";
        // Possui filtro
        ret += " WHERE ";
        
        // Funcionário
        if (func) ret += " idFuncionario = " + idFunc;
        
        // Cliente
        if (func && cli) ret += " AND ";
        if (cli) ret += " idCliente = " + idCli;
        
        // Data
        if ((func || cli) && bData) ret += " AND ";
        if (bData) ret += " DataVenda LIKE '%" + data + "%'";
        
        //Utils.Utils.showMsg(ret);
        
        return ret;
    }
}