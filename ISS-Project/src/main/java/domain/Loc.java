package domain;

import java.util.Objects;

public class Loc {
    private Integer rand;
    private Integer coloana;
    private Long proprietarID;

    public Loc(){
    }

    public Loc(Integer rand, Integer coloana, Long proprietarID) {
        this.rand = rand;
        this.coloana = coloana;
        this.proprietarID = proprietarID;
    }

    public Integer getRand() {
        return rand;
    }

    public void setRand(Integer rand) {
        this.rand = rand;
    }

    public Integer getColoana() {
        return coloana;
    }

    public void setColoana(Integer coloana) {
        this.coloana = coloana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        return Objects.equals(rand, loc.rand) && Objects.equals(coloana, loc.coloana);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rand, coloana);
    }

    public Long getProprietarID() {
        return proprietarID;
    }

    public void setProprietar(Long proprietarID) {
        this.proprietarID = proprietarID;
    }
}
