package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.CategoriaProduto;

/**
 * Classe DAO de CategoriaProduto
 * @author Simor / Daniel L.
 */
public class CategoriaProdutoDAO extends DAOSuper {
    /**
     * Construtor
     */
    public CategoriaProdutoDAO(){
        super();
    }
    
    /**
     * Insere uma linha no banco de dados
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
    public boolean insere(CategoriaProduto obj){
        conecta();
        int linhas = 0;
        
        // Cria Query
        String query = "INSERT INTO lp3_categoria_produto(nome) VALUES('"+obj.getNome()+"')";
        
        // Executa
        try {
            linhas = cmd.executeUpdate(query);    
        } catch (SQLException ex) { Utils.Utils.showMsg(ex.getMessage()); }
        finally { desconecta(); }
        
        return linhas > 0;
    }
    
    /**
     * Seleciona todas linhas do banco de dados
     * @return ArrayList de objetos selecionados
     */
    public ArrayList<CategoriaProduto> selectAll(){
        conecta();
        ArrayList<CategoriaProduto> ret = new ArrayList();
        
        // Cria Query
        String query = "SELECT id, nome FROM lp3_categoria_produto ORDER BY id ASC";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while(set.next()) {
                CategoriaProduto o = new CategoriaProduto();
                o.setNome(set.getString("nome"));
                o.setId(set.getInt("id"));
                
                ret.add(o);
            }
        } catch (SQLException ex) { Utils.Utils.showMsg(ex.getMessage()); }
        finally { desconecta(); }
        
        // Retorna
        return ret;
    }
    
    /**
     * Seleciona a linha com o Id escolhido
     * @param id ID da linha
     * @return Objeto populado (Caso encontrado)
     */
    public CategoriaProduto selectById(int id) {
        conecta();
        CategoriaProduto ret = new CategoriaProduto();
        
        // Cria Query
        String query = "SELECT id, nome FROM lp3_categoria_produto WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if(set.next()) {
                ret.setNome(set.getString("nome"));
                ret.setId(set.getInt("id"));
            }
        } catch (SQLException ex) { Utils.Utils.showMsg(ex.getMessage()); }
        finally { desconecta(); }
        
        // Retorna
        return ret;
    }
    
    /**
     * Seleciona a linha com o Nome escolhido
     * @param nome Nome da linha
     * @return Objeto populado (Caso encontrado)
     */
    public CategoriaProduto selectByNome(String nome) {
        conecta();
        CategoriaProduto ret = new CategoriaProduto();
        
        // Cria Query
        String query = "SELECT id, nome FROM lp3_categoria_produto WHERE nome = '" + nome + "';";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if(set.next()) {
                ret.setNome(set.getString("nome"));
                ret.setId(set.getInt("id"));
            }
        } catch (SQLException ex) { Utils.Utils.showMsg(ex.getMessage()); }
        finally { desconecta(); }
        
        // Retorna
        return ret;
    }
}