package com.tati.views;

import java.util.List;
import java.util.Scanner;


import com.tati.controller.PrestamoController;
import com.tati.controller.ReporteController;
import com.tati.model.Prestamo;

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
                case 3 -> clientesMorosos();
                case 0 -> System.out.println("Volviendo al menú admin...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                            +------------------------+
                            |    MENÚ DE REPORTES    |
                            +------------------------+
                            |[1] Préstamos activos   |
                            |[2] Préstamos vencidos  |
                            |[3] Clientes morosos    |
                            +------------------------+
                            |[0] Volver al menú admin|
                            +------------------------+
                            """);
        System.out.print(">>> Opción: ");
    }

    private void prestamosActivos() {

        System.out.println("=== PRÉSTAMOS ACTIVOS ===");

        List<Prestamo> prestamos = prestamoController.listarTodosPrestamos();

        prestamos.stream()
            .filter(p -> p.getEstado().name().equals("PENDIENTE"))
            .forEach(System.out::println);
    }

    private void prestamosVencidos() {

        System.out.println("=== PRÉSTAMOS VENCIDOS ===");

        List<Prestamo> prestamos = prestamoController.listarTodosPrestamos();

        prestamos.stream()
            .filter(p -> p.getEstado().name().equals("VENCIDO"))
            .forEach(System.out::println);
    }
    private void clientesMorosos() {

        System.out.println("=== CLIENTES MOROSOS ===");

        var clientes = reporteController.clientesMorosos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes morosos.");
            return;
        }

        clientes.forEach(c ->
            System.out.println(
                "ID: " + c.getId() +
                " | Nombre: " + c.getNombre()
            )
        );
    }




}


