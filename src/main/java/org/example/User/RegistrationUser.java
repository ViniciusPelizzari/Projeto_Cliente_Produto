package org.example.User;

import org.example.Empresa.ConsultaCNPJ;
import org.example.SystemExecution;
import org.example.ToEnter.Login;
import org.example.Input.InputOptions;
import org.example.Methods.Validation;

import java.sql.*;
import java.util.Scanner;

import static org.example.SystemExecution.dataEnvioDB;
import static org.example.SystemExecution.horaEnvioDB;

public class RegistrationUser {

    //data reading
    public static final Scanner s = new Scanner(System.in);

    public static String usuarioCnpj;

    public static String usuario;

    public static String password;
    //checking password
    private static String passwordConf;

    private static String opRegister;
    public static int idEmpresa;
    public static String email;
    public static String telefone;

    public static void Password() {
        System.out.print("Type your password: ");
        password = s.nextLine();
    }

    public static void PasswordConf() {
        System.out.print("Confirm your password: ");
        passwordConf = s.nextLine();
        while (!passwordConf.equals(password)) {
            System.out.println("Passwords do not match! Please try again.");
            passwordConf = s.nextLine();
            if (!passwordConf.equals(password)) {
            }
        }
    }

    public static void Email() {
        Scanner scanner = new Scanner(System.in);

        boolean validEmail = false;

        while (!validEmail) {
            System.out.print("Email: ");
            email = scanner.nextLine();

            if (Validation.EmailInput.validateEmail(email)) {
                validEmail = true;
            } else {
                System.out.println("The email entered is not valid. Try again.");
            }
        }
    }

    public static void Telefone() {
        Scanner scanner = new Scanner(System.in);
        telefone = "";

        while (true) {
            System.out.print("Telephone: ");
            telefone = scanner.nextLine();

            if (Validation.ValidatePhoneNumber(telefone)) {
                break; // sair do loop se o número de telefone for válido
            } else {
                System.out.println("The phone number entered is not valid. Try again.");
            }
        }
    }


    public static void CNPJ() {
        System.out.print("Type your CNPJ: ");
        usuarioCnpj = s.nextLine();
        usuarioCnpj = usuarioCnpj.replace(".", "").replace("/", "").replace("-", "");
        int sizeCNPJ = usuarioCnpj.length();
        while (sizeCNPJ != 14) {
            System.out.println("Invalid size! Enter your CNPJ again: ");
            usuarioCnpj = s.nextLine();
            sizeCNPJ = usuarioCnpj.length();
        }
    }

    public static void Usuario() {
        System.out.println("Enter your username");
        usuario = s.nextLine();
    }

    public static void CadastrarOp() {
        System.out.println("Do you want to register?");
        System.out.println("Type 'YES' or 'NO'");
        opRegister = s.nextLine();
    }

    public static void Cadastrar(Connection connection) throws SQLException {
        CadastrarOp();
        if (opRegister.equals("Yes") || opRegister.equals("yes") || opRegister.equals("YES")) {
            Password();
            PasswordConf();
            System.out.println("STATUS: Passwords match!");
            Usuario();
            Email();
            Telefone();
            CNPJ();

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM empresa WHERE cnpj_empresa = ?");
            stmt.setString(1, usuarioCnpj);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Integer.parseInt(rs.getString("id_empresa_login"));
                System.out.println("Registered company");
            } else {
                ConsultaCNPJ.ConsultaCNPJ();
                ConsultaCNPJ.DbUploadEmpresa(connection);
                System.out.println("\nSTATUS: Company successfully registered!");
            }

            PreparedStatement stmt1 = connection.prepareStatement("SELECT id_empresa_login FROM empresa WHERE cnpj_empresa = ?");
            stmt1.setString(1, usuarioCnpj);
            ResultSet rs1 = stmt1.executeQuery();

            if (rs1.next()) {
                idEmpresa = rs1.getInt("id_empresa_login");

                SystemExecution.DataHora();

                PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO login (usuario_login, cpf_login, senha_login, email_login, telefone_login, id_empresa_login, cnpj_login, dataEnvioInfo_login, horaEnvioInfo_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String usuarioCPF = Login.usuarioCpf;
                stmt2.setString(1, usuario);
                stmt2.setString(2, usuarioCPF);
                stmt2.setString(3, password);
                stmt2.setString(4, email);
                stmt2.setString(5, telefone);
                stmt2.setInt(6, idEmpresa);
                stmt2.setString(7, usuarioCnpj);
                stmt2.setDate(8, Date.valueOf(dataEnvioDB));
                stmt2.setTime(9, Time.valueOf(horaEnvioDB.toLocalTime()));
                stmt2.executeUpdate();

                System.out.println("\nSTATUS: Data successfully registered!");
                InputOptions.Option(connection);
            } else {
                System.out.println("Company not registered. Please register the company first.");
            }
        } else {
            System.out.println("OK! We respect your choice");
        }
    }
}

/*CREATE TABLE login(
id_cliente SERIAL PRIMARY KEY,
usuario_login VARCHAR(100),
cpf_login VARCHAR(11),
senha_login VARCHAR(33),
email_login VARCHAR(100),
telefone_login VARCHAR(100),
id_empresa_login VARCHAR(5),
cnpj_login VARCHAR(14),
dataEnvioInfo_login DATE,
horaEnvioInfo_login TIME);*/