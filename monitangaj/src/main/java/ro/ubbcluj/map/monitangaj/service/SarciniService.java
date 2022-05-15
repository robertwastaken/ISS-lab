package ro.ubbcluj.map.monitangaj.service;


import ro.ubbcluj.map.monitangaj.domain.Sarcina;
import ro.ubbcluj.map.monitangaj.repository.Repository;

public class SarciniService extends AbstractService<Long, Sarcina> {
    public SarciniService(Repository<Long, Sarcina> friendshipRepository) {
        repository = friendshipRepository;
    }
}
