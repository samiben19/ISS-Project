package domain.dtos;

import domain.Loc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProgramDTO {
    private Long id;
    private String numeFilm;
    private String data;
    private String startTime;

    private List<Loc> locList = new ArrayList<>();

    public ProgramDTO(Long id, String numeFilm, String data, String startTime) {
        this.id = id;
        this.numeFilm = numeFilm;
        this.data = data;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeFilm() {
        return numeFilm;
    }

    public void setNumeFilm(String numeFilm) {
        this.numeFilm = numeFilm;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Loc> getLocList() {
        return locList;
    }

    public void setLocList(List<Loc> locList) {
        this.locList = locList;
    }
}
