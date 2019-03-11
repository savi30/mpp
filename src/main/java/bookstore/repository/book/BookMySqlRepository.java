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
            String query = builder.select("*").from(BOOKS_TABLE).where(builder.eq("id", id)).build();
            ResultSet result = statement.executeQuery(query);
            optional = objectMapper.map(result);
            this.getAuthors(optional.get());

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
            list.forEach(this::getAuthors);
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
    public Optional<Book> delete(String id) {
        Optional<Book> optional = this.findById(id);
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .delete("books")
                    .where(builder.eq("id", id))
                    .build();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Optional<Book> buy(String bookId, String clientId) {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .update("books")
                    .set(builder.eq("quantity", "quantity - 1"))
                    .where(builder.eq("id", bookId))
                    .build();
            statement.executeUpdate(query);
            this.updateLogs(bookId, clientId);
            return this.findById(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void getAuthors(Book book) {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            ObjectMapper<NamedEntity> namedEntityMapper = new ObjectMapper<>(NamedEntity.class);
            String query = builder
                    .select("id", "author_name as name")
                    .from("authors")
                    .join("authors_books")
                    .on(builder.eq("authors.id", "authors_books.author_id"))
                    .where(builder.eq("authors_books.book_id", book.getId()))
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<NamedEntity> optional = namedEntityMapper.map(result);
                optional.ifPresent(book.getAuthors()::add);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLogs(String bookId, String clientId) {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .insert("store_logs")
                    .fields("user_id", "book_id")
                    .values(clientId, bookId)
                    .build();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
