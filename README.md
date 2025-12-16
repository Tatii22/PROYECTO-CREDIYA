# 💳 Sistema de Cobros de Cartera **CrediYa**

## Descripción general

**CrediYa** es un sistema de consola desarrollado en **Java** que permite gestionar empleados, clientes, préstamos y pagos para una empresa de créditos personales. El proyecto aplica principios de **Programación Orientada a Objetos**, persistencia de datos mediante **MySQL (JDBC)** y buenas prácticas de diseño de software.

El sistema reemplaza el manejo manual en hojas de cálculo por una solución modular, escalable y mantenible.

---

## Objetivo del proyecto

Desarrollar un sistema modular que permita:
- Registrar y consultar empleados y clientes
- Gestionar préstamos con cálculo automático de intereses y cuotas
- Registrar pagos y mantener el historial de abonos
- Controlar el estado de los préstamos (PENDIENTE, PAGADO, VENCIDO)
- Persistir información en base de datos MySQL

---

## Funcionalidades por módulo

### Módulo de Usuarios
- Inicio de sesión
- Asignación de roles (ADMINISTRADOR, EMPLEADO, CLIENTE)

### Módulo de Empleados
- Registro de empleados
- Asociación de empleados a préstamos

### Módulo de Clientes
- Registro y listado de clientes
- Consulta de préstamos asociados

### Módulo de Préstamos
- Creación de préstamos asociados a cliente y empleado
- Cálculo automático de:
  - Monto total con interés
  - Cuota mensual
- Control de estados:
  - PENDIENTE
  - PAGADO
  - VENCIDO

### Módulo de Pagos
- Registro de pagos por cuota exacta
- Actualización del saldo pendiente
- Historial de pagos por préstamo

### Módulo de Reportes
- Consulta de préstamos por cliente
- Consulta de préstamos por estado
- Identificación de clientes morosos

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **MySQL**
- **JDBC**
- **POO (Herencia, Polimorfismo, Encapsulamiento)**
- **Patrón Repository y Service**
- **Git y GitHub**

---

## 📂 Estructura del proyecto

```
CREDIYA
│
├── recibos
│
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com
│ │ │ └── tati
│ │ │ ├── controller
│ │ │ │ ├── ClienteController.java
│ │ │ │ ├── EmpleadoController.java
│ │ │ │ ├── LoginController.java
│ │ │ │ ├── PagoController.java
│ │ │ │ ├── PrestamoController.java
│ │ │ │ └── ReporteController.java
│ │ │ │
│ │ │ ├── model
│ │ │ │ ├── Cliente.java
│ │ │ │ ├── Empleado.java
│ │ │ │ ├── EstadoPrestamo.java
│ │ │ │ ├── Pago.java
│ │ │ │ ├── Prestamo.java
│ │ │ │ └── Usuario.java
│ │ │ │
│ │ │ ├── repository
│ │ │ │ ├── archivosSQL
│ │ │ │ │ ├── DiagramaSQL.png
│ │ │ │ │ └── Pay.sql
│ │ │ │ │
│ │ │ │ ├── cliente
│ │ │ │ │ ├── ClienteDBRepository.java
│ │ │ │ │ └── ClienteRepository.java
│ │ │ │ │
│ │ │ │ ├── common
│ │ │ │ │ └── Repository.java
│ │ │ │ │
│ │ │ │ ├── empleado
│ │ │ │ │ ├── EmpleadoDBRepository.java
│ │ │ │ │ └── EmpleadoRepository.java
│ │ │ │ │
│ │ │ │ ├── pago
│ │ │ │ │ ├── PagoDBRepository.java
│ │ │ │ │ └── PagoRepository.java
│ │ │ │ │
│ │ │ │ ├── prestamo
│ │ │ │ │ ├── PrestamoDBRepository.java
│ │ │ │ │ └── PrestamoRepository.java
│ │ │ │ │
│ │ │ │ └── usuario
│ │ │ │ ├── UsuarioDBRepository.java
│ │ │ │ └── UsuarioRepository.java
│ │ │ │
│ │ │ ├── service
│ │ │ │ ├── cliente
│ │ │ │ │ ├── ClienteService.java
│ │ │ │ │ └── ClienteServiceImpl.java
│ │ │ │ │
│ │ │ │ ├── empleado
│ │ │ │ │ ├── EmpleadoService.java
│ │ │ │ │ └── EmpleadoServiceImpl.java
│ │ │ │ │
│ │ │ │ ├── pago
│ │ │ │ │ ├── PagoService.java
│ │ │ │ │ └── PagoServiceImpl.java
│ │ │ │ │
│ │ │ │ ├── prestamo
│ │ │ │ │ ├── PrestamoService.java
│ │ │ │ │ └── PrestamoServiceImpl.java
│ │ │ │ │
│ │ │ │ ├── reporte
│ │ │ │ │ ├── ReporteService.java
│ │ │ │ │ └── ReporteServiceImpl.java
│ │ │ │ │
│ │ │ │ └── usuario
│ │ │ │ ├── UsuarioService.java
│ │ │ │ └── UsuarioServiceImpl.java
│ │ │ │
│ │ │ ├── UML
│ │ │ │ ├── DiagramaUML.png
│ │ │ │ └── DiagramaUML.puml
│ │ │ │
│ │ │ ├── utils
│ │ │ │ └── DatabaseConnection.java
│ │ │ │
│ │ │ └── views
│ │ │ ├── LoginView.java
│ │ │ ├── MenuAdmin.java
│ │ │ ├── MenuCliente.java
│ │ │ ├── MenuEmpleado.java
│ │ │ ├── MenuPrincipal.java
│ │ │ └── MenuReportes.java
│ │ │
│ │ └── Main.java
│ │
│ ├── resources
│ │
│ └── test
│
├── target
│
├── pom.xml
└── README.md
---

## ▶️ Ejecución del proyecto

1. Crear la base de datos ejecutando el script SQL incluido. [Pay.sql](src\main\java\com\tati\repository\archivosSQL\Pay.sql)
2. Se deben guardar admin y roles desde sql. 
3. Configurar credenciales en `DatabaseConnection`.
4. Ejecutar la clase `MenuPrincipal`.
5. Iniciar sesión con usuario creado en base de datos, el cual es el administrador y crea a empleados, los empleados crean los clientes.

---

## Entregables

- Código fuente organizado por capas
- Script SQL
- Base de datos MySQL funcional
- Diagrama UML
- README.md
- Repositorio en GitHub

---

## Autor

Proyecto académico desarrollado como práctica integral de Java, JDBC y arquitectura en capas.
👤**Claudia Tatiana Villamizar marquez**