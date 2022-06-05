package com.example.issproject;

import domain.Cont;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class AbstractController {
    protected static Cont currentUser;
    public static Service service;
    public static Stage stage;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    protected void changeScene(String fxmlString) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginStart.class.getResource(fxmlString));

        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
    }

    @FXML
    protected void loginScreenSwitch() throws IOException{
        changeScene("interfata-login.fxml");
    }

    @FXML
    protected void registerScreenSwitch() throws IOException{
        changeScene("interfata-register.fxml");
    }

    @FXML
    protected void adminScreenSwitch() throws IOException {
        changeScene("interfata-admin.fxml");
    }

    @FXML
    protected void clientScreenSwitch() throws IOException {
        changeScene("interfata-client.fxml");
    }

    @FXML
    protected void closeWindow() throws IOException {
        System.exit(0);
    }

    @FXML
    protected void giveConfirmation(String s) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, s);
        a.show();
    }

    @FXML
    protected void giveWarning(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR, s);
        a.show();
    }
}
