package ro.ubbcluj.map.monitangaj.service;


import ro.ubbcluj.map.monitangaj.domain.Angajat;
import ro.ubbcluj.map.monitangaj.repository.Repository;

import java.util.Objects;

public class AngajatiService extends AbstractService<Long, Angajat>{

    public AngajatiService(Repository<Long, Angajat> repo) { repository = repo; }

    @Override
    public void addEntity(Angajat entity) {
        for(Angajat user:repository.findAll()){
            if(Objects.equals(user.getNume(), entity.getNume()) && Objects.equals(user.getPrenume(), entity.getPrenume())){
                throw new IllegalArgumentException("Utilizatoru' exista deja;");
            }
        }
        super.addEntity(entity);
    }
}