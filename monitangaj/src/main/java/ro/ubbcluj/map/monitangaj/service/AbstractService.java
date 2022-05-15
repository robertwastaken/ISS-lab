package ro.ubbcluj.map.monitangaj.service;


import ro.ubbcluj.map.monitangaj.domain.Entity;
import ro.ubbcluj.map.monitangaj.repository.Repository;


public abstract class AbstractService<ID, E extends Entity<ID>> {
    public Repository<ID,E> repository;

    public void addEntity(E entity){ repository.save(entity);}
    public void deleteEntity(ID id){
        repository.delete(id);
    }
    public void updateEntity(E entity) {repository.update(entity);}
    public E findOne(ID id){
        return repository.findOne(id);
    }
    public Iterable<E> findAll(){
        return repository.findAll();
    }
    public Long getSize(){
        return repository.getSize();
    }
}