package com.tati.views;

import java.util.List;
import java.util.Scanner;


import com.tati.controller.PrestamoController;
import com.tati.controller.ReporteController;
import com.tati.model.EstadoPrestamo;
import com.tati.model.GestorPrestamos;

public class MenuReportes {

    private final PrestamoController prestamoController;
    private final Scanner scan = new Scanner(System.in);
    private final ReporteController reporteController;


    public MenuReportes(PrestamoController prestamoController,
                    ReporteController reporteController) {
    this.prestamoController = prestamoController;
    this.reporteController = reporteController;
}

    public void iniciar() {

        int opcion = -1;

        do {
            mostrarMenu();
            opcion = Integer.parseInt(scan.nextLine());

            switch (opcion) {
                case 1 -> prestamosActivos();
                case 2 -> prestamosVencidos();
                case 3 -> listarPrestamos();
                case 0 -> System.out.println("Volviendo al menú admin...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                            +----------------------------------+
                            |          MENÚ DE EXAMEN          |
                            +----------------------------------+
                            |[1] Préstamos activos             |
                            |[2] Préstamos vencidos            |
                            |[3] Mostrar resumen               |
                            +----------------------------------+
                            |[0] Volver al menú admin          |
                            +----------------------------------+
                            """);
        System.out.print(">>> Opción: ");
    }

    private void prestamosActivos() {

        System.out.println("=== PRÉSTAMOS ACTIVOS ===");

        List<GestorPrestamos> prestamos = prestamoController.listarTodosPrestamos();

        prestamos.stream()
            .filter(p -> p.getEstado().name().equals("PENDIENTE"))
            .forEach(System.out::println);
    }

    private void prestamosVencidos() {

        System.out.println("=== PRÉSTAMOS VENCIDOS ===");

        List<GestorPrestamos> prestamos = prestamoController.listarTodosPrestamos();

        prestamos.stream()
            .filter(p -> p.getEstado().name().equals("VENCIDO"))
            .forEach(System.out::println);
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


}


