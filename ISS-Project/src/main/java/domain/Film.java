package domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Film {
    private Long id;

    private String nume;
    private String autor;
    private Integer ore;
    private Integer minute;
    private String durata;

    public Film(Long id, String nume, String autor, Integer ore, Integer minute) {
        this.id = id;
        this.nume = nume;
        this.autor = autor;
        this.ore = ore;
        this.minute = minute;
        this.durata = String.format("%02d:%02d", ore, minute);
    }

    public Film(String nume, String autor, Integer ore, Integer minute) {
        this.id = null;
        this.nume = nume;
        this.autor = autor;
        this.ore = ore;
        this.minute = minute;
        this.durata = String.format("%02d:%02d", ore, minute);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getOre() {
        return ore;
    }

    public void setOre(Integer ore) {
        this.ore = ore;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
        Pattern pattern = Pattern.compile("^(.+):(.+)$");

        Matcher matcher = pattern.matcher(durata);

        if(matcher.find()){
            this.ore = Integer.parseInt(matcher.group(1));
            this.minute = Integer.parseInt(matcher.group(2));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(nume, film.nume) && Objects.equals(autor, film.autor) && Objects.equals(ore, film.ore) && Objects.equals(minute, film.minute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, autor, ore, minute);
    }
}
