package bookstore.service;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class CrudService<ID, T> {
    private CrudRepository<T, ID> repository;

    protected CrudService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public Iterable<T> findAll() {
        return repository.findAll();
    }

    public T update(T entity) {
        return repository.save(entity);
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public CrudRepository<T, ID> getRepository() {
        return repository;
    }

    public void setRepository(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }
}
