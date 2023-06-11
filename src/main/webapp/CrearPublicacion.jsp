<%@page import="com.mycompany.proyectoweb.resources.MetodosSQL"%>
<%@page import="java.util.List"%>
<%@ page import="com.mycompany.proyectoweb.resources.CrearP" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Publicación</title>
    <link rel="stylesheet" type="text/css" href="/css/crearp.css">
</head>
<body>
    <%@include file="navbar.jsp" %>
    
    <div id="content">
        <h1>Crear Publicación</h1>
        <form id="formulariocrear" action="${pageContext.request.contextPath}/CrearP" method="POST">
            <label for="titulo">Título:</label>
            <input type="text" name="titulo" required>
            <br>
            <label for="contenido">Contenido:</label>
            <textarea name="contenido" rows="5" required></textarea>
            <br>
            <label for="categoria">Categoría:</label>
            <select name="categoria" id="categoria" required>
                <option value="">Seleccione una categoría</option>
                <% List<String> categorias = CrearP.obtenerCategoriasDeBaseDatos();%>
                <%for (String categoria : categorias) { %>
                    <option value="<%= categoria %>"><%= categoria %></option>
                <% } %>
            </select>
            <br>
            <button id="button2" type="submit">Guardar</button>
        </form>
        <% if (request.getAttribute("mensajeError") != null) { %>
            <p class="error"><%= request.getAttribute("mensajeError") %></p>
        <% } %>
    </div>
    
    <%@include file="footer.jsp" %>
</body>
</html>
