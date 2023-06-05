package org.example.ToEnter;

import org.example.User.RegistrationUser;
import org.example.Input.InputOptions;
import org.example.Methods.Validation;
import java.sql.*;
import java.util.Scanner;

public class Login {

    //data reading
    public static final Scanner s = new Scanner(System.in);
    public static String usuarioCpf;
    public static int id;

    public static void CPF() {
        System.out.println("Type the CPF: ");
        usuarioCpf = s.nextLine();
        int sizeCPF = usuarioCpf.length();
        while (sizeCPF != 11 || !Validation.CPFValidation(usuarioCpf)) {
            System.out.println("Invalid CPF! Please enter a valid CPF: ");
            usuarioCpf = s.nextLine();
            sizeCPF = usuarioCpf.length();
        }
    }

    public static void ID(Connection connection) {
        CPF();
        String cpf = usuarioCpf;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM login WHERE cpf_login = ?");
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = Integer.parseInt(rs.getString("id_empresa_login"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ValidLogin(Connection connection) {
        ID(connection);
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM login WHERE cpf_login = ?");
            stmt.setString(1, usuarioCpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String user = rs.getString("usuario_login");
                System.out.println("Olá, " + user);
                String passwordConf = rs.getString("senha_login");
                RegistrationUser.Password();
                String password = RegistrationUser.password;

                if (passwordConf.equals(password)) {
                    System.out.println("Allowed entry");
                    InputOptions.Option(connection);
                } else {
                    System.out.println("Incorrect password");
                    int attempts = 0;
                    while (attempts < 2 && !passwordConf.equals(password)) {
                        int tent = 2 - attempts;
                        System.out.println("You have " + tent + " tries left.");
                        RegistrationUser.Password();
                        password = RegistrationUser.password;
                        attempts++;
                    }
                    if (passwordConf.equals(password)) {
                        System.out.println("Allowed entry");
                        InputOptions.Option(connection);
                    } else {
                        System.out.println("Blocked user");
                        System.out.println("Contact the call center to verify your activity");
                    }
                }
            } else {
                System.out.println("User not found");
                RegistrationUser.Cadastrar(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
