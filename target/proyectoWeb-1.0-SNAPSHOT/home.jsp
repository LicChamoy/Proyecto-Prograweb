<%@page import="java.sql.Timestamp"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.proyectoweb.resources.Dashboard" %>
<%@ page import="com.mycompany.proyectoweb.resources.Dashboard.Publicacion"%>
<%@ page import="com.mycompany.proyectoweb.resources.MetodosSQL" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/css/home.css">
    </head>
    <body>
    <%@include file="navbar.jsp" %>


    <div id="publicaciones-container">
        <div class="publicaciones">
            <%
                int paginaActual = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;

                List<Publicacion> publicaciones = Dashboard.obtenerPublicacionesPaginaActual(paginaActual);
            %>
            <% if (publicaciones != null && !publicaciones.isEmpty()) {%>
                <% for (Publicacion publicacion : publicaciones) { %>
                    <div class="publicacion">
                        <div class="usuario-hora">
                            <span class="nombre-usuario"><%= publicacion.getNombreUsuario() %></span>
                            <span class="hora-publicacion"><%= obtenerTiempoTranscurrido(publicacion.getFechaCreacion()) %></span>
                        </div>
                        <h2 class="titulo-publicacion"><%= publicacion.getTitulo() %></h2>
                        <input type="date" id="fechaInicio" name="fechaInicio" style="display: none;" disabled>
                        <input type="date" id="fechaFin" name="fechaFin" style="display: none;" disabled>
                        <a href="${pageContext.request.contextPath}/buscarPublicacion?categoria=<%= publicacion.getNombreCategoria() %>" class="etiqueta-categoria">
                            <%= publicacion.getNombreCategoria() %>
                        </a>
                        <p class="contenido-publicacion"><%= publicacion.getContenido() %></p>

                        <% String nombre = (String) session.getAttribute("usuario"); %>
                        <% if (nombre != null && nombre.equals(publicacion.getNombreUsuario())) { %>
                            <a href="${pageContext.request.contextPath}/editarPublicacion?id=<%= publicacion.getId() %>">
                                Editar
                            </a>
                        <% } %>
                    </div>
                <% } %>
                <form id="pagination-form" method="GET" action="${pageContext.request.contextPath}/dashboard">
                    <input type="hidden" name="page" value="<%= paginaActual + 1 %>" id="page-input">
                    <button type="submit" id="load-more-button">Mostrar más</button>
                </form>

            <% } else { %>
                <p>No hay publicaciones disponibles.</p>
            <% } %>
        </div>
    </div>

    <%!
    String obtenerTiempoTranscurrido(Timestamp fechaCreacion) {
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaCreacionLocalDateTime = fechaCreacion.toLocalDateTime();
        Duration duracion = Duration.between(fechaCreacionLocalDateTime, fechaActual);

        long dias = duracion.toDays();
        long horas = duracion.toHours() % 24;
        long minutos = duracion.toMinutes() % 60;

        String tiempoTranscurrido = "";

        if (dias > 0) {
            tiempoTranscurrido = "Publicado hace " + dias + " días";
        } else if (minutos > 60 && horas < 2) {
            tiempoTranscurrido = "Publicado hace " + horas + " hora";
        } else if (horas > 1) {
            tiempoTranscurrido = "Publicado hace " + horas + " horas";
        } else if (minutos > 0) {
            tiempoTranscurrido = "Publicado hace " + minutos + " minutos";
        } else {
            tiempoTranscurrido = "Publicado ahora mismo";
        }

        return tiempoTranscurrido;
    }
    %>

    <%@include file="footer.jsp" %>

    </body>
</html>
