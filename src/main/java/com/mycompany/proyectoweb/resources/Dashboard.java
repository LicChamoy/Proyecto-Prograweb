package com.mycompany.proyectoweb.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    public static List<Publicacion> obtenerPublicacionesPaginaActual(int numeroPagina) {
        List<Publicacion> publicaciones = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();

            int publicacionesPorPagina = 10;
            int offset = (numeroPagina - 1) * publicacionesPorPagina;

            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE ORDER BY fecha_creacion DESC LIMIT ?, ?";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, offset);
            statement.setInt(2, publicacionesPorPagina);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);

                publicaciones.add(publicacion);
            }
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

            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return publicaciones;
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numeroPagina = 1;
        String numeroPaginaStr = request.getParameter("page");
        if (numeroPaginaStr != null && !numeroPaginaStr.isEmpty()) {
            numeroPagina = Integer.parseInt(numeroPaginaStr);
        }
        List<Publicacion> publicaciones = obtenerPublicacionesPaginaActual(numeroPagina);

        request.setAttribute("publicaciones", publicaciones);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }




    public static class Publicacion {
        private int id;
        private String titulo;
        private String contenido;
        private Timestamp fechaCreacion;
        private boolean eliminado;
        private String nombreCategoria;
        private String nombreUsuario;

        public Publicacion(int id, String titulo, String contenido, Timestamp fechaCreacion, boolean eliminado,
                String nombreCategoria, String nombreUsuario) {
            this.id = id;
            this.titulo = titulo;
            this.contenido = contenido;
            this.fechaCreacion = fechaCreacion;
            this.eliminado = eliminado;
            this.nombreCategoria = nombreCategoria;
            this.nombreUsuario = nombreUsuario;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public Timestamp getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(Timestamp fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public boolean isEliminado() {
            return eliminado;
        }

        public void setEliminado(boolean eliminado) {
            this.eliminado = eliminado;
        }

        public String getNombreCategoria() {
            return nombreCategoria;
        }

        public void setNombreCategoria(String nombreCategoria) {
            this.nombreCategoria = nombreCategoria;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }
    }
}