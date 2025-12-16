package com.tati.views;

import java.util.Scanner;

import com.tati.model.Cliente;
import com.tati.model.Prestamo;
import com.tati.controller.PrestamoController;
import com.tati.repository.prestamo.PrestamoDBRepository;
import com.tati.service.prestamo.PrestamoServiceImpl;
import com.tati.controller.PagoController;
import com.tati.repository.pago.PagoDBRepository;
import com.tati.service.pago.PagoServiceImpl;
import java.time.LocalDate;


public class MenuCliente {
    private final Cliente cliente;
    private final Scanner scan = new Scanner(System.in);
    private final PrestamoController prestamoController;
    private final PagoController pagoController;


    public MenuCliente(Cliente cliente) {
        this.cliente = cliente;

        PrestamoDBRepository prestamoRepo = new PrestamoDBRepository();
        PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(prestamoRepo);
        this.prestamoController = new PrestamoController(prestamoService);

        PagoDBRepository pagoRepo = new PagoDBRepository();
        PagoServiceImpl pagoService = new PagoServiceImpl(pagoRepo, prestamoRepo);
        this.pagoController = new PagoController(pagoService);
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
                        | [3] Ver historial de pagos        |
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
                pagarCuota();
                break;
            case 3:
                verHistorialPagos();
                break;
            case 4:
                simularPrestamo();
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    private void consultarMisPrestamos() {
        
        System.out.println("=== MIS PRÉSTAMOS ===");
        var prestamos = prestamoController.listarPrestamosPorCliente(cliente.getId());

        if (prestamos.isEmpty()) {
            System.out.println("No tienes préstamos registrados.");
            return;
        }
        prestamos.forEach(p -> {
        System.out.println("""
            ID: %d
            Saldo pendiente: %.2f
            Cuota mensual: %.2f
            Fecha de la cuota: %s
            Estado: %s
            -------------------------
            """.formatted(
                p.getId(),
                p.getSaldoPendiente(),
                p.calcularCuotaMensual(),
                p.getFechaCuotaActual(),
                p.getEstado()
        ));
    });

    }
    private void pagarCuota() {

        System.out.println("=== MIS PRÉSTAMOS ===");

        var prestamos = prestamoController.listarPrestamosPorCliente(cliente.getId());

        if (prestamos.isEmpty()) {
            System.out.println("No tienes préstamos activos.");
            return;
        }

        prestamos.forEach(p -> {
            System.out.println("""
                ID: %d
                Saldo pendiente: %.2f
                Cuota mensual: %.2f
                Fecha de la cuota: %s
                Estado: %s
                -------------------------
                """.formatted(
                    p.getId(),
                    p.getSaldoPendiente(),
                    p.calcularCuotaMensual(),
                    p.getFechaCuotaActual(),
                    p.getEstado()
            ));
        });

        System.out.print("Ingrese ID del préstamo: ");
        int idPrestamo = Integer.parseInt(scan.nextLine());


        var prestamoSeleccionado = prestamos.stream()
                .filter(p -> p.getId() == idPrestamo)
                .findFirst()
                .orElse(null);

        if (prestamoSeleccionado == null) {
            System.out.println("Préstamo no encontrado.");
            return;
        }


        System.out.println(
            "La cuota de este mes es: " +
            prestamoSeleccionado.calcularCuotaMensual()
        );

        System.out.println(
            "Fecha de pago correspondiente: " +
            prestamoSeleccionado.getFechaCuotaActual()
        );

        System.out.print("Ingrese monto a pagar: ");
        double monto = Double.parseDouble(scan.nextLine());

        try {

            pagoController.registrarPago(idPrestamo, monto, null);
            System.out.println("Pago registrado correctamente");
        } catch (Exception e) {
            System.out.println("Error al registrar pago: " + e.getMessage());
        }
    }


    private void verHistorialPagos() {

        consultarMisPrestamos();

        System.out.print("Ingrese ID del préstamo: ");
        int idPrestamo = Integer.parseInt(scan.nextLine());

        var pagos = pagoController.listarPagosPorPrestamo(idPrestamo);

        if (pagos.isEmpty()) {
            System.out.println("Este préstamo no tiene pagos registrados.");
            return;
        }

        System.out.println("=== HISTORIAL DE PAGOS ===");
        pagos.forEach(p -> {
            System.out.println("""
                Fecha: %s | Monto: %.2f
                """.formatted(
                    p.getFechaPago(),
                    p.getMonto()
            ));
        });
    }

    private void simularPrestamo() {

    System.out.println("=== SIMULACIÓN DE PRÉSTAMO ===");

    try {
        System.out.print("Ingrese monto solicitado: ");
        double monto = Double.parseDouble(scan.nextLine());

        System.out.print("Ingrese interés (%): ");
        double interes = Double.parseDouble(scan.nextLine());

        System.out.print("Ingrese número de cuotas (meses): ");
        int cuotas = Integer.parseInt(scan.nextLine());

        Prestamo simulacion = new Prestamo();
        simulacion.setMonto(monto);
        simulacion.setInteres(interes);
        simulacion.setCuotas(cuotas);

        simulacion.inicializarPrestamo();

        System.out.println("""
            -------------------------------
            MONTO SOLICITADO : %.2f
            INTERÉS          : %.2f%%
            TOTAL A PAGAR    : %.2f
            CUOTA MENSUAL    : %.2f
            FECHA INICIO     : %s
            FECHA FIN        : %s
            -------------------------------
            """.formatted(
                monto,
                interes,
                simulacion.calcularMontoTotal(),
                simulacion.calcularCuotaMensual(),
                simulacion.getFechaInicio(),
                simulacion.getFechaVencimiento()
        ));

    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida. Intente de nuevo.");
    }
}

    

}
