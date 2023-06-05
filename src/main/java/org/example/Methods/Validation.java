package org.example.Methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Validation {
    public static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();

    public static boolean CPFValidation(String cpf) {
        // Remove todos os caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int dv1 = 11 - (soma % 11);
        if (dv1 > 9) {
            dv1 = 0;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int dv2 = 11 - (soma % 11);
        if (dv2 > 9) {
            dv2 = 0;
        }

        // Verifica se os dígitos verificadores calculados são iguais aos dígitos verificadores originais
        return (Character.getNumericValue(cpf.charAt(9)) == dv1 && Character.getNumericValue(cpf.charAt(10)) == dv2);
    }

    public static boolean ValidatePhoneNumber(String telefone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(telefone, "BR");
            return phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }


    public class EmailInput {
        public static final String EMAIL_REGEX = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        public static boolean validateEmail(String email) {
            Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
}
