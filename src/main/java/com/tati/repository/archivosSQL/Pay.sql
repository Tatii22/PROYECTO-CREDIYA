DROP DATABASE IF EXISTS crediya_db;


CREATE DATABASE crediya_db;
USE crediya_db;

-- Tabla usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento INT(50) NOT NULL UNIQUE,
    correo VARCHAR(80),
    telefono VARCHAR(20) NULL,
    salario DECIMAL(12,2) NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);

-- Tabla roles
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

-- Relación muchos a muchos usuario <-> roles
CREATE TABLE usuario_rol (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla préstamos
CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empleado_id INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuotas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,   
    saldo_pendiente DECIMAL(12,2) NOT NULL,  
    estado ENUM('PENDIENTE', 'PAGADO', 'VENCIDO') NOT NULL DEFAULT 'PENDIENTE', 
    FOREIGN KEY (cliente_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (empleado_id) REFERENCES usuarios(id_usuario)
);

-- Tabla pagos
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id_prestamo)
);

-- Inser Roles
INSERT INTO roles (nombre_rol) VALUES ('ADMINISTRADOR');
INSERT INTO roles (nombre_rol) VALUES ('EMPLEADO');
INSERT INTO roles (nombre_rol) VALUES ('CLIENTE');

-- Usuario admin
INSERT INTO usuarios (nombre, documento, correo, telefono, salario, usuario, contrasena) 
VALUES ('Tatiana Villamizar', '12345678', 'tati@mail.com', '3001234567', '3000000', 'admin', '123a');
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1);

CREATE TABLE historial_estado_prestamo (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_prestamo INT NOT NULL,
    estado_anterior ENUM('PENDIENTE', 'PAGADO', 'VENCIDO'),
    estado_nuevo ENUM('PENDIENTE', 'PAGADO', 'VENCIDO'),
    fecha_cambio DATETIME NOT NULL,
    FOREIGN KEY (id_prestamo) REFERENCES prestamos(id_prestamo)
);
