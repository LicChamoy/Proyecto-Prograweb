package com.mycompany.proyectoweb.resources;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "Register", urlPatterns = {"/Register"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Tamaño máximo de la imagen: 5MB
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellidos");
        String fechaNacimiento = request.getParameter("fecha_nacimiento");
        String correo = request.getParameter("correo");
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("password");
        Part filePart = request.getPart("fotoPerfil");

        if (usuario != null && !usuario.isEmpty() && nombre != null && !nombre.isEmpty()) {
            MetodosSQL metodos = new MetodosSQL();

            boolean usuarioExistente = metodos.verificarUsuarioExistente(usuario);
            if (usuarioExistente) {
                request.setAttribute("error", "El usuario ya está en uso");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }

            boolean contrasenasIguales = verificarContrasenasIguales(contrasena, request.getParameter("confirmarContra"));
            if (!contrasenasIguales) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }

            boolean fechaValida = verificarFechaNacimiento(fechaNacimiento);
            if (!fechaValida) {
                request.setAttribute("error", "La fecha de nacimiento no es válida");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
                return;
            }

            boolean registro = metodos.registrarUsuario(usuario, contrasena, nombre, apellido, fechaNacimiento, correo, filePart);

            if (registro) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?registro=exitoso");
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/registro.jsp?registro=fallido");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private boolean verificarContrasenasIguales(String contrasena, String confirmarContra) {
        return contrasena.equals(confirmarContra);
    }

    private boolean verificarFechaNacimiento(String fechaNacimiento) {
        // Obtener la fecha actual
        java.util.Date fechaActual = new java.util.Date();

        // Convertir la fecha de nacimiento en un objeto Date
        java.util.Date fechaNac = null;
        try {
            fechaNac = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // Comparar la fecha de nacimiento con la fecha actual
        return fechaNac != null && fechaNac.before(fechaActual);
    }
}
