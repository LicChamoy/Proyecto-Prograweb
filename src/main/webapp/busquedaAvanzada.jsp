<%@page import="com.mycompany.proyectoweb.resources.CrearP"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Búsqueda Avanzada</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/busqueda.css">
    </head>
    <body>
        <%@include file="navbar.jsp" %>
        
        
        <div class="form-container">
            <h1>Búsqueda Avanzada</h1>
            <form action="buscarPublicacion" method="get">
                <label for="fechaInicio">Fecha de inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio">

                <label for="fechaFin">Fecha de fin:</label>
                <input type="date" id="fechaFin" name="fechaFin">

                <label for="categoria">Categoría:</label>
                <select id="categoria" name="categoria">
                    <option value="">-- Todas las categorías --</option>
                    <% List<String> categorias = CrearP.obtenerCategoriasDeBaseDatos();%>
                    <% for (String categoria : categorias) { %>
                        <option value="<%= categoria %>"><%= categoria %></option>
                    <% } %>
                </select>

                <label for="query">Búsqueda general:</label>
                <input type="text" id="query" name="query">

                <input type="submit" value="Buscar">
            </form>
        </div>

                
        <%@include file="footer.jsp" %>
    </body>
</html>
