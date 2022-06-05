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

public class AdminStart extends Application {
    private Properties getProps(){
        Properties props = new Properties();
        try {
            props.load(AdminStart.class.getResourceAsStream("database.config"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(AdminStart.class.getResource("interfata-admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        FilmRepository filmRepository = new FilmRepository(getProps());
        ProgramRepository programRepository = new ProgramRepository(getProps());
        ConturiRepository conturiRepository = new ConturiRepository(getProps());
        Service service = new Service(filmRepository, programRepository, conturiRepository);

        AdminController adminController = fxmlLoader.getController();
        adminController.setService(service);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        try{
//            Configuration configuration = new Configuration().configure();
//            configuration.addAnnotatedClass(Film.class);
//            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//            SessionFactory factory = configuration.buildSessionFactory(builder.build());
//
//            Session session = factory.openSession();
//            //Transaction transaction = session.beginTransaction();
//
//            Film f = session.get(Film.class, 6);
//            System.out.println(f.getId() + " " + f.getNume() + " " + f.getDurata());
//
//        }
//        catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
    }
}