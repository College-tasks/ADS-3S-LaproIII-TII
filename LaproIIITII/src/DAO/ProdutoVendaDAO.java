package DAO;

import Model.ProdutoVenda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ProdutoVendaDAO extends DAOSuper {

    /**
     * Construtor
     */
    public ProdutoVendaDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
    public boolean insere(ProdutoVenda obj) {
        conecta();
        int linhas = 0;

        // Cria Query
        //falta inserir nomes dos campo do banco na query
        String query = "INSERT INTO lp3_ProdutoVenda(IdProduto, IdVenda, Preco, Quantidade) VALUES('" + obj.getIdProduto() + obj.getIdVenda() + obj.getPreco() + obj.getQuantidade() + "')";
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
     * @param id Id da Venda
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<ProdutoVenda> selectAllByVendaId(int id) {
        conecta();
        ArrayList<ProdutoVenda> ret = new ArrayList();

        // Cria Query
        String query = "SELECT idProduto, idVenda, preco, quantidade FROM lp3_ProdutoVenda WHERE idVenda = " + id ;
        
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {

                ProdutoVenda pv = new ProdutoVenda();
                pv.setIdProduto(set.getInt("IdProduto"));
                pv.setIdVenda(set.getInt("IdVenda"));
                pv.setPreco(set.getDouble("Preco"));
                pv.setQuantidade(set.getInt("Quantidade"));

                ret.add(pv);
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
    public ProdutoVenda selectById(int id) {
        conecta();
        ProdutoVenda ret = new ProdutoVenda();

        // Cria Query
        String query = "SELECT idProduto, idVenda, preco, quantidade FROM lp3_ProdutoVenda WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setIdProduto(set.getInt("IdProduto"));
                ret.setIdVenda(set.getInt("IdVenda"));
                ret.setPreco(set.getDouble("Valor"));
                ret.setQuantidade(set.getInt("Quantidade"));
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