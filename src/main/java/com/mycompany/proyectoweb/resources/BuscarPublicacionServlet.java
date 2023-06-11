package com.mycompany.proyectoweb.resources;

import com.mycompany.proyectoweb.resources.Dashboard.Publicacion;
import java.util.List;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/buscarPublicacion")
public class BuscarPublicacionServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            String categoria = request.getParameter("categoria");
            String query = request.getParameter("query");

            java.util.Date fechaInicioUtil = null;
            java.util.Date fechaFinUtil = null;

        if (!categoria.isEmpty()) {
            // Búsqueda por categoría
            List<Publicacion> resultados = MetodosSQL.buscarPublicacionesPorCategoria(categoria);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else if (!query.isEmpty() && !categoria.isEmpty()) {
            
            // Búsqueda por categoría, query
            java.util.List<Publicacion> resultados = MetodosSQL.buscarPublicacionesGeneralYCategoría(query, categoria);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty() && !categoria.isEmpty()) {
            
            if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaInicioUtil = sdf.parse(fechaInicioStr);
                    fechaFinUtil = sdf.parse(fechaFinStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Búsqueda por categoría, fecha inicio y fecha fin
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechaFinUtil.getTime());
            java.util.List<Publicacion> resultados = MetodosSQL.buscarPublicacionesPorCategoriaYFecha(categoria, sqlFechaInicio, sqlFechaFin);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty() && !query.isEmpty()) {
            
            if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaInicioUtil = sdf.parse(fechaInicioStr);
                    fechaFinUtil = sdf.parse(fechaFinStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Búsqueda por query, fecha inicio y fecha fin
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechaFinUtil.getTime());
            java.util.List<Publicacion> resultados = MetodosSQL.buscarPublicacionesPorFechaYGeneral(sqlFechaInicio, sqlFechaFin, query);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty()) {
            
            if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaInicioUtil = sdf.parse(fechaInicioStr);
                    fechaFinUtil = sdf.parse(fechaFinStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Búsqueda por fecha inicio y fecha fin
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechaFinUtil.getTime());
            List<Publicacion> resultados = MetodosSQL.buscarPublicacionesPorFechas(sqlFechaInicio, sqlFechaFin);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty() && !query.isEmpty() && !categoria.isEmpty()) {
            
            if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaInicioUtil = sdf.parse(fechaInicioStr);
                    fechaFinUtil = sdf.parse(fechaFinStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Búsqueda por fecha inicio y fecha fin, query y categoria
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicioUtil.getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(fechaFinUtil.getTime());
            List<Publicacion> resultados = MetodosSQL.buscarPublicacionesGeneralFechaCategoria(query, categoria, sqlFechaInicio, sqlFechaFin);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        } else {
            // Búsqueda general
            List<Publicacion> resultados = MetodosSQL.buscarPublicaciones(query);
            request.setAttribute("resultados", resultados);
            request.getRequestDispatcher("resultados.jsp").forward(request, response);
        }
    }
}
