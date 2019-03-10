package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.repository.config.MySqlDatabaseConnector;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;
import bookstore.utils.builder.SqlQueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

public class BookMySqlRepository implements BookRepository {
    private static final Logger LOGGER = Logger.getLogger(BookMySqlRepository.class.getName());
    private static final String TABLE = "books";
    private Validator validator;

    public BookMySqlRepository(Validator validator) throws SQLException {
        this.validator = validator;
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder.select("*").from(TABLE).where(builder.eq("id", aLong.toString())).build();
            ResultSet result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Book> findAll() {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder.select("*").from(TABLE).build();


            ResultSet result = statement.executeQuery(query);
            List<Field> fields = Arrays.asList(Book.class.getDeclaredFields());
            for (Field field : fields) {
                field.setAccessible(true);
            }

            List<Book> list = new ArrayList<>();
            while (result.next()) {
                Book dto = null;
                try {
                    dto = Book.class.getConstructor().newInstance();
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                for (Field field : fields) {
                    String name = field.getName();

                    try {
                        String value = result.getString(name);
                        field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                list.add(dto);
            }
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidationException {
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long aLong) {
        return Optional.empty();
    }
}
