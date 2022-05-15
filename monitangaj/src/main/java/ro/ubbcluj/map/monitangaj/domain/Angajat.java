package ro.ubbcluj.map.monitangaj.domain;

import java.util.Objects;

public class Angajat extends Entity<Long>{

    private String nume;
    private String prenume;
    private String prezenta;

    public String getPrezenta() {
        return prezenta;
    }

    public void setPrezenta(String prezenta) {
        this.prezenta = prezenta;
    }

    public Angajat(String nume, String prenume) {
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
        return "User{" +
                "id = " + getId() +
                ", firstName='" + nume + '\'' +
                ", lastName='" + prenume + '\'' +
                ", prezenta='" + prezenta + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angajat angajat = (Angajat) o;
        return Objects.equals(nume, angajat.nume) && Objects.equals(prenume, angajat.prenume) && Objects.equals(prezenta, angajat.prezenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, prezenta);
    }
}