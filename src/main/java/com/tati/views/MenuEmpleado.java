package com.tati.views;

import com.tati.controller.ClienteController;
import com.tati.model.Cliente;
import com.tati.repository.cliente.ClienteDBRepository;
import com.tati.service.cliente.ClienteServiceImpl;

import java.util.Scanner;

public class MenuEmpleado {
    private Scanner scan = new Scanner(System.in);
    private ClienteController clienteController;

    public MenuEmpleado() {
        // Inicializamos ClienteController con Repository y Service
        ClienteDBRepository clienteRepo = new ClienteDBRepository();
        ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepo);
        clienteController = new ClienteController(clienteService);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(scan.nextLine());
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    public void mostrarMenu() {
        String viewMenu = """
            .--. .-..-..---. .-.    .--.  .--. .---.  .--. 
            : .--': `' :: .; :: :   : .--': .; :: .  :: ,. :
            : `;  : .. ::  _.': :   : `;  :    :: :: :: :: :
            : :__ : :; :: :   : :__ : :__ : :: :: :; :: :; :
            `.__.':_;:_;:_;   :___.'`.__.':_;:_;:___.'`.__.'
            """;

        String menuOpciones = """
            +-----------------------------+
            | [1] Registrar Cliente       |
            | [2] Listar Clientes         |
            | [0] Volver al Menú Principal|
            +-----------------------------+
        """;

        System.out.println(viewMenu);
        System.out.println(menuOpciones);
        System.out.print(">>> ");
    }


    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> listarClientes();
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida.");
        }
    }

    private void registrarCliente() {
        System.out.print("Nombre: ");
        String nombre = scan.nextLine();
        System.out.print("Documento: ");
        int doc = Integer.parseInt(scan.nextLine());
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
        cliente.setDocumento(doc);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        cliente.setUsuario(usuario);
        cliente.setContrasena(contrasena);

        clienteController.crearCliente(cliente);
        System.out.println("Cliente registrado correctamente ✅");
    }

    private void listarClientes() {
        System.out.println("=== Lista de Clientes ===");
        clienteController.listarClientes().forEach(System.out::println);
    }
}
