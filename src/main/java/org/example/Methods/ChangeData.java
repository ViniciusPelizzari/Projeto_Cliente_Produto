package org.example.Methods;

import org.example.Empresa.ConsultaCEP;
import org.example.Input.InputOptions;
import org.example.ToEnter.Login;
import org.example.User.RegistrationUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.Empresa.ConsultaCEP.*;

public class ChangeData {

    static Scanner s = new Scanner(System.in);

    public static void ChangeUserPassword(Connection connection) {
        System.out.println("ALERT");
        System.out.println("Changing data\n");

        Login.CPF();

        String newUser;
        String newPassword;
        int opcao;
        String usuarioCpf = Login.usuarioCpf;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM login WHERE cpf_login = ?");
            stmt.setString(1, usuarioCpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordConf = rs.getString("senha_login");
                System.out.println("Type your password: ");
                String password = s.nextLine();

                if (passwordConf.equals(password)) {
                    System.out.println("Allowed access");
                    System.out.println("1 - Change User | 2 - Change Password | 3 - Change Email | 4 - Change Phone");
                    opcao = Integer.parseInt(s.nextLine());
                    switch (opcao) {
                        case 1 -> {
                            System.out.println("Enter the new user:");
                            newUser = s.nextLine();
                            stmt = connection.prepareStatement("UPDATE login SET usuario_login = ? WHERE cpf_login = ?");
                            stmt.setString(1, newUser);
                            stmt.setString(2, usuarioCpf);
                            int result = stmt.executeUpdate();
                            if (result > 0) {
                                System.out.println("Successfully changed password");
                                InputOptions.Option(connection);
                            } else {
                                System.out.println("Could not change password");
                                InputOptions.Option(connection);
                            }
                        }
                        case 2 -> {
                            System.out.println("Enter the new password:");
                            newPassword = s.nextLine();
                            stmt = connection.prepareStatement("UPDATE login SET senha_login = ? WHERE cpf_login = ?");
                            stmt.setString(1, newPassword);
                            stmt.setString(2, usuarioCpf);
                            int result = stmt.executeUpdate();
                            if (result > 0) {
                                System.out.println("Successfully changed password");
                                InputOptions.Option(connection);
                            } else {
                                System.out.println("Could not change password");
                                InputOptions.Option(connection);
                            }
                        }
                        case 3 -> {
                            RegistrationUser.Email();
                            String email = RegistrationUser.email;
                            stmt = connection.prepareStatement("UPDATE login SET email_login = ? WHERE cpf_login = ?");
                            stmt.setString(1, email);
                            stmt.setString(2, usuarioCpf);
                            int result = stmt.executeUpdate();
                            if (result > 0) {
                                System.out.println("Successfully changed password");
                                InputOptions.Option(connection);
                            } else {
                                System.out.println("Could not change password");
                                InputOptions.Option(connection);
                            }
                        }
                        case 4 -> {
                            RegistrationUser.Telefone();
                            String telefone = RegistrationUser.telefone;
                            stmt = connection.prepareStatement("UPDATE login SET telefone_login = ? WHERE cpf_login = ?");
                            stmt.setString(1, telefone);
                            stmt.setString(2, usuarioCpf);
                            int result = stmt.executeUpdate();
                            if (result > 0) {
                                System.out.println("Successfully changed password");
                                InputOptions.Option(connection);
                            } else {
                                System.out.println("Could not change password");
                                InputOptions.Option(connection);
                            }
                        }
                        default -> {
                            System.out.println(" ");
                            InputOptions.Option(connection);
                        }
                    }
                } else {
                    System.out.println("Could not proceed! Incorrect username or password");
                    InputOptions.Option(connection);
                }
            } else {
                System.out.println("User not found");
                InputOptions.Option(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void ChangeCompany(Connection connection) {
        System.out.println("ALERT");
        System.out.println("Change of company data\n");

        Login.CPF();
        String usuarioCpf = Login.usuarioCpf;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM login WHERE cpf_login = ?");
            stmt.setString(1, usuarioCpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordConf = rs.getString("senha_login");
                System.out.println("Type your password: ");
                String password = s.nextLine();
                String cnpj_login = rs.getString("cnpj_login");


                if (passwordConf.equals(password)) {
                    System.out.println("Allowed entry");
                    System.out.println("Enter the CNPJ of the company you want to update:");
                    String cnpj = s.nextLine();
                    if(cnpj.equals(cnpj_login)) {
                        try {
                            stmt = connection.prepareStatement("SELECT * FROM empresa WHERE cnpj_empresa = ?");
                            stmt.setString(1, cnpj);
                            rs = stmt.executeQuery();

                            if (rs.next()) {
                                System.out.print("Corporate reason: ");
                                String razSocial = s.nextLine();
                                System.out.print("Fantasy name: ");
                                String fantasia = s.nextLine();
                                System.out.print("State registration: ");
                                int inscEstadual = s.nextInt();
                                ConsultaCEP.Dados();
                                System.out.print("Company type: ");
                                String tpEmpresa = s.nextLine();

                                stmt = connection.prepareStatement("UPDATE empresa SET razao_social_empresa = ?, nome_fantasia_empresa = ?, inscricao_estadual_empresa = ?, logradouro_empresa = ?," +
                                        " numero_endereco_empresa = ?, complemento_empresa = ?, bairro_empresa = ?, cidade_empresa = ?, estado_empresa = ?, cep_empresa = ?," +
                                        " email_empresa = ?, telefone_empresa = ?, tipo_empresa_empresa = ? WHERE cnpj_empresa = ?");

                                stmt.setString(1, razSocial);
                                stmt.setString(2, fantasia);
                                stmt.setInt(3, inscEstadual);
                                stmt.setString(4, logradouro);
                                stmt.setInt(5, num);
                                stmt.setString(6, complemento);
                                stmt.setString(7, bairro);
                                stmt.setString(8, cidade);
                                stmt.setString(9, uf);
                                stmt.setString(10, cep);
                                stmt.setString(11, email);
                                stmt.setString(12, telefone);
                                stmt.setString(13, tpEmpresa);
                                stmt.setString(14, cnpj);
                                int rowsUpdated = stmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("\nSuccessfully updated company data");
                                    InputOptions.Option(connection);
                                } else {
                                    System.out.println("Could not update company data");
                                    InputOptions.Option(connection);
                                }
                            } else {
                                System.out.println("Company not found");
                                InputOptions.Option(connection);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else{
                        System.out.println("Company not found");
                        InputOptions.Option(connection);
                    }
                } else {
                    System.out.println("Could not proceed! Incorrect username or password");
                    InputOptions.Option(connection);
                }
            } else {
                System.out.println("Could not proceed! Incorrect username or password");
                InputOptions.Option(connection);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }*/
}
