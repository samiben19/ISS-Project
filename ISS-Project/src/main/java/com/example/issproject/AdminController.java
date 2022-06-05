package com.example.issproject;

import domain.Film;
import domain.dtos.ProgramDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController extends AbstractController{
    public TableView<Film> table1;
    public TableColumn<Film, String> column1_film;
    public TableColumn<Film, String> column1_autor;
    public TableColumn<Film, String> column1_durata;

    private ObservableList<Film> filmeObs = FXCollections.observableArrayList();

    public TableView<ProgramDTO> table2;
    public TableColumn<ProgramDTO, String> column2_film;
    public TableColumn<ProgramDTO, String> column2_data;
    public TableColumn<ProgramDTO, String> column2_start;
    private ObservableList<ProgramDTO> programDTOSObs = FXCollections.observableArrayList();

    public TextField txt_numeFilm;
    public TextField txt_autorFilm;
    public TextField txt_durataFilm;

    public TextField txt_dataProgram;
    public TextField txt_startProgram;

    @FXML
    public void initialize(){
        column1_film.setCellValueFactory(new PropertyValueFactory<Film, String>("nume"));
        column1_autor.setCellValueFactory(new PropertyValueFactory<Film, String>("autor"));
        column1_durata.setCellValueFactory(new PropertyValueFactory<Film, String>("durata"));

        column2_film.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("numeFilm"));
        column2_data.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("data"));
        column2_start.setCellValueFactory(new PropertyValueFactory<ProgramDTO, String>("startTime"));

        refreshFilme();
        refreshPrograme();
    }

    private void refreshPrograme(){
        List<ProgramDTO> programDTOS = service.getProgrameDTO();

        programDTOSObs.clear();
        programDTOSObs.addAll(programDTOS);

        table2.setItems(programDTOSObs);
        table2.getSelectionModel().clearSelection();
    }

    private void refreshFilme(){
        List<Film> filme = service.getFilme();

        filmeObs.clear();
        filmeObs.addAll(filme);

        table1.setItems(filmeObs);
        table1.getSelectionModel().clearSelection();
    }

    private void clearTexts1(){
        txt_numeFilm.clear();
        txt_autorFilm.clear();
        txt_durataFilm.clear();
    }

    private void clearTexts2(){
        txt_dataProgram.clear();
        txt_startProgram.clear();
    }

    public void btn_addFilmClicked(ActionEvent actionEvent) {
        try{
            String nume = txt_numeFilm.getText();
            String autor = txt_autorFilm.getText();
            String durataStr = txt_durataFilm.getText();

            Pattern pattern = Pattern.compile("^(.+):(.+)$");

            Matcher matcher = pattern.matcher(durataStr);

            Integer ore = null, minute = null;
            if(matcher.find()){
                ore = Integer.parseInt(matcher.group(1));
                minute = Integer.parseInt(matcher.group(2));
            }

            service.addFilm(nume, autor, ore, minute);
            refreshFilme();
            clearTexts1();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    public void btn_updateFilmClicked(ActionEvent actionEvent) {
        try{
            String nume = txt_numeFilm.getText();
            String autor = txt_autorFilm.getText();
            String durataStr = txt_durataFilm.getText();

            Pattern pattern = Pattern.compile("^(.+):(.+)$");

            Matcher matcher = pattern.matcher(durataStr);

            Integer ore = null, minute = null;
            if(matcher.find()){
                ore = Integer.parseInt(matcher.group(1));
                minute = Integer.parseInt(matcher.group(2));
            }

            long id = table1.getSelectionModel().getSelectedItem().getId();

            service.updateFilm(id, nume, autor, ore, minute);
            refreshFilme();
            clearTexts1();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    public void btn_deleteFilmClicked(ActionEvent actionEvent) {
        try{
            long id = table1.getSelectionModel().getSelectedItem().getId();

            service.deleteFilm(id);
            refreshFilme();
            clearTexts1();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    public void table1_clicked(MouseEvent mouseEvent) {
        Film film = table1.getSelectionModel().getSelectedItem();
        if(film != null){
            txt_numeFilm.setText(film.getNume());
            txt_autorFilm.setText(film.getAutor());
//            txt_durataFilm.setText(film.getDurata());
        }
    }

    public void btn_addFilmProgramClicked(ActionEvent actionEvent) {
        try{
            Film film = table1.getSelectionModel().getSelectedItem();
            String data = txt_dataProgram.getText();
            String start = txt_startProgram.getText();

            service.addProgram(film, data, start);
            refreshPrograme();
            clearTexts2();
        }
        catch (Exception ex){
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    public void btn_updateFilmProgramClicked(ActionEvent actionEvent) {
    }

    public void btn_deleteFilmProgramClicked(ActionEvent actionEvent) {
    }
}
