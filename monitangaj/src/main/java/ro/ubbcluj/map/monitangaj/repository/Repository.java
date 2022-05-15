package ro.ubbcluj.map.monitangaj.repository;


import ro.ubbcluj.map.monitangaj.domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    void save(E entity);
    void delete(ID id);
    void update(E entity);
    Long getSize();
}