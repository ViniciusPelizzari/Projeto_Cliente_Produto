package org.example.Client;

import org.example.Empresa.ConsultaCNPJ;
import org.example.Empresa.ResultadoCNPJ;
import org.example.User.RegistrationUser;
import org.example.Empresa.ConsultaCEP;
import org.example.ToEnter.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientRegistration {

    static Scanner s = new Scanner(System.in);
    public static int opc_cliente;

    public static String cep_cpf;

    public static void ReadCep(){
        System.out.print("CEP: ");
        cep_cpf = s.nextLine();
        ConsultaCEP.ConsultaCEPcomPerguntas(cep_cpf);
    }

    public static void Cliente(Connection connection) throws SQLException {

        System.out.println("1 - PESSOA FÍSICA | 2 - PESSOA JURÍDICA");
        opc_cliente = Integer.parseInt(s.nextLine());
        if (opc_cliente == 1) {
            Login.CPF();
            String usuarioCpf = Login.usuarioCpf;
            boolean verif = false;
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cliente WHERE cpf_cliente = ?");
                stmt.setString(1, usuarioCpf);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Registered customer\n");
                    verif = true;
                } else {

                    ResultadoCNPJ.Nome();
                    ReadCep();
                    RegistrationUser.Email();
                    RegistrationUser.Telefone();
                    ResultadoCNPJ.Obs();

                    ConsultaCNPJ.DbUploadClienteCPF(connection);
                }
            } catch (SQLException e) {

                ResultadoCNPJ.Nome();
                ReadCep();
                RegistrationUser.Email();
                RegistrationUser.Telefone();
                ResultadoCNPJ.Obs();

                ConsultaCNPJ.DbUploadClienteCPF(connection);
            }
        } else if (opc_cliente == 2) {
            RegistrationUser.CNPJ();
            String usuarioCnpj = RegistrationUser.usuarioCnpj;
            boolean verif = false;
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cliente WHERE cnpj_cliente = ?");
                stmt.setString(1, usuarioCnpj);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Registered customer\n");
                    verif = true;
                } else {

                    ConsultaCNPJ.ConsultaCNPJ();
                    ResultadoCNPJ.Obs();
                    ConsultaCNPJ.DbUploadClienteCNPJ(connection);
                }
            } catch (SQLException e) {

                ConsultaCNPJ.ConsultaCNPJ();
                ResultadoCNPJ.Obs();
                ConsultaCNPJ.DbUploadClienteCNPJ(connection);
            }
        }
    }
}

