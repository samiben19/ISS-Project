package com.example.issproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.ConturiRepository;
import repository.FilmRepository;
import repository.ProgramRepository;
import service.Service;

import java.io.IOException;
import java.util.Properties;

public class ClientStart extends Application {
    private Properties getProps(){
        Properties props = new Properties();
        try {
            props.load(ClientStart.class.getResourceAsStream("database.config"));
            System.out.println("Properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find database.config, " + e);
            return null;
        }
        return props;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientStart.class.getResource("interfata-client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        FilmRepository filmRepository = new FilmRepository(getProps());
        ProgramRepository programRepository = new ProgramRepository(getProps());
        ConturiRepository conturiRepository = new ConturiRepository(getProps());
        Service service = new Service(filmRepository, programRepository, conturiRepository);

        ClientController clientController = fxmlLoader.getController();
        clientController.setService(service);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}