package com.example.issproject;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController extends AbstractController{

    public TextField txt_nume;
    public TextField txt_prenume;
    public TextField txt_email;
    public TextField txt_telefon;
    public PasswordField txt_parola;

    public void btn_registerClicked(ActionEvent actionEvent) {
        try {
            service.register(txt_nume.getText(), txt_prenume.getText(), txt_email.getText(), txt_telefon.getText(), "CLIENT", txt_parola.getText());
            giveConfirmation("Cont inregistrat cu succes !");
            loginScreenSwitch();
        }
        catch (Exception ex){
            giveWarning(ex.getMessage());
        }
    }

    public void btn_backClicked(ActionEvent actionEvent) throws IOException {
        loginScreenSwitch();
    }
}
