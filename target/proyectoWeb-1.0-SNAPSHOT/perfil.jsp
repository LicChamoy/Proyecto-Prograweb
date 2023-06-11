<%@page import="java.sql.SQLException"%>
<%@page import="com.mycompany.proyectoweb.resources.PerfilUsuarioServlet.PerfilUsuario"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.proyectoweb.resources.PerfilUsuarioServlet"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mycompany.proyectoweb.resources.MetodosSQL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Perfil de Usuario</title>
        <link rel="stylesheet" type="text/css" href="/css/perfilV2.css">
        <script src="https://kit.fontawesome.com/b277c39ac4.js" crossorigin="anonymous"></script>
        <script src="RegistroV2.js"></script>
    </head>
    <body>

        <div class="contenedor">
            <%@include file="navbar.jsp" %>
            <%
                PerfilUsuario perfil = null;
                if (userId != null) {
                    try {
                        perfil = PerfilUsuarioServlet.obtenerPerfil(userId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }
            %>
            <% if (perfil != null) { %>
                <h1>Editar datos del Usuario</h1>
                <div class="container">
                    <% if (perfil != null) { %>
                        <div class="profile">
                            <div class="profile-details">
                                <form id="profile-form" method="post" action="perfil" onsubmit="return validate(), return validar(), return validarFecha();" enctype="multipart/form-data">
                                    <div class="profile-image-container">
                                        <% 
                                        byte[] imagenBytes = perfil.getImagen();
                                        String base64Image = imagenBytes != null ? java.util.Base64.getEncoder().encodeToString(imagenBytes) : null;
                                        %>
                                        <img id="profile-image-preview" src="data:image/jpeg;base64,<%= base64Image %>" alt="Foto de perfil" class="preview-profile-image">
                                        <input type="file" name="fotoPerfil" id="fotoPerfil" accept="image/*" class="upload-button">

                                    </div>

                                    <div class="input-container">
                                        <div>
                                            <span class="label">Nombre(s):</span>
                                            <input type="text" class="value" name="nombre" id="nombre" value="<%= perfil.getNombre() %>" required>
                                        </div>
                                        <div>
                                            <span class="label">Apellido(s):</span>
                                            <input type="text" class="value" name="apellido" id="apellido" value="<%= perfil.getApellido() %>" required>
                                        </div>
                                    </div>

                                    <div>
                                        <span class="label">Dirección de correo electrónico:</span>
                                        <input type="text" class="value" name="correo" id="correo" value="<%= perfil.getCorreoElectronico() %>" required>
                                    </div>
                                    <div>
                                        <span class="label">Edad:</span>
                                        <span id="edad" class="value"><%= calcularEdad(perfil.getFechaNacimiento()) %></span>
                                    </div>

                                    <div>
                                        <script>
                                            asignarFechaNacimiento("<%= perfil.getFechaNacimiento() %>");
                                        </script>
                                        <span class="label">Fecha de nacimiento:</span>
                                        <input class="value" type="date" name="fecha_nacimiento" id="fecha_nacimiento" required>

                                    </div>
                                    <div>
                                        <label class="password-label">Nueva contraseña:</label>
                                        <input class="password-input" name="password" id="password" value=" ">
                                    </div>
                                    <div>
                                        <label class="password-label">Confirmar nueva contraseña:</label>
                                        <input class="password-input" name="Cpassword" id="Cpassword" value=" ">
                                    </div>
                                    <div>
                                        <button type="submit" class="save-button">Guardar cambios</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <% if (request.getAttribute("mensaje") != null) { %>
                            <p class="mensaje"><%= request.getAttribute("mensaje") %></p>
                        <% } %>
                        <script>
                            document.getElementById("profile-form").addEventListener("submit", function(event) {
                                var form = this;
                                var hasChanges = false;
                                var inputs = form.getElementsByTagName("input");
                                for (var i = 0; i < inputs.length; i++) {
                                    if (inputs[i].value !== inputs[i].defaultValue) {
                                        hasChanges = true;
                                        break;
                                    }
                                }
                                if (!hasChanges) {
                                    event.preventDefault();
                                    alert("No se realizaron cambios.");
                                    window.location.href = "home.jsp";
                                }
                            });
                        </script>
                    <% } else { %>
                        <h1>No se encontró el perfil de usuario</h1>
                    <% } %>
                </div>
            <% } else { %>
                <h1>No se encontró el perfil de usuario</h1>
            <% } %>
        </div>

        
        <script>
            document.getElementById("fotoPerfil").addEventListener("change", function(event) {
                var file = event.target.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    var previewImage = document.getElementById("profile-image-preview");
                    previewImage.src = e.target.result;
                };
                reader.readAsDataURL(file);
            });
        </script>

        <script>
            function formatearFecha(fecha) {
                var fechaObj = new Date(fecha);
                var dia = fechaObj.getDate();
                var mes = fechaObj.getMonth() + 1;
                var anio = fechaObj.getFullYear();

                if (dia < 10) {
                dia = "0" + dia;
                }
                if (mes < 10) {
                mes = "0" + mes;
                }

                return dia + "/" + mes + "/" + anio;
            }
        </script>
        
        <script>
            function actualizarEdad() {
                var fechaNacimiento = document.getElementById("fecha_nacimiento").value;
                var fechaNac = new Date(fechaNacimiento);
                var fechaActual = new Date();

                var diff = fechaActual - fechaNac;
                var edad = Math.floor(diff / (1000 * 60 * 60 * 24 * 365.25));

                document.getElementById("edad").textContent = edad;
            }

            document.getElementById("fecha_nacimiento").addEventListener("change", function() {
                actualizarEdad();
            });
        </script>

        
        <script>
            function asignarFechaNacimiento(fecha) {
              var fechaNacimientoInput = document.getElementById("fecha_nacimiento");
              if (fecha) {
                fechaNacimientoInput.value = fecha;
              } else {
                fechaNacimientoInput.value = "";
              }
            }

            asignarFechaNacimiento("<%= perfil.getFechaNacimiento() %>");
        </script>
        
        
        <%!
        int calcularEdad(String fechaNacimiento) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaNac = sdf.parse(fechaNacimiento);
                LocalDate fechaNacimientoLocal = new java.sql.Date(fechaNac.getTime()).toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                return Period.between(fechaNacimientoLocal, fechaActual).getYears();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
        %>

        
        
        <%@include file="footer.jsp" %>
    </body>
</html>
