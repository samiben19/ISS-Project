package domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity

@Table(name = "Filme")
public class Film {
    @Id @GeneratedValue
    private Long id;

    private String nume;
    private String autor;
    private String durata;

    public Film(){
    }

    public Film(Long id, String nume, String autor, String durata) {
        this.id = id;
        this.nume = nume;
        this.autor = autor;
        this.durata = durata;
    }

    public Film(String nume, String autor, String durata) {
        this.id = null;
        this.nume = nume;
        this.autor = autor;
        this.durata = durata;
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

    public String getDurata() {
        return this.durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(nume, film.nume) && Objects.equals(autor, film.autor) && Objects.equals(durata, film.durata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, autor, durata);
    }
}
//@Entity
//
//@Table(name = "Filme")
//public class Film {
//    @Id
//    private int id;
//
//    private String nume;
//    private String autor;
//    private int ore;
//    private int minute;
//
//    public Film(){
//
//    }
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getNume() {
//        return nume;
//    }
//
//    public void setNume(String nume) {
//        this.nume = nume;
//    }
//
//    public String getAutor() {
//        return autor;
//    }
//
//    public void setAutor(String autor) {
//        this.autor = autor;
//    }
//
//    public int getOre() {
//        return ore;
//    }
//
//    public void setOre(int ore) {
//        this.ore = ore;
//    }
//
//    public int getMinute() {
//        return minute;
//    }
//
//    public void setMinute(int minute) {
//        this.minute = minute;
//    }
//}
