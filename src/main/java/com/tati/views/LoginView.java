package com.tati.views;

import com.tati.controller.LoginController;
import com.tati.model.Usuario;

import java.util.Scanner;

public class LoginView {

    private final LoginController loginController;
    private final Scanner scanner = new Scanner(System.in);

    public LoginView(LoginController loginController) {
        this.loginController = loginController;
    }

    public Usuario iniciarSesion() {
        System.out.println("===== INICIO DE SESIÓN =====");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        return loginController.login(usuario, contrasena);
    }
}
