<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyectoweb.resources.Dashboard.Publicacion"%>

<html>
<head>
    <title>Resultados de búsqueda</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/result.css">
</head>
<body>
    <%@include file="navbar.jsp" %>
    <div class="publicaciones">
        <% List<Publicacion> resultados = (List<Publicacion>) request.getAttribute("resultados");
        if (resultados != null && !resultados.isEmpty()) {
            for (Publicacion publicacion : resultados) { %>
                <div class="publicacion">
                    <div class="usuario-hora">
                        <span class="nombre-usuario"><%= publicacion.getNombreUsuario() %></span>
                        <span class="hora-publicacion"><%= publicacion.getFechaCreacion() %></span>
                    </div>
                    <h2 class="titulo-publicacion"><%= publicacion.getTitulo() %></h2>
                    <a href="${pageContext.request.contextPath}/buscarPorCategoria?categoria=<%= publicacion.getNombreCategoria() %>" class="etiqueta-categoria"><%= publicacion.getNombreCategoria() %></a>
                    <p class="contenido-publicacion"><%= publicacion.getContenido() %></p>
                </div>            <% }
        } else if (resultados == null || resultados.isEmpty()){ %>
            <p>No se encontraron resultados.</p>
        <% } %>
    </div>

</body>
</html>
