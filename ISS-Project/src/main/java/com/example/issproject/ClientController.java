package com.example.issproject;

import domain.Film;
import domain.Loc;
import domain.dtos.ProgramDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientController extends AbstractController{
    public GridPane gridPane;
    private Button[][] buttons;

    public Label lbl_user;

    public TableView<ProgramDTO> table1;
    public TableColumn<ProgramDTO, String> column1_film;
    public TableColumn<ProgramDTO, String> column1_ora;
    public TableColumn<ProgramDTO, String> column1_data;
    private ObservableList<ProgramDTO> filmeObs = FXCollections.observableArrayList();

    private List<Loc> locuriSelectate = new ArrayList<>();

    @FXML
    public void initialize(){
        lbl_user.setText(currentUser.getNume() + " " + currentUser.getPrenume());

        buttons = new Button[gridPane.getRowCount() + 1][gridPane.getColumnCount() + 1];
        for(int i = 0; i < gridPane.getRowCount(); i++)
            for(int j = 0; j < gridPane.getColumnCount(); j++){
                buttons[i][j] = new Button();
                buttons[i][j].setPrefWidth(30);
                buttons[i][j].setUserData(new Loc(j + 1, i + 1, currentUser.getId()));
                buttons[i][j].setOnAction(this::btn_LocClicked);
                gridPane.add(buttons[i][j], i, j);
            }

        column1_film.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("numeFilm"));
        column1_ora.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("startTime"));
        column1_data.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("data"));

        table1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadLocuri(newSelection.getLocList());
                locuriSelectate.clear();
            }
        });

        refreshFilme();
        //loadLocuri(List.of(new Loc(1, 5)));
    }

    private void loadLocuri(List<Loc> ocupate){
        for(int i = 0; i < gridPane.getRowCount(); i++)
            for(int j = 0; j < gridPane.getColumnCount(); j++){
                buttons[i][j].setStyle("");
            }
        for(Loc l : ocupate)
            if(l.getProprietarID().equals(currentUser.getId()))
                buttons[l.getColoana() - 1][l.getRand() - 1].setStyle("-fx-background-color: blue");
            else
                buttons[l.getColoana() - 1][l.getRand() - 1].setStyle("-fx-background-color: red");
    }

    public void btn_LocClicked(ActionEvent actionEvent) {
        Button buttonul = (Button)actionEvent.getSource();
        Loc loc = (Loc) buttonul.getUserData();

        System.out.println(loc.getRand() + " " + loc.getColoana());
        if(!(buttonul.getStyle().equals("-fx-background-color: red") || buttonul.getStyle().equals("-fx-background-color: blue"))){
            if(buttonul.getStyle().equals("-fx-background-color: green"))
            {
                buttonul.setStyle("");
                locuriSelectate.remove(loc);
            }
            else {
                buttonul.setStyle("-fx-background-color: green");
                locuriSelectate.add(loc);
            }
        }
        else
        {
            System.out.println("Ocupat deja !");
        }
    }

    public void btn_RezervaClicked(ActionEvent actionEvent) {
        if(table1.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR, "Selectati un film mai intai !");
            a.show();
            return;
        }

        int index = table1.getSelectionModel().getSelectedIndex();
        service.ocupaLocuri(table1.getSelectionModel().getSelectedItem().getId(), locuriSelectate);
        refreshFilme();

        table1.getSelectionModel().select(index);
        loadLocuri(table1.getSelectionModel().getSelectedItem().getLocList());
        locuriSelectate.clear();
    }

    private void refreshFilme(){
        List<ProgramDTO> programDTOS = service.getProgrameDTO();

        filmeObs.clear();
        filmeObs.addAll(programDTOS);

        table1.setItems(filmeObs);
        table1.getSelectionModel().clearSelection();
    }


    public void btn_logoutClicked(ActionEvent actionEvent) throws IOException {
        loginScreenSwitch();
    }
}