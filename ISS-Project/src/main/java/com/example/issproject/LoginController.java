package com.example.issproject;

import domain.Cont;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends AbstractController{
    public TextField txt_email;
    public PasswordField txt_password;

    public void btn_loginClicked(ActionEvent actionEvent) throws IOException {
        Cont cont = service.login(txt_email.getText(), txt_password.getText());
        if(cont != null) {
            currentUser = cont;
            if (cont.getRol().equals("ADMIN"))
                adminScreenSwitch();
            else if (cont.getRol().equals("CLIENT"))
                clientScreenSwitch();
        }
        else
            giveWarning("Creditentiale invalide !");
    }


    public void btn_registerClicked(ActionEvent actionEvent) throws IOException {
        registerScreenSwitch();
    }
}
