/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.pa4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matthew
 */
public class DatabaseSetup {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/knives";
    static final String USER = "root";
    static final String PASS = "password";
    private Connection con;
    private Statement stmt;
    
    public DatabaseSetup(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = con.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            con.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Statement getStatement(){
        return stmt;
    }
    
    public Connection getConnection(){
        return con;
    }
}