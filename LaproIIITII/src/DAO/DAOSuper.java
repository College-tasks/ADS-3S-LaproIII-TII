package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Classe pai dos DAO's; cria e fecha conexões
 * @author Simor / Daniel L.
 */
abstract class DAOSuper {
    protected Connection con;
    protected Statement cmd;
    
    /**
     * Construtor
     */
    public DAOSuper() {
        String conStr = "jdbc:mysql://50.115.33.88:3306/temp?zeroDateTimeBehavior=convertToNull";
        try {
            con = DriverManager.getConnection(conStr, "tempUser", "temp123");
        } catch (SQLException ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
    }
    
    /**
     * Cria a conexão com o banco de dados
     */
    protected void conecta(){
        try {
            cmd = con.createStatement();
        } catch (SQLException ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    protected void desconecta(){
        try {
            if (!con.isClosed()) {
            con.close();
        }
        } catch (SQLException ex) { JOptionPane.showMessageDialog(null, ex.getMessage()); }
        
    }
}
