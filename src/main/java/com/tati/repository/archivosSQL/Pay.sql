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

-- Usuario empleado
INSERT INTO usuarios (nombre, documento, correo, telefono, salario, usuario, contrasena) 
VALUES ('Carlos Gómez', 87654321, 'carlos@mail.com', '3109876543', 2500000, 'cgomez', 'emp123');

-- Usuario cliente
INSERT INTO usuarios (nombre, documento, correo, telefono, salario, usuario, contrasena) 
VALUES ('Laura Martínez', 11223344, 'laura@mail.com', '3201112233', 1800000, 'lmartinez', 'cli123');

INSERT INTO usuarios (nombre, documento, correo, telefono, salario, usuario, contrasena) 
VALUES ('Pedro Ramírez', 99887766, 'pedro@mail.com', '3114455667', 1500000, 'pramirez', 'cli456');


-- Rol EMPLEADO
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (2, 2);

-- Rol CLIENTE
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (3, 3);
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (4, 3);


INSERT INTO prestamos (
    cliente_id,
    empleado_id,
    monto,
    interes,
    cuotas,
    fecha_inicio,
    fecha_vencimiento,
    saldo_pendiente,
    estado
) VALUES (
    3,
    2,
    5000000,
    12.50,
    24,
    '2025-01-01',
    '2026-12-01',
    5000000,
    'PENDIENTE'
);

-- Primer pago
INSERT INTO pagos (prestamo_id, fecha_pago, monto)
VALUES (1, '2025-02-01', 250000);

-- Segundo pago
INSERT INTO pagos (prestamo_id, fecha_pago, monto)
VALUES (1, '2025-03-01', 250000);

INSERT INTO prestamos (
    cliente_id,
    empleado_id,
    monto,
    interes,
    cuotas,
    fecha_inicio,
    fecha_vencimiento,
    saldo_pendiente,
    estado
) VALUES (
    4,
    2,
    3000000,
    15.00,
    12,
    '2023-01-01',
    '2024-01-01',
    3000000,
    'VENCIDO'
);
