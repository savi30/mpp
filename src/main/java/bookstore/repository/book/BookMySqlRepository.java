package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.repository.config.MySqlDatabaseConnector;
import bookstore.utils.builder.SqlQueryBuilder;
import bookstore.utils.mapper.ObjectMapper;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookMySqlRepository implements BookRepository {
    private static final String BOOKS_TABLE = "books";
    private Validator validator;
    private ObjectMapper<Book> objectMapper;

    public BookMySqlRepository(Validator validator) {
        this.validator = validator;
        this.objectMapper = new ObjectMapper<>(Book.class);
    }

    @Override
    public Optional<Book> findById(String id) {
        Optional<Book> optional = Optional.empty();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder.select("*").from(BOOKS_TABLE).where(builder.eq("id", id.toString())).build();
            ResultSet result = statement.executeQuery(query);
            optional = objectMapper.map(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Collection<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .select("books.id", "title", "publish_year", " price", "quantity")
                    .from(BOOKS_TABLE)
                    .join("authors_books")
                    .on(builder.eq("books.id", "authors_books.book_id"))
                    .join("authors")
                    .on(builder.eq("authors_books.author_id", "authors.id"))
                    .groupBy("id")
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<Book> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            getAuthors(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
    public Optional<Book> delete(String aLong) {
        return Optional.empty();
    }

    private void getAuthors(List<Book> list) throws SQLException {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            ObjectMapper<NamedEntity> namedEntityMapper = new ObjectMapper<>(NamedEntity.class);
            list.forEach(book -> {
                String query = builder
                        .select("id", "author_name as name")
                        .from("authors")
                        .join("authors_books")
                        .on(builder.eq("authors.id", "authors_books.author_id"))
                        .where(builder.eq("authors_books.book_id", book.getId()))
                        .build();
                ResultSet result = null;
                try {
                    result = statement.executeQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        if (!result.next()) break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Optional<NamedEntity> optional = namedEntityMapper.map(result);
                    optional.ifPresent(book.getAuthors()::add);
                }
            });
        }
    }
}
