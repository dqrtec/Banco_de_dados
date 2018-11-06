/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Tibet Teixeira
 */
public class Conexao {

    public static Connection arirConexao() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/spotper";
            conn = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na conexão com o Banco",
                    "SpotPer", JOptionPane.ERROR_MESSAGE);
            e.getMessage();
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
