package com.mycompany.proyectoweb.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CrearP")
public class CrearP extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> categorias = obtenerCategoriasDeBaseDatos();

        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher("/CrearP.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String titulo = request.getParameter("titulo");
    String contenido = request.getParameter("contenido");
    String categoria = request.getParameter("categoria");
    
    HttpSession session = request.getSession();
    String nombre_usuario = (String) session.getAttribute("usuario");

    if (titulo.isEmpty() || contenido.isEmpty() || categoria.isEmpty()) {
        request.setAttribute("mensajeError", "Todos los campos son obligatorios");
        request.getRequestDispatcher("/CrearPublicacion.jsp").forward(request, response);
    } else {
        try {
            Connection conexion = ConexionBd.conectar();
            String sql = "INSERT INTO publicaciones (titulo, contenido, nombre_categoria, nombre_usuario) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, titulo);
            statement.setString(2, contenido);
            statement.setString(3, categoria);
            statement.setString(4, nombre_usuario);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }
}

    public static List<String> obtenerCategoriasDeBaseDatos() {
        List<String> categorias = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConexionBd.conectar();

            String sql = "SELECT nombre_categoria FROM categorias";
            statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String categoria = resultSet.getString("nombre_categoria");
                categorias.add(categoria);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return categorias;
    }
}
