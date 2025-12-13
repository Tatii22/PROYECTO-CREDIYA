package com.tati.views;

import com.tati.controller.EmpleadoController;
import com.tati.controller.LoginController;
import com.tati.model.Cliente;
import com.tati.model.Usuario;
import com.tati.repository.cliente.ClienteDBRepository;
import com.tati.repository.empleado.EmpleadoDBRepository;
import com.tati.repository.usuario.UsuarioDBRepository;
import com.tati.service.cliente.ClienteServiceImpl;
import com.tati.service.empleado.EmpleadoServiceImpl;
import com.tati.service.usuario.UsuarioServiceImpl;
import com.tati.controller.ClienteController;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scan = new Scanner(System.in);
    private final LoginView loginView;
    private final LoginController loginController;

    public MenuPrincipal() {

        UsuarioDBRepository usuarioRepository = new UsuarioDBRepository();
        UsuarioServiceImpl usuarioService = new UsuarioServiceImpl(usuarioRepository);
        loginController = new LoginController(usuarioService);

        loginView = new LoginView(loginController);
    }

    public void iniciar() {
        int opcion = -1;

        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scan.nextLine());

                switch (opcion) {
                    case 1 -> manejarLogin();
                    case 0 -> System.out.println(" Saliendo del sistema...");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println(" Ingresa un número válido.");
            }

        } while (opcion != 0);
    }

    private void manejarLogin() {

        try {
            Usuario usuario = loginView.iniciarSesion();
            String rol = loginController.obtenerRol(usuario.getId());

            switch (rol.toUpperCase()) {

                case "ADMINISTRADOR":
                    EmpleadoController empleadoController = new EmpleadoController(new EmpleadoServiceImpl(new EmpleadoDBRepository()));
                    ClienteController clienteController = new ClienteController(new ClienteServiceImpl(new ClienteDBRepository()));
                    new MenuAdmin(empleadoController, clienteController).iniciar();
                    break;

                case "EMPLEADO":
                    new MenuEmpleado().iniciar();
                    break;

                case "CLIENTE":
                    new MenuCliente().iniciar();
                    break;

                default:
                    System.out.println("Rol no reconocido.");
            }

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }



    private void mostrarMenu() {

        String viewMenu = """
              ..|'''.| '||''|.   '||''''|  '||''|.   '||' '||' '|'      |    
            .|'     '   ||   ||   ||  .     ||   ||   ||    || |      |||   
            ||          ||''|'    ||''|     ||    ||  ||     ||      |  ||  
            '|.      .  ||   |.   ||        ||    ||  ||     ||     .''''|. 
             ''|....'  .||.  '|' .||.....| .||...|'  .||.   .||.   .|.  .||.
            """;

        String menuOpciones = """
                +-----------------------------------+
                |        * MENÚ PRINCIPAL *         |
                +-----------------------------------+
                | [1] Iniciar sesión                |
                | [0] Salir                         |
                +-----------------------------------+
                """;

        System.out.println(viewMenu);
        System.out.println(menuOpciones);
        System.out.print(">>> ");
    }
}
