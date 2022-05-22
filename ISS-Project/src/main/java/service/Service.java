package service;

import domain.Film;
import repository.FilmRepository;

import java.util.List;

public class Service {
    private FilmRepository filmRepository;

    public Service(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    private String validateFilm(String nume, String autor, Integer ore, Integer minute){
        String error = "";
        if(nume.equals(""))
            error += "Nume de film invalid !\n";
        if(autor.equals(""))
            error += "Autor invalid ! !\n";
        if(ore == null || minute == null || ore < 0 || ore > 100 || minute < 0 || minute >= 60)
            error += "Durata invalida !\n";
        return error;
    }

    public void addFilm(String nume, String autor, Integer ore, Integer minute){
        String error = validateFilm(nume, autor, ore, minute);
        if(!error.equals(""))
            throw new ServiceException(error);

        Film film = new Film(nume, autor, ore, minute);
        filmRepository.save(film);
    }

    public void updateFilm(Long id, String nume, String autor, Integer ore, Integer minute){
        String error = validateFilm(nume, autor, ore, minute);
        if(!error.equals(""))
            throw new ServiceException(error);

        Film film = new Film(id, nume, autor, ore, minute);
        filmRepository.update(film);
    }

    public void deleteFilm(Long id){
        filmRepository.delete(id);
    }

    public List<Film> getFilme(){
        return filmRepository.getAll();
    }
}
