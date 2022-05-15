package ro.ubbcluj.map.monitangaj.domain;

import java.util.Objects;

public class Sef extends Entity<Long>{

    private String nume;
    private String prenume;

    public Sef(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }


    @Override
    public String toString() {
        return "Sef{" +
                "id = " + getId() +
                ", firstName='" + nume + '\'' +
                ", lastName='" + prenume + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sef)) return false;
        Sef that = (Sef) o;
        return getNume().equals(that.getNume()) &&
                getPrenume().equals(that.getPrenume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getPrenume());
    }
}