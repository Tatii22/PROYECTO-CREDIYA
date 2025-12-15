package com.tati.views;

import java.util.Scanner;

import com.tati.model.Cliente;
import com.tati.controller.PrestamoController;
import com.tati.repository.prestamo.PrestamoDBRepository;
import com.tati.service.prestamo.PrestamoServiceImpl;


public class MenuCliente {
    private final Cliente cliente;
    private final Scanner scan = new Scanner(System.in);
    private final PrestamoController prestamoController;

    public MenuCliente(Cliente cliente) {
        this.cliente = cliente;

        PrestamoDBRepository prestamoRepo = new PrestamoDBRepository();
        PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(prestamoRepo);
        this.prestamoController = new PrestamoController(prestamoService);
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
        .--. .-.   .-. .--. .-..-..-----. .--. 
        : .--': :   : :: .--': `: :`-. .-': .--'
        : :   : :   : :: `;  : .` :  : :  : `;  
        : :__ : :__ : :: :__ : :. :  : :  : :__ 
        `.__.':___.':_;`.__.':_;:_;  :_;  `.__.'
        """;
    
   
    String menuOpciones = """
                        +-----------------------------------+
                        |          * Bienvenid@ *           |
                        +-----------------------------------+
                        | [1] Consultar mis préstamos       |
                        | [2] Pagar cuota                   |
                        | [3] Ver mi estado general         |
                        | [4] Ver simulación de prestamo    |
                        +-----------------------------------+
                        | [0] Volver al menu pricipal       |
                        +-----------------------------------+
        """;


    System.out.println("\n" + viewMenu);
    System.out.println(menuOpciones);
    System.out.print(">>> Ingrese su opción: ");
}
    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                consultarMisPrestamos();
                break;
            case 2:
                System.out.println("Pagar cuota - En construcción");
                break;
            case 3:
                System.out.println("Ver mi estado general - En construcción");
                break;
            case 4:
                System.out.println("Ver simulación de prestamo - En construcción");
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    public void consultarMisPrestamos() {
        System.out.println("=== MIS PRÉSTAMOS ===");
        var prestamos = prestamoController.listarPrestamosPorCliente(cliente.getId());

        if (prestamos.isEmpty()) {
            System.out.println("No tienes préstamos registrados.");
            return;
        }
        prestamos.forEach(p -> {
        System.out.println("""
            -------------------------
            ID Préstamo: %d
            Monto: %.2f
            Interés: %.2f%%
            Cuotas: %d
            Saldo pendiente: %.2f
            Estado: %s
            -------------------------
            """.formatted(
                p.getId(),
                p.getMonto(),
                p.getInteres(),
                p.getCuotas(),
                p.getSaldoPendiente(),
                p.getEstado()
        ));
    });
    }
}
