package bookstore.repository;

import bookstore.domain.core.Entity;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class FileRepository<ID extends Serializable, T extends Entity<ID>> extends InMemoryRepository<ID, T> {
    private String fileName;
    private Reader<T> reader;
    public FileRepository(Validator<T> validator, String fileName, Reader<T> reader) {
        super(validator);
        this.fileName = fileName;
        this.reader = reader;
        loadData();
    }

    /**
     * Load all entities from the file into memory.
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                try {
                    super.save(reader.get(line));
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write entity to file.
     */
    protected void saveToFile(T entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(entity.toFileString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a entity and write changes to file.
     * @return an {@code Optional} encapsulating the added entity.
     */
    @Override
    public Optional<T> save(T entity) throws ValidationException {
        Optional<T> optional = super.save(entity);
        optional.ifPresentOrElse(
                opt -> writeAllToFile(),
                () -> saveToFile(entity)
                );

        return optional;
    }

    /**
     * Delete a entity and write changes to file.
     * @return an {@code Optional} encapsulating the deleted entity or empty if there is no such entity.
     */
    @Override
    public Optional<T> delete(ID id){
        Optional<T> optional = super.delete(id);
        optional.ifPresent(opt -> writeAllToFile());
        return optional;
    }

    /**
     * Update a entity and write changes to file.
     * @return an {@code Optional} encapsulating the updated entity or empty if there is no such entity.
     */
    @Override
    public Optional<T> update(T entity)throws ValidationException {
        Optional<T> optional = super.update(entity);
        optional.ifPresent(opt -> writeAllToFile());
        return optional;
    }

    /**
     * Write all to file.
     */
    protected void writeAllToFile(){
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().forEach(entity -> {try{
                bufferedWriter.write(entity.toFileString());
            }catch (IOException e){
                e.printStackTrace();
            }});
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

