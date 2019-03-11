package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserFileRepository extends InMemoryRepository<String, User> {
    private String fileName;

    public UserFileRepository(Validator<User> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    /**
     * Load all users from the file into memory.
     */
    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                String id = String.valueOf(items.get(0));
                String name = items.get(1);

                User user = new User(id, name);
                try {
                    super.save(user);
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write user to file.
     */
    private void saveToFile(User entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getName());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write all to file.
     */
    private void writeAllToFile(){
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().forEach(user -> {try{
                bufferedWriter.write(user.getId() + "," +user.getName() + "\n");
            }catch (IOException e){
                e.printStackTrace();
            }});
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Add a user and write changes to file.
     * @return an {@code Optional} encapsulating the added user.
     */
    @Override
    public Optional<User> save(User entity) throws ValidationException {
        Optional<User> optional = super.save(entity);
        if (optional.isPresent()) {
            saveToFile(entity);
            return optional;
        }
        return Optional.empty();
    }

    /**
     * Delete a user and write changes to file.
     * @return an {@code Optional} encapsulating the deleted user or empty if there is no such user.
     */
    @Override
    public Optional<User> delete(String id){
        Optional<User> optional = super.delete(id);
        if (optional.isPresent()) {
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }

    /**
     * Update a user and write changes to file.
     * @return an {@code Optional} encapsulating the updated user or empty if there is no such user.
     */
    @Override
    public Optional<User> update(User entity)throws ValidationException {
        Optional<User> optional = super.update(entity);
        if (optional.isPresent()) {
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }
}
