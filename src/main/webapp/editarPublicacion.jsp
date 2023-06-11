<%@page import="com.mycompany.proyectoweb.resources.EditarPublicacionServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.proyectoweb.resources.Dashboard" %>
<%@ page import="com.mycompany.proyectoweb.resources.Dashboard.Publicacion" %>
<%@ page import="com.mycompany.proyectoweb.resources.CrearP" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Publicación</title>
        <link rel="stylesheet" type="text/css" href="/css/editarp.css">
        <script>
            function confirmarEliminacion() {
                return confirm("Esta acción eliminará la publicación y no se puede deshacer. ¿Deseas continuar?");
            }
        </script>
    </head>
    <body>
        <%@include file="navbar.jsp" %>
        <div class="container">
            <%
                Publicacion publicacion = null;
                String idPublicacion = request.getParameter("id");
                if (idPublicacion != null) {
                    publicacion = EditarPublicacionServlet.obtenerPublicacionPorId(Integer.parseInt(idPublicacion));
                }
            %>
            <h1>Editar Publicación</h1>
            <form action="editarPublicacion" method="post" onsubmit="return confirmarEliminacion();">
                <input type="hidden" name="id" value="<%= publicacion.getId() %>">
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" value="<%= publicacion.getTitulo() %>">

                <label for="contenido">Contenido:</label>
                <textarea id="contenido" rows="5" name="contenido"><%= publicacion.getContenido() %></textarea>

                <label for="categoria">Categoría:</label>
                <select id="categoria" name="categoria">
                    <% List<String> categorias = CrearP.obtenerCategoriasDeBaseDatos(); %>
                    <% for (String categoria : categorias) { %>
                        <option value="<%= categoria %>" <%= categoria.equals(publicacion.getNombreCategoria()) ? "selected" : "" %>><%= categoria %></option>
                    <% } %>
                </select>

                <label>Eliminar publicación:</label>
                <select id="eliminar" name="eliminar">
                    <option value="false">No</option>
                    <option value="true">Sí</option>
                </select>

                <input type="submit" value="Guardar Cambios">
            </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>

