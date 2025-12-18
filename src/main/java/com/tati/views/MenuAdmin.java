package com.tati.views;

import java.util.List;
import java.util.Scanner;

import com.tati.controller.EmpleadoController;
import com.tati.controller.PrestamoController;
import com.tati.controller.ReporteController;
import com.tati.model.Empleado;
import com.tati.model.Cliente;
import com.tati.model.GestorPrestamos;
import com.tati.repository.cliente.ClienteDBRepository;
import com.tati.repository.prestamo.PrestamoDBRepository;
import com.tati.service.prestamo.PrestamoServiceImpl;
import com.tati.service.reporte.ReporteServiceImpl;
import com.tati.controller.ClienteController;

public class MenuAdmin {

    private final EmpleadoController empleadoController;
    private final ClienteController clienteController;
    private final PrestamoController prestamoController;
    private final ReporteController reporteController;
    ClienteDBRepository clienteRepo = new ClienteDBRepository();



    private final Scanner scan = new Scanner(System.in);

    public MenuAdmin(EmpleadoController empleadoController, ClienteController clienteController) {
        this.empleadoController = empleadoController;
        this.clienteController = clienteController;
        PrestamoDBRepository prestamoRepo = new PrestamoDBRepository();
        PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(prestamoRepo);
        this.prestamoController = new PrestamoController(prestamoService);
        ReporteServiceImpl reporteService = new ReporteServiceImpl(prestamoRepo, clienteRepo);
        this.reporteController = new ReporteController(reporteService);


    }
    

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scan.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            }
        } while (opcion != 0);
    }

    public void mostrarMenu() {

        String viewMenu = """
        .--. .---. .-..-..-..-..-..-. .--. .-----..---.  .--. .---.  .--. .---. 
        : .; :: .  :: `' :: :: `: :: :: .--'`-. .-': .; :: .; :: .  :: ,. :: .; :
        :    :: :: :: .. :: :: .` :: :`. `.   : :  :   .':    :: :: :: :: ::   .'
        : :: :: :; :: :; :: :: :. :: : _`, :  : :  : :.`.: :: :: :; :: :; :: :.`.
        :_;:_;:___.':_;:_;:_;:_;:_;:_;`.__.'  :_;  :_;:_;:_;:_;:___.'`.__.':_;:_;
        """;

        String menuOpciones = """
                            +-----------------------------------+
                            |          * Bienvenid@ *           |
                            +-----------------------------------+
                            | [1] Crear empleado                |
                            | [2] Consultar empleados           |
                            | [3] Consultar préstamos           |
                            | [4] Consultar clientes            |
                            | [5] Examen                      |
                            +-----------------------------------+
                            | [0] Volver al menú principal      |
                            +-----------------------------------+
                """;

        System.out.println("\n" + viewMenu);
        System.out.println(menuOpciones);
        System.out.print(">>> Ingrese su opción: ");
    }

    public void procesarOpcion(int opcion) {

        switch (opcion) {

            case 1 -> crearEmpleado();

            case 2 -> listarEmpleados();

            case 3 ->  listarPrestamos();

            case 4 -> listarClientes();

            case 5 -> reportes();

            case 0 -> System.out.println("Volviendo al menú principal...");

            default -> System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    private void crearEmpleado() {

        System.out.println("Nombre:");
        String nombre = scan.nextLine();

        System.out.println("Documento:");
        int documento = Integer.parseInt(scan.nextLine());

        System.out.println("Correo:");
        String correo = scan.nextLine();

        System.out.println("Salario:");
        double salario = Double.parseDouble(scan.nextLine());

        System.out.println("Usuario:");
        String usuario = scan.nextLine();

        System.out.println("Contraseña:");
        String contrasena = scan.nextLine();

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setDocumento(documento);
        empleado.setCorreo(correo);
        empleado.setSalario(salario);
        empleado.setUsuario(usuario);
        empleado.setContrasena(contrasena);

        empleadoController.crearEmpleado(empleado);
        System.out.println("Empleado registrado correctamente ");
    }

    private void listarEmpleados() {
        List<Empleado> empleados = empleadoController.listarEmpleados();
        empleados.forEach(System.out::println);
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteController.listarClientes();
        clientes.forEach(System.out::println);
    }
    private void listarPrestamos() {
        List<GestorPrestamos> prestamos = prestamoController.listarTodosPrestamos();
        System.out.println("=== LISTA DE PRÉSTAMOS ===");
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            prestamos.forEach(System.out::println);
        }
    
    
    }

    private void reportes() {
        MenuReportes menuReportes = new MenuReportes(prestamoController, reporteController);
        menuReportes.iniciar();
    }

}
