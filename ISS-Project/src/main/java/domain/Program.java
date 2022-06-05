package domain;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private Long id;
    private Long idFilm;
    private LocalDateTime date;
    private String startTime;
    private List<Loc> locuriOcupate;

    public Program(){
    }

    public Program(Long id, Long idFilm, LocalDateTime date, String startTime, List<Loc> locuriOcupate) {
        this.id = id;
        this.idFilm = idFilm;
        this.date = date;
        this.startTime = startTime;
        this.locuriOcupate = locuriOcupate;
    }

    public Program(Long idFilm, LocalDateTime date, String startTime, List<Loc> locuriOcupate) {
        this.idFilm = idFilm;
        this.date = date;
        this.startTime = startTime;
        this.locuriOcupate = locuriOcupate;
    }

    public Program(Long id, Long idFilm, LocalDateTime date, String startTime) {
        this.id = id;
        this.idFilm = idFilm;
        this.date = date;
        this.startTime = startTime;
        this.locuriOcupate = new ArrayList<>();
    }

    public Program(Long idFilm, LocalDateTime date, String startTime) {
        this.idFilm = idFilm;
        this.date = date;
        this.startTime = startTime;
        this.locuriOcupate = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFilm() {
        return idFilm;
    }

    public void setFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Loc> getLocuriOcupate() {
        return locuriOcupate;
    }

    public void setLocuriOcupate(List<Loc> locuriOcupate) {
        this.locuriOcupate = locuriOcupate;
    }

    public void ocupaLoc(Loc loc){
        this.locuriOcupate.add(loc);
    }

    public void elibereazaLoc(Loc loc){
        this.locuriOcupate.remove(loc);
    }
}
