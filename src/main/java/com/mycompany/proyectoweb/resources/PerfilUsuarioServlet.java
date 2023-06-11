package com.mycompany.proyectoweb.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/perfil")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class PerfilUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("usuario");
        if (userId != null) {
            try {
                PerfilUsuario perfil = obtenerPerfil(userId);
                request.setAttribute("perfil", perfil);
                request.getRequestDispatcher("/perfil.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("usuario");
        if (userId != null) {
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String correo = request.getParameter("correo");
            String fechaNacimiento = request.getParameter("fecha_nacimiento");
            Part fotoPerfil = request.getPart("fotoPerfil");

            MetodosSQL metodosSQL = new MetodosSQL();
            boolean cambiosGuardados = metodosSQL.guardarCambiosUsuario(userId, nombre, apellido, correo, fechaNacimiento, fotoPerfil);

            if (cambiosGuardados) {
                response.sendRedirect(request.getContextPath() + "/perfil");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar los cambios del perfil");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    
    public static String obtenerContrasena(String userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String contrasena = null;

        try {
            connection = ConexionBd.conectar();
            String query = "SELECT pass FROM usuarios WHERE nombre_usuario = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, userId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                contrasena = resultSet.getString("pass");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return contrasena;
    }

    private byte[] obtenerBytesDesdePart(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

    public static PerfilUsuario obtenerPerfil(String userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PerfilUsuario perfil = null;

        try {
            connection = ConexionBd.conectar();
            String query = "SELECT nombre_usuario, nombre, apellido, correo, FdN, imagen FROM usuarios WHERE nombre_usuario = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, userId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String correoElectronico = resultSet.getString("correo");
                String fechaNacimiento = resultSet.getString("FdN");
                byte[] imagenBytes = resultSet.getBytes("imagen");
                return new PerfilUsuario(nombreUsuario, nombre, apellido, correoElectronico, fechaNacimiento, imagenBytes);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return perfil;
    }

    public static class PerfilUsuario {
        private String nombreUsuario;
        private String nombre;
        private String apellido;
        private String fechaNacimiento;
        private String correoElectronico;
        private byte[] imagen;

        public PerfilUsuario(String nombreUsuario, String nombreCompleto, String apellido,
                String correoElectronico, String fechaNacimiento, byte[] imagen) {
            this.nombreUsuario = nombreUsuario;
            this.nombre = nombreCompleto;
            this.apellido = apellido;
            this.fechaNacimiento = fechaNacimiento;
            this.correoElectronico = correoElectronico;
            this.imagen = imagen;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public byte[] getImagen() {
            return imagen;
        }
    }
}
