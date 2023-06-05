package org.example.Input;

import org.example.Client.ClientRegistration;
import org.example.ToEnter.Login;
import org.example.Methods.ChangeData;
import org.example.Methods.ExitSystem;

import java.sql.Connection;
import java.sql.SQLException;

public class InputOptions {
    public static int opc;
    public static void Option(Connection connection) throws SQLException {

        System.out.println("1 - LOGOUT | 2 - CHANGE USER/PASSWORD | 3 - CHANGE COMPANY DATA | 4 - CUSTOMER REGISTRATION");
        opc = Login.s.nextInt();
        if (opc == 1) {
            //System exit
            ExitSystem.Exit();
        } else if (opc == 2) {
            //Change User or Password
            ChangeData.ChangeUserPassword(connection);
        } else if (opc == 3) {
            //Change of company data
            //ChangeData.ChangeCompany(connection);
        } else if (opc == 4) {
            //customer registration
            ClientRegistration.Cliente(connection);
        } else {
            System.out.print("Invalid option");

        }
        /*while (opc < 1 || opc > 4) {
            System.out.println("Invalid option. Please choose a valid option.");
            opc = Integer.parseInt(Login.s.nextLine());

            if (opc == 1) {
                //System exit
                ExitSystem.Exit();
            } else if (opc == 2) {
                //Change User or Password
                ChangeData.ChangeUserPassword(connection);
            } else if (opc == 3) {
                //Change of company data
                ChangeData.ChangeCompany(connection);
            } else if (opc == 4) {
                //customer registration
                ClientRegistration.Cliente(connection);
            } else {
                System.out.print("Invalid option");

            }
        }*/
    }
}
