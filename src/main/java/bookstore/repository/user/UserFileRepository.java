package bookstore.repository.user;

import bookstore.domain.book.Book;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserFileRepository extends InMemoryRepository<Long, User> {
    private String fileName;
    public UserFileRepository(Validator<User> validator, String fileName){
        super(validator);
        this.fileName = fileName;
        loadData();
    }
    private void loadData(){
        Path path = Paths.get(fileName);

        try{
            Files.lines(path).forEach(line ->{
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String name = items.get(1);


                User user = new User(id, name);
                try{
                    super.save(user);
                }catch (ValidationException ve){
                    ve.printStackTrace();
                }
            });

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

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

    @Override
    public Optional<User> save(User entity) throws ValidationException {
        Optional<User> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }
}
