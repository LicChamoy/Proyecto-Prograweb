package com.mycompany.proyectoweb.resources;

import com.mycompany.proyectoweb.resources.ConexionBd;
import com.mycompany.proyectoweb.resources.Dashboard.Publicacion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet("/editarPublicacion")
public class EditarPublicacionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPublicacion = Integer.parseInt(request.getParameter("id"));

        Publicacion publicacion = obtenerPublicacionPorId(idPublicacion);

        if (publicacion != null) {
            request.getSession().setAttribute("publicacionEditar", publicacion);
            request.getRequestDispatcher("/editarPublicacion.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la publicación guardada en el atributo de sesión
        Publicacion publicacion = (Publicacion) request.getSession().getAttribute("publicacionEditar");

        if (publicacion != null) {
            // Obtener los parámetros del formulario
            int idPublicacion = publicacion.getId();
            String nuevoTitulo = request.getParameter("titulo");
            String nuevaCategoria = request.getParameter("categoria");
            String nuevoContenido = request.getParameter("contenido");
            boolean eliminado = request.getParameter("eliminar") != null;

            boolean cambiosGuardados = editarPublicacion(idPublicacion, nuevoTitulo, nuevaCategoria, nuevoContenido, eliminado);

            if (cambiosGuardados) {
                // Redireccionar a la página de éxito o mostrar un mensaje de éxito
                response.sendRedirect("home.jsp");
            } else {
                // Redireccionar a la página de error o mostrar un mensaje de error
                response.sendRedirect("home.jsp");
            }
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    // Función para obtener una publicación por su ID desde la base de datos
    public static Publicacion obtenerPublicacionPorId(int idPublicacion) {
        Publicacion publicacion = null;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "SELECT * FROM publicaciones WHERE id = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setInt(1, idPublicacion);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                // Crear el objeto Publicacion con los datos obtenidos
                publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado, nombreCategoria, nombreUsuario);
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener la publicación desde la base de datos: " + e.getMessage());
        }

        return publicacion;
    }

    private boolean editarPublicacion(int idPublicacion, String nuevoTitulo, String nuevaCategoria, String nuevoContenido, boolean eliminado) {
        boolean cambiosGuardados = false;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "UPDATE publicaciones SET titulo = ?, nombre_categoria = ?, contenido = ?, fecha_creacion = ?, eliminado = ? WHERE id = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, nuevoTitulo);
            preparedStatement.setString(2, nuevaCategoria);
            preparedStatement.setString(3, nuevoContenido);

            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(4, fechaActual);

            preparedStatement.setBoolean(5, eliminado);

            preparedStatement.setInt(6, idPublicacion);

            int filasActualizadas = preparedStatement.executeUpdate();

            if (filasActualizadas > 0) {
                cambiosGuardados = true;
                System.out.println("La publicación ha sido actualizada exitosamente.");
            } else {
                System.out.println("Error al guardar los cambios de la publicación. No se pudieron actualizar los datos.");
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar los cambios de la publicación: " + e.getMessage());
        }

        return cambiosGuardados;
    }
}
