package service;

import domain.Cont;
import domain.Film;
import domain.Loc;
import domain.Program;
import domain.dtos.ProgramDTO;
import repository.ConturiRepository;
import repository.FilmRepository;
import repository.ProgramRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    private FilmRepository filmRepository;
    private ProgramRepository programRepository;
    private ConturiRepository conturiRepository;

    public Service(FilmRepository filmRepository, ProgramRepository programRepository, ConturiRepository conturiRepository){
        this.filmRepository = filmRepository;
        this.programRepository = programRepository;
        this.conturiRepository = conturiRepository;
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

        String durata = String.format("%02d:%02d", ore, minute);

        Film film = new Film(nume, autor, durata);
        filmRepository.save(film);
    }

    public void updateFilm(Long id, String nume, String autor, Integer ore, Integer minute){
        String error = validateFilm(nume, autor, ore, minute);
        if(!error.equals(""))
            throw new ServiceException(error);

        String durata = String.format("%02d:%02d", ore, minute);

        Film film = new Film(id, nume, autor, durata);
        filmRepository.update(film);
    }

    public void deleteFilm(Long id){
        filmRepository.delete(id);
    }

    public List<Film> getFilme(){
        return filmRepository.getAll();
    }

    private boolean oraValida(Integer ore, Integer minute){
        if(ore == null || minute == null)
            return false;
        if(ore < 0 || ore >= 24 || minute < 0 || minute >= 60)
            return false;
        return true;
    }

    private String validateProgram(Film film, String data, String start){
        String error = "";

        if(film == null)
            error += "Nu a fost selectat niciun film !\n";

        Pattern pattern = Pattern.compile("^(.+):(.+)$");

        Matcher matcher = pattern.matcher(start);

        Integer ore = null, minute = null;
        try {
            if (matcher.find()) {
                ore = Integer.parseInt(matcher.group(1));
                minute = Integer.parseInt(matcher.group(2));
            }
            if (!oraValida(ore, minute))
                error += "Ora de start invalida !\n";
        }catch (NumberFormatException __){
            error += "Ora de start invalida !\n";
        }


        try {
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("d.M.yyyy")));
            LocalDate.parse(data, DateTimeFormatter.ofPattern("d.M.yyyy"));
        }
        catch (DateTimeParseException ex){
            error += "Data invalida !\n";
        }
        return error;
    }

    public void addProgram(Film film, String data, String start){
        String error = validateProgram(film, data, start);
        if(!error.equals(""))
            throw new ServiceException(error);

        String[] numbers = start.split(":");
        start = String.format("%02d:%02d", Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));

        LocalDateTime dataTime = LocalDate.parse(data,  DateTimeFormatter.ofPattern("d.M.yyyy")).atStartOfDay();

        Program program = new Program(film.getId(), dataTime, start);
        programRepository.save(program);
    }

    public void ocupaLocuri(Long id, List<Loc> locuriOcupateNoi){
        Program searched = programRepository.getById(id);

        if(searched == null)
            throw new ServiceException("Program inexistent !");

        Long idFilm = searched.getIdFilm();
        LocalDateTime date = searched.getDate();
        String startTime = searched.getStartTime();
        List<Loc> locuriOcupate = searched.getLocuriOcupate();

        locuriOcupate.addAll(locuriOcupateNoi);

        Program newProgram = new Program(id, idFilm, date, startTime, locuriOcupate);
        programRepository.update(newProgram);
    }

    public List<ProgramDTO> getProgrameDTO(){
        List<ProgramDTO> programsDTO = new ArrayList<>();


        for(Program program : programRepository.getAll()){
            Film f = filmRepository.getById(program.getIdFilm());
            ProgramDTO programDTO = new ProgramDTO(program.getId(), f.getNume(), program.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), program.getStartTime());
            programDTO.setLocList(program.getLocuriOcupate());
            programsDTO.add(programDTO);
        }

        return programsDTO;
    }

    private String validateCont(String nume, String prenume, String email, String telefon, String rol, String parola){
        String error = "";
        if(nume.strip().equals(""))
            error += "Nume invalid !\n";
        if(prenume.strip().equals(""))
            error += "Prenume invalid !\n";
        if(!email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$"))
            error += "Email invalid !\n";
        if(!telefon.matches("[0-9]{5,}"))
            error += "Telefon invalid !\n";
        if(!(rol.equals("ADMIN") || rol.equals("CLIENT")))
            error += "Rol invalid !\n";
        if(parola.strip().equals(""))
            error += "Parola invalida !\n";
        return error;
    }

    public void register(String nume, String prenume, String email, String telefon, String rol, String parola){
        String error = validateCont(nume, prenume, email, telefon, rol, parola) ;
        if(!error.equals(""))
            throw new ServiceException(error);

        Cont cont = new Cont(nume, prenume, email, telefon, rol, parola);

        conturiRepository.save(cont);
    }

    public Cont login(String email, String parola){
        return conturiRepository.login(email, parola);
    }
}
