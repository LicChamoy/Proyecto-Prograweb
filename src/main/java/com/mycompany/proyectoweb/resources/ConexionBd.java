package com.mycompany.proyectoweb.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBd {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_pw1";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";

    public static Connection conectar() {
        Connection conexion = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return conexion;
    }
}
