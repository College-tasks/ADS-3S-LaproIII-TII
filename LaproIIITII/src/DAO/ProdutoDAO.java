package DAO;

import Model.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ProdutoDAO extends DAOSuper {

    /**
     * Construtor
     */
    public ProdutoDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
    public boolean insere(Produto obj) {
        conecta();
        int linhas = 0;

        // Cria Query
        String query = "INSERT INTO lp3_Produto(IdCategoria, Nome, Preco) VALUES('" + obj.getIdCategoria() +"','"+ obj.getNome() +"','"+ obj.getPreco() +"')";
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
    public ArrayList<Produto> selectAll() {
        conecta();
        ArrayList<Produto> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idCategoria, nome, preco, ativo FROM lp3_Produto ORDER BY id ASC";
        
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                Produto p = new Produto();
                p.setId(set.getInt("Id"));
                p.setIdCategoria(set.getInt("IdCategoria"));
                p.setNome(set.getString("Nome"));
                p.setPreco(set.getDouble("preco"));
                p.setAtivo(set.getBoolean("ativo"));

                ret.add(p);
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
     * Seleciona todas linhas do banco de dados (Ativas)
     *
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<Produto> selectAllActive() {
        conecta();
        ArrayList<Produto> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, idCategoria, nome, preco, ativo FROM lp3_Produto WHERE ativo = 1 ORDER BY id ASC";
        
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                Produto p = new Produto();
                p.setId(set.getInt("Id"));
                p.setIdCategoria(set.getInt("IdCategoria"));
                p.setNome(set.getString("Nome"));
                p.setPreco(set.getDouble("preco"));
                p.setAtivo(set.getBoolean("ativo"));

                ret.add(p);
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
    public Produto selectById(int id) {
        conecta();
        Produto ret = new Produto();

        // Cria Query
        String query = "SELECT id, IdCategoria, Nome, Preco, Ativo FROM lp3_Produto WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setIdCategoria(set.getInt("IdCategoria"));
                ret.setNome(set.getString("Nome"));
                ret.setPreco(set.getDouble("Preco"));
                ret.setAtivo(set.getBoolean("Ativo"));
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
     * Edita uma linha no banco de dados
     *
     * @param obj Objeto a ser editado
     * @param id Id do objeto
     * @return Sucesso ou falha da operação
     */
    public boolean update(Produto obj, int id) {
        conecta();
        int linhas = 0;

        // Cria Query
        String ativo = obj.isAtivo() ? "1" : "0";
        String query = "UPDATE lp3_produto SET IdCategoria = " + obj.getIdCategoria() + ", Nome = '"+obj.getNome()+"', Preco = '"+obj.getPreco()+"', Ativo = '"+ ativo +"' WHERE id = " + id;
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
}