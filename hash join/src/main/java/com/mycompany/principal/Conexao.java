/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author megazzzmata
 */
public class Conexao {
    public static Connection abrirConexao() {
        Connection conn = null;
        try {
            String userName = "SA";
            String password = "D@niel12";
            String url = "jdbc:sqlserver://localhost;databaseName=tpc-h";
            //;databaseName=tpch
            
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
}
