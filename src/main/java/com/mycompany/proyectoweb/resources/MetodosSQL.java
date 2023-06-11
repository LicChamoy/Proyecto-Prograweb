package com.mycompany.proyectoweb.resources;

import com.mycompany.proyectoweb.resources.Dashboard.Publicacion;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;

public class MetodosSQL {
    public static List<Publicacion> buscarPublicaciones(String query) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        
        try {
            conexion = ConexionBd.conectar();
            
            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND (contenido LIKE LOWER(?) OR titulo LIKE LOWER(?))";
            String searchTerm = "%" + query + "%";
            String searchTerm2 = "%" + query + "%";

            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm2);
            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }
            
            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
    
    public static List<Publicacion> buscarPublicacionesPorCategoria(String categoria) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();

            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND nombre_categoria = ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, categoria);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
    
    public static List<Publicacion> buscarPublicacionesPorCategoriaYFecha(String categoria, Date fechaInicio, Date fechaFin) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();
            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND nombre_categoria = ? AND fecha_creacion BETWEEN ? AND ?";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, categoria);
            statement.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(3, new java.sql.Date(fechaFin.getTime()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);

            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public static List<Publicacion> buscarPublicacionesGeneralYCategoría(String query, String categoria) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();
            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND (contenido LIKE ? OR titulo LIKE ?) AND nombre_categoria = ?";
            String searchTerm = "%" + query + "%";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm);
            statement.setString(3, categoria);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
    
    public static List<Publicacion> buscarPublicacionesPorFechas(Date fechaInicio, Date fechaFin) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();

            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND fecha_creacion BETWEEN ? AND ?";
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(2, new java.sql.Date(fechaFin.getTime()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultados;
    }

    public static List<Publicacion> buscarPublicacionesPorFechaYGeneral(Date fechaInicio, Date fechaFin, String query) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();
            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND (contenido LIKE ? OR titulo LIKE ?) AND fecha_creacion BETWEEN ? AND ?";
            String searchTerm = "%" + query + "%";
            String searchTerm2 = "%" + query + "%";
            
                       
            statement = conexion.prepareStatement(sql);
            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm2);
            statement.setDate(3, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(4, new java.sql.Date(fechaFin.getTime()));

            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public static List<Publicacion> buscarPublicacionesGeneralFechaCategoria(String query, String categoria, Date fechaInicio, Date fechaFin) {
        List<Publicacion> resultados = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = ConexionBd.conectar();
            String sql = "SELECT * FROM publicaciones WHERE eliminado = FALSE AND (contenido LIKE ? OR titulo LIKE ?) AND nombre_categoria = ? AND fecha_creacion BETWEEN ? AND ?";
            String searchTerm = "%" + query + "%";
            String searchTerm2 = "%" + query + "%";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm2);
            statement.setString(3, categoria);
            statement.setDate(4, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(5, new java.sql.Date(fechaFin.getTime()));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                String contenido = resultSet.getString("contenido");
                Timestamp fechaCreacion = resultSet.getTimestamp("fecha_creacion");
                boolean eliminado = resultSet.getBoolean("eliminado");
                String nombreCategoria = resultSet.getString("nombre_categoria");
                String nombreUsuario = resultSet.getString("nombre_usuario");

                Publicacion publicacion = new Publicacion(id, titulo, contenido, fechaCreacion, eliminado,
                        nombreCategoria, nombreUsuario);
                resultados.add(publicacion);
            }

            resultSet.close();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public static int obtenerSiguientePagina(int paginaActual) {
        return paginaActual + 1;
    }

    public static int obtenerSiguientePagina() {
        return obtenerSiguientePagina(1);
    }
    
    public boolean verificarUsuarioExistente(String usuario) {
        boolean usuarioExistente = false;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    usuarioExistente = true;
                }
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario existente: " + e.getMessage());
        }

        return usuarioExistente;
    }

    public boolean registrarUsuario(String usuario, String pass, String nombre, String apellido, String nacimiento, String correo, Part fotoPerfil) {
        boolean registroExitoso = false;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "INSERT INTO usuarios (nombre_usuario, pass, nombre, apellido, FdN, correo, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, nombre);
            preparedStatement.setString(4, apellido);
            preparedStatement.setString(5, nacimiento);
            preparedStatement.setString(6, correo);
            
            InputStream fotoInputStream = fotoPerfil.getInputStream();
            preparedStatement.setBlob(7, fotoInputStream);

            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                registroExitoso = true;
                System.out.println("El usuario se registró correctamente.");
            } else {
                System.out.println("Error al registrar usuario. No se pudieron insertar los datos.");
            }

            conexion.close();
        } catch (SQLException | IOException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }

        return registroExitoso;
    }

    public byte[] profileImage(String nombreUsuario) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    byte[] imageBytes = null;

    try {
        Connection conexion = ConexionBd.conectar();
        stmt = conexion.prepareStatement("SELECT imagen FROM usuarios WHERE nombre_usuario = ?");
        stmt.setString(1, nombreUsuario);
        rs = stmt.executeQuery();

        if (rs.next()) {
            Blob imageBlob = rs.getBlob("imagen");
            if (imageBlob != null) {
                InputStream inputStream = imageBlob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                imageBytes = outputStream.toByteArray();
                inputStream.close();
                outputStream.close();
            }
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    return imageBytes;
}
     
    public boolean inicioSesion(String nombreUsuario, String pass) {
        boolean iniciarSesion = false;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND pass = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                iniciarSesion = true;
            }

            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }

        return iniciarSesion;
    }
    
    public boolean guardarCambiosUsuario(String nombreUsuario, String nombre, String apellido, String correoElectronico, String fechaNacimiento, Part fotoPerfil) {
        boolean cambiosGuardados = false;

        try {
            Connection conexion = ConexionBd.conectar();
            String query = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, FdN = ?, imagen = ? WHERE nombre_usuario = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, correoElectronico);
            preparedStatement.setString(4, fechaNacimiento);

            if (fotoPerfil != null) {
                InputStream fotoInputStream = fotoPerfil.getInputStream();
                preparedStatement.setBlob(5, fotoInputStream);
            } else {
                preparedStatement.setNull(5, java.sql.Types.BLOB);
            }

            preparedStatement.setString(6, nombreUsuario);

            int filasActualizadas = preparedStatement.executeUpdate();
            if (filasActualizadas > 0) {
                cambiosGuardados = true;
                System.out.println("Los cambios del usuario se guardaron correctamente.");
            } else {
                System.out.println("Error al guardar los cambios del usuario. No se pudieron actualizar los datos.");
            }

            conexion.close();
        } catch (SQLException | IOException e) {
            System.out.println("Error al guardar los cambios del usuario: " + e.getMessage());
        }

        return cambiosGuardados;
    }
}
