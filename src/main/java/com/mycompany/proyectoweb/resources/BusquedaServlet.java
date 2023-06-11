package com.mycompany.proyectoweb.resources;

import com.mycompany.proyectoweb.resources.Dashboard.Publicacion;
import com.mycompany.proyectoweb.resources.MetodosSQL;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/busquedaServlet")
public class BusquedaServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        List<Publicacion> resultados = MetodosSQL.buscarPublicaciones(query);
        
        request.setAttribute("resultados", resultados);

        request.getRequestDispatcher("resultados.jsp").forward(request, response);
    }
}
