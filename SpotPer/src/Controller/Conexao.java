/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tibet Teixeira
 */
public class Conexao {

    public static Connection abrirConexao() {
        Connection conn = null;
        try {
            String userName = "sa";
            String password = "brasil158";            
            String url = "jdbc:sqlserver://ZOSO-WIN\\SQLSERVER;databaseName=spotper";
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, userName, password);
            
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão com o Banco",
                    "SpotPer", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return conn;
    }

    public static void fecharConexao(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
