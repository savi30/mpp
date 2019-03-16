package bookstore.repository.user;

import bookstore.domain.user.User;
import bookstore.repository.FileRepository;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.builder.UserBuilder;
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

public class UserFileRepository extends FileRepository<String, User> {

    public UserFileRepository(Validator<User> validator, String fileName) {
        super(validator, fileName, new UserBuilder());
    }
}
