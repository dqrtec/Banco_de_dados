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

    public static Connection arirConexao() {
        Connection conn = null;
        try {

            /* CONEXAO SQL SERVER (ERRO)
            
            String url = "jdbc:sqlserver://MYPC\\SQLEXPRESS;databaseName=MYDB"; // Config 1
            String url = "jdbc:sqlserver://ZOSO-WIN\\SQLEXPRESS;databaseName=spotper;integratedSecurity=true"; // Config 2

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection(url, userName, password); // Config 1
            conn = DriverManager.getConnection(url); // Config 2
             */
            
            /* CONEXAO POSTGRES (Ok) */
            
            String userName = "postgres";
            String password = "brasil158";
            String url = "jdbc:postgresql://localhost:5432/spotper";
            
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            
            /* Mysql Connection (ok)
            
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/spotper";
            conn = DriverManager.getConnection(url, "root", "");
            */
            
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
