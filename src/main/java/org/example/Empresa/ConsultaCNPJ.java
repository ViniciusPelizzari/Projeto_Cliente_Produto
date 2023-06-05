package org.example.Empresa;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Client.ClientRegistration;
import org.example.SystemExecution;
import org.example.ToEnter.Login;
import org.example.User.RegistrationUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

import static org.example.Empresa.ResultadoCNPJ.nome_cliente_cpf;
import static org.example.Empresa.ResultadoCNPJ.observacao_cliente;
import static org.example.SystemExecution.dataEnvioDB;
import static org.example.SystemExecution.horaEnvioDB;
import static org.example.ToEnter.Login.usuarioCpf;

public class ConsultaCNPJ {

    static Scanner s = new Scanner(System.in);

    public static String cnpj;
    public static String inscEstadual;
    public static String ibge;

    public static void InscEstadual() {
        System.out.print("State registration: ");
        inscEstadual = s.nextLine();
    }

    public static String getIbge() {
        return ibge;
    }

    public static void setIbge(String ibge) {
        ConsultaCNPJ.ibge = ibge;
    }

    public static ResultadoCNPJ resultado;
    public static void ConsultaCNPJ() {
        cnpj = RegistrationUser.usuarioCnpj;

        try {
            // URL da API do ReceitaWS com o CNPJ a ser consultado
            String url = "https://www.receitaws.com.br/v1/cnpj/" + cnpj;

            // Criação da conexão HTTP
            HttpURLConnection cnxConsultaCnpj = (HttpURLConnection) new URL(url).openConnection();
            cnxConsultaCnpj.setRequestMethod("GET");

            // Verifica se a requisição foi bem sucedida (código 200)
            if (cnxConsultaCnpj.getResponseCode() == 200) {
                // Leitura da resposta da API
                BufferedReader reader = new BufferedReader(new InputStreamReader(cnxConsultaCnpj.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                resultado = gson.fromJson(response.toString(), ResultadoCNPJ.class);

                //System.out.println(response.toString());
                System.out.println("Nome: " + resultado.getNome());
                System.out.println("CNPJ: " + resultado.getCnpj());
                InscEstadual();
                System.out.println("Situação: " + resultado.getSituacao());
                System.out.println("Abertura: " + resultado.getAbertura());

                ConsultaCEP.ConsultaCEPsemPerguntas(resultado.getCep().replace(".", "").replace("-", ""));
                if (resultado.getCep() == null || resultado.getCep().isEmpty()) {
                   ClientRegistration.ReadCep();
                } else{
                    System.out.println("CEP: " + resultado.getCep());
                    if (resultado.getBairro() == null || resultado.getBairro().isEmpty()) {
                        ConsultaCEP.getBairro();
                    } else{
                        System.out.println("Bairro: " + resultado.getBairro());
                        ConsultaCEP.setBairro(resultado.getBairro());
                    }

                    if (resultado.getComplemento() == null || resultado.getComplemento().isEmpty()) {
                        ConsultaCEP.Complement();
                    } else{
                        System.out.println("Complemento: " + resultado.getComplemento());
                        ConsultaCEP.setComplemento(resultado.getComplemento());
                    }

                    if (resultado.getLogradouro() == null || resultado.getLogradouro().isEmpty()) {
                        ConsultaCEP.Logradouro();
                    } else{
                        System.out.println("Logradouro: " + resultado.getLogradouro());
                        ConsultaCEP.setLogradouro(resultado.getLogradouro());
                    }

                    if (resultado.getNumero() == null || resultado.getNumero().isEmpty()/* || resultado.getNumero().equalsIgnoreCase("s/n") || resultado.getNumero().equalsIgnoreCase("S/n") || resultado.getNumero().equalsIgnoreCase("s/N") || resultado.getNumero().equalsIgnoreCase("S/N") || resultado.getNumero().equalsIgnoreCase("sn") || resultado.getNumero().equalsIgnoreCase("Sn") || resultado.getNumero().equalsIgnoreCase("sN") || resultado.getNumero().equalsIgnoreCase("SN")*/) {
                        ConsultaCEP.Num();
                    } else{
                        System.out.println("Number: " + resultado.getNumero());
                        ConsultaCEP.num = resultado.getNumero();
                    }

                    if (resultado.getMunicipio() == null || resultado.getMunicipio().isEmpty()) {
                        ConsultaCEP.City();
                    } else{
                        System.out.println("City: " + resultado.getMunicipio());
                        ConsultaCEP.setCidade(resultado.getMunicipio());
                    }

                    if (resultado.getUf() == null || resultado.getUf().isEmpty()) {
                        ConsultaCEP.State();
                    } else{
                        System.out.println("State: " + resultado.getUf());
                        ConsultaCEP.setUf(resultado.getUf());
                    }
                }

                System.out.println("Capital Social: " + resultado.getCapital_social());
                System.out.println("Data Situação: " + resultado.getData_situacao());
                System.out.println("Data Situação Especial: " + resultado.getData_situacao_especial());
                System.out.println("EFR: " + resultado.getEfr());

                if (resultado.getEmail() == null || resultado.getEmail().isEmpty()) {
                    RegistrationUser.Email();
                } else{
                    System.out.println(resultado.getEmail());
                    RegistrationUser.email = resultado.getEmail();
                }

                if (resultado.getTelefone() == null || resultado.getTelefone().isEmpty()) {
                    RegistrationUser.Telefone();
                } else{
                    System.out.println(resultado.getTelefone());
                    RegistrationUser.telefone = resultado.getTelefone();
                }

                if (resultado.getFantasia() == null || resultado.getFantasia().isEmpty()) {
                    resultado.setFantasia(resultado.getNome());
                    System.out.println(resultado.getFantasia());
                } else{
                    System.out.println(resultado.getFantasia());
                }

                System.out.println("Motivo Situação: " + resultado.getMotivo_situacao());
                System.out.println("Tipo: " + resultado.getTipo());
                System.out.println("Porte: " + resultado.getPorte());
                System.out.println("Natureza Jurídica: " + resultado.getNatureza_juridica());

                System.out.println("Última Atualização: " + resultado.getUltima_atualizacao());
                System.out.println("Status: " + resultado.getStatus());
                System.out.println("Situação Especial: " + resultado.getSituacao_especial());
                System.out.println("Data Situação Especial: " + resultado.getData_situacao_especial());

            } else {
                System.out.println("Erro na consulta. Código: " + cnxConsultaCnpj.getResponseCode());
            }

            // Fechamento da conexão
            cnxConsultaCnpj.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void codIBGE(){
        System.out.print("IBGE code: ");
        ibge = s.nextLine();
    }*/

    public static void DbUploadClienteCNPJ(Connection connection) throws SQLException {

        SystemExecution.DataHora();

        nome_cliente_cpf = null;
        usuarioCpf = null;

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO cliente (nome_cliente_cpf, cpf_cliente_cpf, email_cliente," + //3
                " telefone_cliente, logradouro_cliente, numero_cliente, complemento_cliente, municipio_cliente, " + //5
                " bairro_cliente, codIBGE_cliente, uf_cliente, cep_cliente, nome_empresa_cliente_cnpj, " + //5
                " fantasia_cliente_cnpj, cnpj_cliente_cnpj, iE_cliente_cnpj, situacao_cliente_cnpj, observacao_cliente, " + //5
                " abertura_cliente_cnpj, tipo_empresa_cliente_cnpj, porte_cliente_cnpj, natureza_juridica_cliente_cnpj, data_situacao_cliente_cnpj, " + //5
                " ultima_atualizacao_cliente_cnpj, status_cliente_cnpj, efr_cliente_cnpj, motivo_situacao_cliente_cnpj, " + //4
                " situacao_especial_cliente_cnpj, data_situacao_especial_cliente_cnpj, capital_social_cliente_cnpj, id_empresa_cliente, " + //4
                " dataEnvioInfo_cliente, horaEnvioInfo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, nome_cliente_cpf);
        stmt.setString(2, usuarioCpf);
        stmt.setString(3, RegistrationUser.email);
        stmt.setString(4, RegistrationUser.telefone.replace(".", "").replace("(", "").replace(")", "").replace("-", ""));
        stmt.setString(5, ConsultaCEP.getLogradouro());
        stmt.setString(6, String.valueOf(ConsultaCEP.num));
        stmt.setString(7, ConsultaCEP.getComplemento());
        stmt.setString(8, ConsultaCEP.getCidade());
        stmt.setString(9, ConsultaCEP.getBairro());
        stmt.setString(10, ConsultaCEP.getIbge());
        stmt.setString(11, ConsultaCEP.getUf());
        stmt.setString(12, ConsultaCEP.getCep());
        stmt.setString(13, resultado.getNome());
        stmt.setString(14, resultado.getFantasia());
        stmt.setString(15, resultado.getCnpj().replace(".", "").replace("/", "").replace("-", ""));
        stmt.setString(16, ConsultaCNPJ.inscEstadual);
        stmt.setString(17, resultado.getSituacao());
        stmt.setString(18, observacao_cliente);
        stmt.setString(19, resultado.getAbertura());
        stmt.setString(20, resultado.getTipo());
        stmt.setString(21, resultado.getPorte());
        stmt.setString(22, resultado.getNatureza_juridica());
        stmt.setString(23, resultado.getData_situacao());
        stmt.setString(24, resultado.getUltima_atualizacao());
        stmt.setString(25, resultado.getStatus());
        stmt.setString(26, resultado.getEfr());
        stmt.setString(27, resultado.getMotivo_situacao());
        stmt.setString(28, resultado.getSituacao_especial());
        stmt.setString(29, resultado.getData_situacao_especial());
        stmt.setString(30, resultado.getCapital_social());
        stmt.setInt(31, Login.id);
        stmt.setDate(32, Date.valueOf(dataEnvioDB));
        stmt.setTime(33, Time.valueOf(horaEnvioDB.toLocalTime()));

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data successfully inserted!");
        } else {
            System.out.println("Failed to insert data.");
        }
    }

    public static void DbUploadClienteCPF(Connection connection) throws SQLException {

        SystemExecution.DataHora();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO cliente (nome_cliente_cpf, cpf_cliente_cpf, email_cliente," + //3
                " telefone_cliente, logradouro_cliente, numero_cliente, complemento_cliente, municipio_cliente, " + //5
                " bairro_cliente, codIBGE_cliente, uf_cliente, cep_cliente, observacao_cliente, id_empresa_cliente, " + //4
                " dataEnvioInfo_cliente, horaEnvioInfo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, nome_cliente_cpf);
        stmt.setString(2, usuarioCpf);
        stmt.setString(3, RegistrationUser.email);
        stmt.setString(4, RegistrationUser.telefone);
        stmt.setString(5, ConsultaCEP.getLogradouro());
        stmt.setString(6, String.valueOf(ConsultaCEP.num));
        stmt.setString(7, ConsultaCEP.getComplemento());
        stmt.setString(8, ConsultaCEP.getCidade());
        stmt.setString(9, ConsultaCEP.getBairro());
        stmt.setString(10, ConsultaCEP.getIbge());
        stmt.setString(11, ConsultaCEP.getUf());
        stmt.setString(12, ConsultaCEP.getCep());
        stmt.setString(13, observacao_cliente);
        stmt.setInt(14, Login.id);
        stmt.setDate(15, Date.valueOf(dataEnvioDB));
        stmt.setTime(16, Time.valueOf(horaEnvioDB.toLocalTime()));

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data successfully inserted!");
        } else {
            System.out.println("Failed to insert data.");
        }
    }

    public static void DbUploadEmpresa(Connection connection) throws SQLException {

        SystemExecution.DataHora();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO empresa (nome_empresa_empresa, fantasia_empresa, cnpj_empresa, iE_empresa, situacao_empresa, logradouro_empresa," +
                " numero_empresa, complemento_empresa, municipio_empresa, bairro_empresa, codIBGE_empresa, uf_empresa," +
                " cep_empresa, email_empresa, telefone_empresa, observacao_empresa, abertura_empresa, tipo_empresa_empresa, porte_empresa, natureza_juridica_empresa," +
                " data_situacao_empresa, ultima_atualizacao_empresa, status_empresa, efr_empresa, motivo_situacao_empresa," +
                " situacao_especial_empresa, data_situacao_especial_empresa, capital_social_empresa, dataEnvioInfo_empresa, horaEnvioInfo_empresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        stmt.setString(1, resultado.getNome());
        stmt.setString(2, resultado.getFantasia());
        stmt.setString(3, resultado.getCnpj().replace(".", "").replace("/", "").replace("-", ""));
        stmt.setString(4, ConsultaCNPJ.inscEstadual);
        stmt.setString(5, resultado.getSituacao());
        stmt.setString(6, ConsultaCEP.getLogradouro());
        stmt.setString(7, String.valueOf(ConsultaCEP.num));
        stmt.setString(8, ConsultaCEP.getComplemento());
        stmt.setString(9, ConsultaCEP.getCidade());
        stmt.setString(10, ConsultaCEP.getBairro());
        stmt.setString(11, ConsultaCEP.getIbge());
        stmt.setString(12, ConsultaCEP.getUf());
        stmt.setString(13, ConsultaCEP.getCep());
        stmt.setString(14, RegistrationUser.email);
        stmt.setString(15, RegistrationUser.telefone);
        stmt.setString(16, observacao_cliente);
        stmt.setString(17, resultado.getAbertura());
        stmt.setString(18, resultado.getTipo());
        stmt.setString(19, resultado.getPorte());
        stmt.setString(20, resultado.getNatureza_juridica());
        stmt.setString(21, resultado.getData_situacao());
        stmt.setString(22, resultado.getUltima_atualizacao());
        stmt.setString(23, resultado.getStatus());
        stmt.setString(24, resultado.getEfr());
        stmt.setString(25, resultado.getMotivo_situacao());
        stmt.setString(26, resultado.getSituacao_especial());
        stmt.setString(27, resultado.getData_situacao_especial());
        stmt.setString(28, resultado.getCapital_social());
        stmt.setDate(29, Date.valueOf(dataEnvioDB));
        stmt.setTime(30, Time.valueOf(horaEnvioDB.toLocalTime()));

        stmt.executeUpdate();
    }
}

/*CREATE TABLE empresa(
id_cliente SERIAL PRIMARY KEY,
nome_empresa_empresa VARCHAR(150),
fantasia_empresa VARCHAR(150),
cnpj_empresa VARCHAR(14),
iE_empresa VARCHAR(15),
situacao_empresa VARCHAR(33),
logradouro_empresa VARCHAR(150),
numero_empresa  VARCHAR(11),
complemento_empresa VARCHAR(150),
municipio_empresa VARCHAR(100),
bairro_empresa VARCHAR(100),
codIBGE_empresa  VARCHAR(33),
uf_empresa  VARCHAR(33),
cep_empresa VARCHAR(33),
email_empresa VARCHAR(200),
telefone_empresa VARCHAR(100),
observacao_empresa VARCHAR(500),
abertura_empresa VARCHAR(50),
tipo_empresa_empresa VARCHAR(50),
porte_empresa VARCHAR(50),
natureza_juridica_empresa VARCHAR(150),
data_situacao_empresa VARCHAR(50),
ultima_atualizacao_empresa VARCHAR(50),
status_empresa VARCHAR(50),
efr_empresa VARCHAR(250),
motivo_situacao_empresa VARCHAR(250),
situacao_especial_empresa VARCHAR(250),
data_situacao_especial_empresa VARCHAR(250),
capital_social_empresa VARCHAR(250),
dataEnvioInfo_cliente DATE,
horaEnvioInfo_cliente TIME);*/



/*CREATE TABLE cliente(
id_cliente SERIAL PRIMARY KEY,
nome_cliente_cpf VARCHAR(100),
cpf_cliente_cpf VARCHAR(11),
email_cliente VARCHAR(200),
telefone_cliente VARCHAR(100),
logradouro_cliente VARCHAR(150),
numero_cliente  VARCHAR(11),
complemento_cliente VARCHAR(150),
municipio_cliente VARCHAR(100),
bairro_cliente VARCHAR(100),
codIBGE_cliente  VARCHAR(33),
uf_cliente  VARCHAR(33),
cep_cliente VARCHAR(33),
nome_empresa_cliente_cnpj VARCHAR(150),
fantasia_cliente_cnpj VARCHAR(150),
cnpj_cliente_cnpj VARCHAR(14),
iE_cliente_cnpj VARCHAR(15),
situacao_cliente_cnpj VARCHAR(33),
observacao_cliente VARCHAR(500),
abertura_cliente_cnpj VARCHAR(50),
tipo_empresa_cliente_cnpj VARCHAR(50),
porte_cliente_cnpj VARCHAR(50),
natureza_juridica_cliente_cnpj VARCHAR(150),
data_situacao_cliente_cnpj VARCHAR(50),
ultima_atualizacao_cliente_cnpj VARCHAR(50),
status_cliente_cnpj VARCHAR(50),
efr_cliente_cnpj VARCHAR(250),
motivo_situacao_cliente_cnpj VARCHAR(250),
situacao_especial_cliente_cnpj VARCHAR(250),
data_situacao_especial_cliente_cnpj VARCHAR(250),
capital_social_cliente_cnpj VARCHAR(250),
id_empresa_cliente INTEGER,
dataEnvioInfo_cliente DATE,
horaEnvioInfo_cliente TIME);*/