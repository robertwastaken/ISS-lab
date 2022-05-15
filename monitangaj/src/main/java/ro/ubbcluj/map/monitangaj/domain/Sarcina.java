package ro.ubbcluj.map.monitangaj.domain;

import java.util.Objects;

public class Sarcina extends Entity<Long> {

    private String descriere;
    private StatusSarcina status;
    private Long idAngajat;

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public StatusSarcina getStatus() {
        return status;
    }

    public void setStatus(StatusSarcina status) {
        this.status = status;
    }

    public Long getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(Long idAngajat) {
        this.idAngajat = idAngajat;
    }

    @Override
    public String toString() {
        return "Sarcina{" +
                "descriere='" + descriere + '\'' +
                ", status=" + status +
                ", idAngajat=" + idAngajat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sarcina sarcina = (Sarcina) o;
        return Objects.equals(descriere, sarcina.descriere) && status == sarcina.status && Objects.equals(idAngajat, sarcina.idAngajat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriere, status, idAngajat);
    }

    public Sarcina(String descriere, StatusSarcina status, Long idAngajat) {
        this.descriere = descriere;
        this.status = status;
        this.idAngajat = idAngajat;
    }
}