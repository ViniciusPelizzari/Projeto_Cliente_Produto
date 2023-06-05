package org.example;

import org.example.ToEnter.Login;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SystemExecution {
    public static String dataEnvioDB;
    public static Time horaEnvioDB;

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5433/Projeto";
            String user = "postgres";
            String password = "20032006";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("STATUS: Connection made successfully");

            Login.ValidLogin(connection);

        } catch (ClassNotFoundException e) {
            System.out.println("STATUS: Database not found");
        } catch (SQLException e) {
            Logger.getLogger(SystemExecution.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    Logger.getLogger(SystemExecution.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    public static void DataHora() {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);
        System.out.println(dataFormatada);

        LocalDate dataFormatadaDB = LocalDate.parse(dataFormatada, formatter);
        dataEnvioDB = dataFormatadaDB.toString();

        LocalTime horaAtual = LocalTime.now();
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = horaAtual.format(formatter);
        System.out.println(horaFormatada);

        String horaFormatadaString = horaFormatada;
        horaEnvioDB = java.sql.Time.valueOf(horaFormatadaString);
    }
}

//ALTER SEQUENCE tabela_exemplo_id_seq RESTART WITH 1;
