package DAO;

import Model.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class UsuarioDAO extends DAOSuper {

    /**
     * Construtor
     */
    public UsuarioDAO() {
        super();
    }

    /**
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
    public boolean insere(Usuario obj) {
        conecta();
        int linhas = 0;

        // Cria Query
        String query = "";
        if (obj.getMatricula() != null) {
            query = "INSERT INTO lp3_Usuario(Login, Senha, Matricula, Email, Saldo) VALUES('" + obj.getLogin() +"','"+ obj.getSenha() +"','"+ obj.getMatricula()
                +"','"+ obj.getEmail() +"','"+ obj.getSaldo() + "')";
        }
        else {
            query = "INSERT INTO lp3_Usuario(Login, Senha, Email) VALUES('" + obj.getLogin() +"','"+ obj.getSenha() +"','"+ obj.getEmail() +"')";
        }
        

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
     * Insere uma linha no banco de dados
     *
     * @param obj Objeto a ser inserido
     * @return Sucesso ou falha da operação
     */
    public boolean edita(Usuario obj, int id) {
        conecta();
        int linhas = 0;

        // Cria Query
        //falta inserir nomes dos campo do banco na query
        String query = "UPDATE lp3_usuario SET email = '" + obj.getEmail() + "', senha = '"+obj.getSenha()+"', saldo = '"+obj.getSaldo()+"' WHERE id = " + id;

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
    public ArrayList<Usuario> selectAll() {
        conecta();
        ArrayList<Usuario> ret = new ArrayList();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario ORDER BY id ASC";

        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            while (set.next()) {
                Usuario u = new Usuario();
                u.setId(set.getInt("Id"));
                u.setLogin(set.getString("Login"));
                u.setSenha(set.getString("Senha"));
                u.setMatricula(set.getString("Matricula"));
                u.setEmail(set.getString("Email"));
                u.setSaldo(set.getDouble("Saldo"));

                ret.add(u);
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
    public Usuario selectById(int id) {
        conecta();
        Usuario ret = new Usuario();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario WHERE id = " + id;
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setLogin(set.getString("Login"));
                ret.setSenha(set.getString("Senha"));
                ret.setMatricula(set.getString("Matricula"));
                ret.setEmail(set.getString("Email"));
                ret.setSaldo(set.getDouble("Saldo"));
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
     * Seleciona a linha com o Login escolhido
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return Objeto populado (Caso encontrado)
     */
    public Usuario logIn(String login, String senha) {
        conecta();
        Usuario ret = new Usuario();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario WHERE UPPER(login) = '" + login.toUpperCase() + "' AND senha = '" + senha  + "';";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setLogin(set.getString("Login"));
                ret.setSenha(set.getString("Senha"));
                ret.setMatricula(set.getString("Matricula"));
                ret.setEmail(set.getString("Email"));
                ret.setSaldo(set.getDouble("Saldo"));
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
     * Seleciona a linha com o Email escolhido
     *
     * @param email Email do usuário
     * @return Objeto populado (Caso encontrado)
     */
    public Usuario selectByEmail(String email) {
        conecta();
        Usuario ret = new Usuario();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario WHERE UPPER(email) = '" + email.toUpperCase() + "';";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setLogin(set.getString("Login"));
                ret.setSenha(set.getString("Senha"));
                ret.setMatricula(set.getString("Matricula"));
                ret.setEmail(set.getString("Email"));
                ret.setSaldo(set.getDouble("Saldo"));
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
     * Seleciona a linha com a matricula escolhida
     *
     * @param matricula Matrícula do usuário
     * @return Objeto populado (Caso encontrado)
     */
    public Usuario selectByMatricula(String matricula) {
        conecta();
        Usuario ret = new Usuario();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario WHERE matricula = '" + matricula + "';";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setLogin(set.getString("Login"));
                ret.setSenha(set.getString("Senha"));
                ret.setMatricula(set.getString("Matricula"));
                ret.setEmail(set.getString("Email"));
                ret.setSaldo(set.getDouble("Saldo"));
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
     * Seleciona a linha com o login escolhido
     *
     * @param login Login do usuário
     * @return Objeto populado (Caso encontrado)
     */
    public Usuario selectByLogin(String login) {
        conecta();
        Usuario ret = new Usuario();

        // Cria Query
        String query = "SELECT id, login, senha, matricula, email, saldo FROM lp3_Usuario WHERE login = '" + login + "';";
        try {
            // Preenche retorno
            ResultSet set = cmd.executeQuery(query);
            if (set.next()) {
                ret.setId(set.getInt("Id"));
                ret.setLogin(set.getString("Login"));
                ret.setSenha(set.getString("Senha"));
                ret.setMatricula(set.getString("Matricula"));
                ret.setEmail(set.getString("Email"));
                ret.setSaldo(set.getDouble("Saldo"));
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