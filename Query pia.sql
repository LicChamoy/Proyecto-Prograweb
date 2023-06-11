drop database if exists bd_pw1;
CREATE DATABASE bd_pw1;

USE bd_pw1;

CREATE TABLE usuarios (
  nombre_usuario VARCHAR(50) NOT NULL PRIMARY KEY,
  pass VARCHAR(50) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FdN DATE NOT NULL,
  correo VARCHAR(100) NOT NULL,
  imagen LONGBLOB
);

CREATE TABLE categorias (
    nombre_categoria VARCHAR(50) primary key
);

CREATE TABLE publicaciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100),
    contenido TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    eliminado BOOLEAN DEFAULT FALSE,
    nombre_categoria VARCHAR(50),
    nombre_usuario VARCHAR(50),
    FOREIGN KEY (nombre_categoria) REFERENCES categorias (nombre_categoria) ON DELETE CASCADE,
    FOREIGN KEY (nombre_usuario) REFERENCES usuarios (nombre_usuario) ON DELETE CASCADE
);

ALTER TABLE usuarios
ADD fecha_creacion TIMESTAMP;

DROP TABLE usuarios;

select * from usuarios;
select * from publicaciones;
select * from categorias;

SELECT nombre_usuario, nombre, apellido, correo, FdN, imagen FROM usuarios WHERE nombre_usuario = 'Chamoi';

SELECT * FROM publicaciones WHERE eliminado = FALSE AND (contenido LIKE LOWER('%esta%') OR titulo LIKE LOWER('%esta%'));

SELECT imagen FROM usuarios WHERE nombre_usuario = 'Chamoi';

DELETE FROM usuarios WHERE nombre_usuario = 'a';

INSERT INTO usuarios (nombre_usuario, pass, nombre, apellido, FdN, correo)
VALUES ('admin', 'admin', 'admin', 'admin', '1990-01-01', 'usuario1@example.com');

INSERT INTO usuarios (nombre_usuario, pass, nombre, apellido, FdN, correo)
VALUES ('Chamoi', 'Chamoi.123', 'Chamoi', 'Segundo', '2000-04-29', 'chamoi@gmail.com');

UPDATE usuarios SET fecha_creacion = ('2023-06-03 12:14:57') WHERE nombre_usuario = ('Chamoi');


INSERT INTO categorias(nombre_categoria)
VALUES ('Politica');

insert INTO publicaciones (titulo, contenido, nombre_categoria, nombre_usuario)
Values ('Publicacion numero 13 para ver si jala la paginacion', 'me quede sin ideas una disculpa', 'random', 'Chamoi');

insert INTO publicaciones (titulo, contenido, nombre_categoria, nombre_usuario, eliminado)
Values ('test', 'esta publicacion deberia de poder ser vista.', 'random', 'admin', false);
