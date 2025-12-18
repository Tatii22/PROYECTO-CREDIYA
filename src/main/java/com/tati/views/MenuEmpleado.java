package com.tati.views;

import com.tati.controller.ClienteController;
import com.tati.controller.PrestamoController;
import com.tati.model.Cliente;
import com.tati.model.Empleado;
import com.tati.model.Prestamo;
import com.tati.repository.cliente.ClienteDBRepository;
import com.tati.repository.prestamo.PrestamoDBRepository;
import com.tati.service.cliente.ClienteServiceImpl;
import com.tati.service.prestamo.PrestamoServiceImpl;
import com.tati.exception.EntidadNoEncontradaException;

import java.util.Scanner;

public class MenuEmpleado {

    private final Scanner scan = new Scanner(System.in);
    private final ClienteController clienteController;
    private final PrestamoController prestamoController;
    private final Empleado empleadoLogueado;

    public MenuEmpleado(Empleado empleadoLogueado) {

        if (empleadoLogueado == null) {
            throw new EntidadNoEncontradaException("Empleado no puede ser null");
        }

        this.empleadoLogueado = empleadoLogueado;

        ClienteDBRepository clienteRepo = new ClienteDBRepository();
        ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepo);
        this.clienteController = new ClienteController(clienteService);

        PrestamoDBRepository prestamoRepo = new PrestamoDBRepository();
        PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(prestamoRepo);
        this.prestamoController = new PrestamoController(prestamoService);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scan.nextLine());
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        
        System.out.println("""
        .--. .-..-..---. .-.    .--.  .--. .---.  .--. 
       : .--': `' :: .; :: :   : .--': .; :: .  :: ,. :
       : `;  : .. ::  _.': :   : `;  :    :: :: :: :: :
       : :__ : :; :: :   : :__ : :__ : :: :: :; :: :; :
       `.__.':_;:_;:_;   :___.'`.__.':_;:_;:___.'`.__.'

       
                +-----------------------------+
                |        MENÚ EMPLEADO        |
                +-----------------------------+
                | [1] Registrar Cliente       |
                | [2] Registrar Préstamo      |
                | [3] Listar Clientes         |
                +-----------------------------+
                | [0] Volver al menu pricipal |
                +-----------------------------+
            """);
       
        System.out.print(">>> Ingrese su opción: ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> registrarPrestamo();
            case 3 -> listarClientes();
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción inválida");
        }
    }

    private void registrarCliente() {

        System.out.print("Nombre: ");
        String nombre = scan.nextLine();

        System.out.print("Documento: ");
        int documento = Integer.parseInt(scan.nextLine());

        System.out.print("Correo: ");
        String correo = scan.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scan.nextLine();

        System.out.print("Usuario: ");
        String usuario = scan.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scan.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setDocumento(documento);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        cliente.setUsuario(usuario);
        cliente.setContrasena(contrasena);

        clienteController.crearCliente(cliente);

        System.out.println(" Cliente registrado correctamente");
    }

    private void listarClientes() {
        System.out.println("=== LISTA DE CLIENTES ===");
        clienteController.listarClientes().forEach(System.out::println);
    }

    private void registrarPrestamo() {

        listarClientes();

        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(scan.nextLine());

        System.out.print("Monto: ");
        double monto = Double.parseDouble(scan.nextLine());

        System.out.print("Interés (%): ");
        double interes = Double.parseDouble(scan.nextLine());

        System.out.print("Cuotas: ");
        int cuotas = Integer.parseInt(scan.nextLine());

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);
        prestamo.setEmpleado(empleadoLogueado); 
        prestamo.setMonto(monto);
        prestamo.setInteres(interes);
        prestamo.setCuotas(cuotas);

        prestamoController.crearPrestamo(prestamo);

        System.out.println(" Préstamo registrado correctamente");
    }

}
