package com.tati.views;

import java.util.Scanner;

public class MenuEmpleado {
    private Scanner scan;

    public MenuEmpleado() {
        scan = new Scanner(System.in);
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
        .--. .-..-..---. .-.    .--.  .--. .---.  .--. 
        : .--': `' :: .; :: :   : .--': .; :: .  :: ,. :
        : `;  : .. ::  _.': :   : `;  :    :: :: :: :: :
        : :__ : :; :: :   : :__ : :__ : :: :: :; :: :; :
        `.__.':_;:_;:_;   :___.'`.__.':_;:_;:___.'`.__.'
        """;
    
   
    String menuOpciones = """
                        +-----------------------------------+
                        |          * Bienvenid@ *           |
                        +-----------------------------------+
                        | [1] Registrar clientes            |
                        | [2] Crear préstamos               |
                        | [3] Registrar pagos               |
                        | [4] Consultar prestamos           |
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
                System.out.println("Registrar clientes - En construcción");
                break;
            case 2:
                System.out.println("Crear prestamos - En construcción");
                break;
            case 3:
                System.out.println("Registrar pagos - En construcción");
                break;
            case 4:
                System.out.println("Consultar préstamos - En construcción");
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
        }
    }
    

}
