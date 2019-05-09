package bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author pollos_hermanos.
 */
public class CrudService<ID, T> {
    private CrudRepository<T, ID> repository;
    private static final Logger log = LoggerFactory.getLogger(
            CrudService.class);

    protected CrudService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Optional<T> findById(ID id) {
        log.trace("findById --- method entered");
        Optional<T> result = repository.findById(id);
        log.trace("findById: result={}", result);
        return result;
    }

    public Iterable<T> findAll() {
        log.trace("findAll --- method entered");
        Iterable<T> result = repository.findAll();
        log.trace("findAll: result={}", result);
        return result;
    }

    public T update(T entity) {
        log.trace("update --- method entered");
        T result = repository.save(entity);
        log.trace("update: result={}", result);
        return result;
    }

    public void save(T entity) {
        log.trace("save --- method entered");
        repository.save(entity);
        log.trace("save --- method exited");
    }

    public void delete(ID id) {
        log.trace("delete --- method entered");
        repository.deleteById(id);
        log.trace("delete --- method exited");
    }

    public CrudRepository<T, ID> getRepository() {
        log.trace("getRepository --- method entered");
        log.trace("update: result={}", repository);
        return repository;
    }

    public void setRepository(CrudRepository<T, ID> repository) {
        log.trace("setRepository --- method entered");
        this.repository = repository;
        log.trace("setRepository --- method exited");
    }
}
