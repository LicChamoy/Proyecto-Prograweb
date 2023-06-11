<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio de Sesion</title>
        <link rel="stylesheet" href="css/login.css">
        <script src="Login.js"></script>
    </head>
    <body>
        <form action="InicioSesion" method="post" onsubmit="return validar();" form class="login-container">
            <div class="data" style="justify-content: center; align-content:center; text-align: center;">
                <br>

                <p>Inicio de Sesión</p>
                <br>
                <p style="font-size: 10px;"><%= request.getParameter("error") %></p>
                <input type="text" placeholder="Usuario" id="nombre_usuario" name="nombre_usuario">
                <br>
                <input type="password" placeholder="Contraseña" id="pass" name="pass">      
            </div>
             &nbsp; 
             <input type="submit" value="Login">
             &nbsp;
             <a href="registro.jsp">
                 <input type="button" value="Registrarse">
             </a>

        </form>
        <br> 
        <%
            String success = request.getParameter("success");
            if (success != null && success.equals("true")) {
                out.println("<script>alert('Inicio de sesión exitoso'); location.href='home.jsp';</script>");
            }
        %>

    </body>
</html>
