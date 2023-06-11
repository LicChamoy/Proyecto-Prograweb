// Servlet InicioSesion
package com.mycompany.proyectoweb.resources;

import com.mycompany.proyectoweb.resources.MetodosSQL;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InicioSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Obtén los parámetros de usuario y contraseña del formulario
        String nombreUsuario = request.getParameter("nombre_usuario");
        String pass = request.getParameter("pass");

        // Lógica de inicio de sesión
        MetodosSQL metodos = new MetodosSQL();
        boolean iniciarSesion = metodos.inicioSesion(nombreUsuario, pass);

        if (iniciarSesion) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", nombreUsuario);
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Usuario no encontrado, verifica tus credenciales de acceso");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
}
