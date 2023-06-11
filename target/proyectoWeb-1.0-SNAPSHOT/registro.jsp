<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar nuevo usuario</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/css/CSS.css">
        <style>
            .error-message {
                color: red;
                font-weight: bold;
                margin-top: 10px;
            }
        </style>
        <script>
            function validarFormulario() {
                var nombres = document.getElementById("nombres").value;
                var apellidos = document.getElementById("apellidos").value;
                var correo = document.getElementById("correo").value;
                var usuario = document.getElementById("usuario").value;
                var password = document.getElementById("password").value;
                var Cpassword = document.getElementById("confirmContra").value;

                var f = new Date();
                var mes = (f.getMonth() + 1).toString();
                var dia = f.getDate().toString();
                var fechaNac = document.getElementById("fecha_nacimiento").value;

                if (mes.length <= 1) {
                    mes = "0" + mes;
                }

                if (dia.length <= 1) {
                    dia = "0" + dia;
                }
                var fechaActual = f.getFullYear() + "-" + mes + "-" + dia;

                if (fechaNac > fechaActual) {
                    alert("La fecha seleccionada no se puede ingresar");
                    return false;
                }

                var expresion = /^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/;
                var expresion2 = /^[a-zA-ZÀ-ÿ\s]+$/;
                var expresion3 = /^[a-zA-Z0-9\_\-]+$/;

                if (nombres === "" || apellidos === "" || correo === "" || usuario === "" || password === "" || Cpassword === "") {
                    alert("Favor de ingresar todos los datos");
                    return false;
                } else if (!expresion2.test(nombres)) {
                    alert("El nombre tiene caracteres inválidos, solo ingresar letras");
                    return false;
                } else if (!expresion2.test(apellidos)) {
                    alert("Los apellidos tienen caracteres inválidos, solo ingresar letras");
                    return false;
                } else if (usuario.length > 20) {
                    alert("Nombre de usuario demasiado largo");
                    return false;
                } else if (!expresion3.test(usuario)) {
                    alert("El usuario contiene caracteres no válidos");
                    return false;
                } else if (password.length < 8 || !/[a-z]/.test(password) || !/[A-Z]/.test(password) || !/[0-9]/.test(password) || !/[.:;(){}-]/.test(password)) {
                    alert("La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un dígito y un signo de puntuación");
                    return false;
                } else if (password !== Cpassword) {
                    alert("Las contraseñas no coinciden");
                    return false;
                }

                var regx = /^([a-zA-Z0-9\._]+)@([a-zA-Z0-9])+.([a-z])(.[a-z]+)?$/;

                if (regx.test(correo)) {
                    return true;
                } else {
                    alert("Favor de proporcionar un correo válido.");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="Register" method="post" onsubmit="return validarFormulario();" enctype="multipart/form-data">
                <section class="Menu">
                    <h1>Datos del Usuario</h1>
                    <div class="txt">
                        <input type="text" name="nombre" id="nombres" required pattern="[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+" title="No se permiten símbolos">
                        <span></span>
                        <label>Nombre(s)</label>
                    </div>

                    <div class="txt">
                        <input type="text" name="apellidos" id="apellidos" required pattern="[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+" title="No se permiten símbolos">
                        <span></span>
                        <label>Apellido(s)</label>
                    </div>

                    <div class="txt">
                        <input type="text" name="correo" id="correo" required>
                        <span></span>
                        <label>Correo electrónico</label>
                    </div>

                    <div class="txt">
                        <input type="text" name="usuario" id="usuario" required pattern="[a-zA-Z]+" title="No se permiten caracteres especiales">
                        <span></span>
                        <label>Nombre de usuario</label>
                    </div>

                    <div class="txt">
                        <input type="password" name="password" id="password" required>
                        <span></span>
                        <label>Contraseña</label>
                    </div>

                    <div class="txt">
                        <input type="password" name="confirmarContra" id="confirmContra" required>
                        <span></span>
                        <label>Confirmar contraseña</label>
                    </div>

                    <div class="misc">
                        <p>Fecha de Nacimiento</p>
                    </div>

                    <input type="date" name="fecha_nacimiento" id="fecha_nacimiento" required>

                    <div class="misc">
                        <p>Elija una foto de perfil</p>
                        <input type="file" name="fotoPerfil" id="fotoPerfil" accept="image/*" required>
                    </div>

                    <input type="submit" value="Registrar">

                    <div class="reg">
                        <p>¿Ya tienes una cuenta?<a href="login.jsp"> Inicia sesión</a></p>
                    </div>
                </section>
                <div id="footer">
                    <p>Derechos reservados &copy; 2023 Diego Ismael Espinosa Ramos</p>
                </div>
            </form>
        </div>
    </body>
</html>
