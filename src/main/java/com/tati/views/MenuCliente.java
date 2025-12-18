package com.tati.views;

import java.util.Scanner;

import com.tati.model.Cliente;
import com.tati.model.EstadoPrestamo;
import com.tati.model.Prestamo;
import com.tati.controller.PrestamoController;
import com.tati.controller.PagoController;
import com.tati.controller.HistorialEstadoPrestamoController;

import com.tati.repository.prestamo.PrestamoDBRepository;
import com.tati.repository.pago.PagoDBRepository;
import com.tati.repository.historial.HistorialEstadoPrestamoDBRepository;

import com.tati.service.prestamo.PrestamoServiceImpl;
import com.tati.service.pago.PagoServiceImpl;
import com.tati.service.historial.HistorialEstadoPrestamoServiceImpl;

public class MenuCliente {

    private final Cliente cliente;
    private final Scanner scan = new Scanner(System.in);

    private final PrestamoController prestamoController;
    private final PagoController pagoController;
    private final HistorialEstadoPrestamoController historialController;

    public MenuCliente(Cliente cliente) {
        this.cliente = cliente;

        // ===== Repositorios =====
        PrestamoDBRepository prestamoRepo = new PrestamoDBRepository();
        PagoDBRepository pagoRepo = new PagoDBRepository();
        HistorialEstadoPrestamoDBRepository historialRepo =
                new HistorialEstadoPrestamoDBRepository();

        // ===== Servicios =====
        PrestamoServiceImpl prestamoService =
                new PrestamoServiceImpl(prestamoRepo);

        PagoServiceImpl pagoService =
                new PagoServiceImpl(pagoRepo, prestamoRepo, historialRepo);

        HistorialEstadoPrestamoServiceImpl historialService =
                new HistorialEstadoPrestamoServiceImpl(historialRepo);

        // ===== Controladores =====
        this.prestamoController = new PrestamoController(prestamoService);
        this.pagoController = new PagoController(pagoService);
        this.historialController =
                new HistorialEstadoPrestamoController(historialService);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scan.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
                opcion = -1;
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
                        | [3] Ver historial de pagos        |
                        | [4] Ver simulación de prestamo    |
                        | [5] Pagar cuota vencida (con mora)|
                        | [6] Ver historial del préstamo    |
                        +-----------------------------------+
                        | [0] Volver al menu pricipal       |
                        +-----------------------------------+
        """;


    System.out.println("\n" + viewMenu);
    System.out.println(menuOpciones);
    System.out.print(">>> Ingrese su opción: ");
}
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> consultarMisPrestamos();
            case 2 -> pagarCuota();
            case 3 -> verHistorialPagos();
            case 4 -> simularPrestamo();
            case 5 -> pagarCuotaVencida();
            case 6 -> verHistorialPrestamo();
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }

    private void consultarMisPrestamos() {
        var prestamos =
                prestamoController.listarPrestamosPorCliente(cliente.getId());

        if (prestamos.isEmpty()) {
            System.out.println("No tienes préstamos.");
            return;
        }

        prestamos.forEach(p -> System.out.println("""
            ID: %d
            Saldo pendiente: %.2f
            Cuota mensual: %.2f
            Fecha cuota: %s
            Estado: %s
            -------------------------
            """.formatted(
                p.getId(),
                p.getSaldoPendiente(),
                p.calcularCuotaMensual(),
                p.getFechaCuotaActual(),
                p.getEstado()
        )));
    }

    private void pagarCuota() {
        consultarMisPrestamos();

        System.out.print("ID del préstamo: ");
        int id = Integer.parseInt(scan.nextLine());

        System.out.print("Monto a pagar: ");
        double monto = Double.parseDouble(scan.nextLine());

        try {
            pagoController.registrarPago(id, monto, null);
            System.out.println("Pago registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verHistorialPagos() {
        consultarMisPrestamos();

        System.out.print("ID del préstamo: ");
        int id = Integer.parseInt(scan.nextLine());

        var pagos = pagoController.listarPagosPorPrestamo(id);

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados.");
            return;
        }

        pagos.forEach(p ->
                System.out.println("Fecha: " + p.getFechaPago()
                        + " | Monto: " + p.getMonto()));
    }

    private void pagarCuotaVencida() {
        var prestamos =
                prestamoController.listarPrestamosPorCliente(cliente.getId());

        var vencidos = prestamos.stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.VENCIDO)
                .toList();

        if (vencidos.isEmpty()) {
            System.out.println("No tienes préstamos vencidos.");
            return;
        }

        vencidos.forEach(p -> {
            double mora = p.calcularMora(0.10);
            double total = p.calcularCuotaMensual() + mora;

            System.out.println("""
                ID: %d
                Cuota: %.2f
                Mora (10%%): %.2f
                Total: %.2f
                -------------------------
                """.formatted(
                    p.getId(),
                    p.calcularCuotaMensual(),
                    mora,
                    total
            ));
        });

        System.out.print("ID del préstamo: ");
        int id = Integer.parseInt(scan.nextLine());

        System.out.print("Monto a pagar: ");
        double monto = Double.parseDouble(scan.nextLine());

        try {
            pagoController.pagarCuotaVencida(id, monto);
            System.out.println("Pago con mora registrado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verHistorialPrestamo() {
        consultarMisPrestamos();

        System.out.print("ID del préstamo: ");
        int id = Integer.parseInt(scan.nextLine());

        var historial = historialController.verHistorial(id);

        if (historial.isEmpty()) {
            System.out.println("No hay cambios de estado.");
            return;
        }

        historial.forEach(h -> System.out.println("""
            Fecha: %s
            %s → %s
            -------------------------
            """.formatted(
                h.getFechaCambio(),
                h.getEstadoAnterior(),
                h.getEstadoNuevo()
        )));
    }

    private void simularPrestamo() {
        try {
            System.out.print("Monto: ");
            double monto = Double.parseDouble(scan.nextLine());

            System.out.print("Interés (%): ");
            double interes = Double.parseDouble(scan.nextLine());

            System.out.print("Cuotas: ");
            int cuotas = Integer.parseInt(scan.nextLine());

            Prestamo p = new Prestamo();
            p.setMonto(monto);
            p.setInteres(interes);
            p.setCuotas(cuotas);
            p.inicializarPrestamo();

            System.out.println("""
                Total a pagar: %.2f
                Cuota mensual: %.2f
                """.formatted(
                    p.calcularMontoTotal(),
                    p.calcularCuotaMensual()
            ));
        } catch (Exception e) {
            System.out.println("Datos inválidos.");
        }
    }
}




