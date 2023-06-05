package org.example;

import org.example.Empresa.ConsultaCNPJ;
import org.example.Empresa.ResultadoCNPJ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TesteConexao {
    static Scanner s = new Scanner(System.in);
    static String nome;

    public static void TesteC(Connection connection) throws SQLException {

        PreparedStatement stmt1 = connection.prepareStatement("INSERT INTO teste (testecol) VALUES (?)");
        stmt1.setString(1, nome);
        int rowsAffected = stmt1.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data successfully inserted!");
        } else {
            System.out.println("Failed to insert data.");
        }
    }
}
