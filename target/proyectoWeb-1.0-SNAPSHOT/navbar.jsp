<%@page import="com.mycompany.proyectoweb.resources.MetodosSQL"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/navbar.css">
    </head>
    <body>
        <div id="navbar-container">
            <div id="navbar" class="navbar">
                <a href="home.jsp">
                    <img src="imagenes/logo.png" alt="cangrejo logo" class="logo">
                </a>
                <div id="search-bar" class="search-bar">
                    <form action="${pageContext.request.contextPath}/busquedaServlet" method="GET">
                        <input type="text" name="query" placeholder="Buscar publicación...">
                        <button type="submit">Buscar</button>
                    </form>
                    <button onclick="window.location.href='${pageContext.request.contextPath}/busquedaAvanzada.jsp'" class="busqueda-avanzada-button">
                        Búsqueda avanzada
                    </button>
                    <button onclick="window.location.href='${pageContext.request.contextPath}/CrearPublicacion.jsp'" class="busqueda-avanzada-button">
                        Crear publicación
                    </button>
                </div>
                <div class="user-profile">
                    <% String userId = (String) session.getAttribute("usuario"); %>
                    <% if (userId != null) { %>
                        <div class="dropdown">
                            <button class="dropbtn"><%= userId %></button>
                            <div class="dropdown-content">
                                <a href="${pageContext.request.contextPath}/perfil.jsp">Ver perfil</a>
                                <a href="${pageContext.request.contextPath}/login.jsp" onclick="cerrarSesion()">Cerrar sesión</a>
                            </div>
                            <script>
                                function cerrarSesion() {
                                    var xhttp = new XMLHttpRequest();
                                    xhttp.onreadystatechange = function() {
                                        if (this.readyState == 4 && this.status == 200) {
                                            window.location.href = "/login.jsp";
                                        }
                                    };
                                    xhttp.open("GET", "/logout", true);
                                    xhttp.send();
                                }
                            </script>
                        </div>
                        <% 
                        byte[] imageData = new MetodosSQL().profileImage(userId);
                        String base64Image = imageData != null ? java.util.Base64.getEncoder().encodeToString(imageData) : null;
                        %>
                        <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Foto de perfil" class="profile-image">
                    <% } %>
                </div>
            </div>
        </div>
                
        <script>
            window.addEventListener('scroll', function() {
                var navbar = document.getElementById('navbar-container');
                var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
                if (scrollTop > 0) {
                    navbar.classList.add('navbar-fixed');
                } else {
                    navbar.classList.remove('navbar-fixed');
                }
            });
        </script>
    </body>
</html>
