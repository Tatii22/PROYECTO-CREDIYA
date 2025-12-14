package com.tati.views;

import java.util.Scanner;

import com.tati.model.Cliente;

public class MenuCliente {
    private final Cliente cliente;
    private final Scanner scan = new Scanner(System.in);

    public MenuCliente(Cliente cliente) {
        this.cliente = cliente;
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
                System.out.println("Consultar mis préstamos - En construcción");
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
}
