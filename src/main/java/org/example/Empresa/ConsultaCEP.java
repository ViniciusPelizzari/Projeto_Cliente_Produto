package org.example.Empresa;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ConsultaCEP {
    static Scanner s = new Scanner(System.in);

    public static String logradouro;
    public static String num;
    public static String complemento;
    public static String bairro;
    public static String cidade;
    public static String uf;
    public static String cep;
    public static String ibge;

    public static void Num(){
        System.out.print("Number: ");
        num = s.nextLine();
    }

    public static String getLogradouro() {
        return logradouro;
    }

    public static void setLogradouro(String logradouro) {
        ConsultaCEP.logradouro = logradouro;
    }

    public static String getComplemento() {
        return complemento;
    }

    public static void setComplemento(String complemento) {
        ConsultaCEP.complemento = complemento;
    }

    public static String getBairro() {
        return bairro;
    }

    public static void setBairro(String bairro) {
        ConsultaCEP.bairro = bairro;
    }

    public static String getCidade() {
        return cidade;
    }

    public static void setCidade(String cidade) {
        ConsultaCEP.cidade = cidade;
    }

    public static String getUf() {
        return uf;
    }

    public static void setUf(String uf) {
        ConsultaCEP.uf = uf;
    }

    public static String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        ConsultaCEP.cep = cep;
    }

    public static String getIbge() {
        return ibge;
    }

    public static void setIbge(String ibge) {
        ConsultaCEP.ibge = ibge;
    }

    public static void ImprimirConsultaCEP(){
        System.out.println("Logradouro: " + getLogradouro());
        System.out.println("Complemento: " + getComplemento());
        System.out.println("Neighborhood: " + getBairro());
        System.out.println("City: " + getCidade());
        System.out.println("State: " + getUf());
        System.out.println("IBGE code: " + getIbge());
    }

    public static void Logradouro(){
        System.out.print("Public place: ");
        ConsultaCEP.setLogradouro(s.nextLine());
    }
    public static void Complement(){
        System.out.print("Complement: ");
        ConsultaCEP.setComplemento(s.nextLine());
    }
    public static void Bairro(){
        System.out.print("Neighborhood: ");
        ConsultaCEP.setBairro(s.nextLine());
    }
    public static void City(){
        System.out.print("City: ");
        ConsultaCEP.setCidade(s.nextLine());
    }
    public static void State(){
        System.out.print("State: ");
        ConsultaCEP.setUf(s.nextLine());
    }
    public static void Ibge(){
        System.out.print("IBGE code: ");
        ConsultaCEP.setIbge(s.nextLine());
    }

    //para testar esta classe, usar: ClientRegistration.ReadCep();
    public static void ConsultaCEPcomPerguntas(String cep) {
        try {
            if (cep == null || cep.isEmpty()) {
                System.out.print("CEP: ");
                cep = s.nextLine();
            }
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                httpURLConnection.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            }
            int status;
            try {
                status = httpURLConnection.getResponseCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while (true) {
                    try {
                        if (!((inputLine = in.readLine()) != null)) break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    content.append(inputLine);
                }
                try {
                    in.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JSONObject obj = new JSONObject(content.toString());

                ConsultaCEP consulta = new ConsultaCEP();
                consulta.setCep(cep);
                //System.out.println(content.toString());

                ConsultaCEP.setLogradouro(obj.getString("logradouro"));
                if (getLogradouro() == null || getLogradouro().trim().isEmpty()) {
                    Logradouro();
                }
                Num();
                ConsultaCEP.setComplemento(obj.getString("complemento"));
                if (getComplemento() == null || getComplemento().trim().isEmpty()) {
                    Complement();
                }
                ConsultaCEP.setBairro(obj.getString("bairro"));
                if (getBairro() == null || getBairro().trim().isEmpty()) {
                    Bairro();
                }
                ConsultaCEP.setCidade(obj.getString("localidade"));
                if (getCidade() == null || getCidade().trim().isEmpty()) {
                    City();
                }
                ConsultaCEP.setUf(obj.getString("uf"));
                if (getUf() == null || getUf().trim().isEmpty()) {
                    State();
                }
                ConsultaCEP.setIbge(obj.getString("ibge"));
                if (getIbge() == null || getIbge().trim().isEmpty()) {
                    Ibge();
                }
                //ImprimirConsultaCEP();
            } else {
                System.out.println("Error " + status);
            }
        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void ConsultaCEPsemPerguntas(String cep) {
        try {
            if (cep == null || cep.isEmpty()) {
                System.out.print("CEP: ");
                cep = s.nextLine();
            }
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                httpURLConnection.setRequestMethod("GET");
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            }
            int status;
            try {
                status = httpURLConnection.getResponseCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while (true) {
                    try {
                        if (!((inputLine = in.readLine()) != null)) break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    content.append(inputLine);
                }
                try {
                    in.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JSONObject obj = new JSONObject(content.toString());

                ConsultaCEP consulta = new ConsultaCEP();
                consulta.setCep(cep);
                ConsultaCEP.setLogradouro(obj.getString("logradouro"));
            } else {
                System.out.println("Error " + status);
            }
        } catch(
                Exception e)
        {
            e.printStackTrace();
        }
    }
}

