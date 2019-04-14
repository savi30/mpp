package bookstore.repository.book;

import bookstore.utils.domain.book.Book;
import bookstore.utils.domain.core.Entity;
import bookstore.utils.domain.core.NamedEntity;
import bookstore.repository.DBRepository;
import bookstore.repository.config.MySqlDatabaseConnector;
import bookstore.utils.builder.SqlQueryBuilder;
import bookstore.utils.mapper.ObjectMapper;
import bookstore.utils.validator.Validator;
import bookstore.utils.exception.ValidationException;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookMySqlRepository extends DBRepository<String, Book> implements BookRepository {
    private static final String BOOKS_TABLE = "books";

    private ObjectMapper<Book> objectMapper;

    public BookMySqlRepository(Validator<Book> validator) {
        super(validator);
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
            if(result.isBeforeFirst()) {
                result.next();
                optional = objectMapper.map(result);
                this.getAuthors(connection, Collections.singletonList(optional.get()));
            }
            statement.close();
            statement.close();
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
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<Book> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            getAuthors(connection, list);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Optional<Book> buy(String bookId, String clientId) {
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .update("books")
                    .set(builder.eq("quantity", "quantity - 1"))
                    .where(builder.eq("id", bookId))
                    .build();
            statement.executeUpdate(query);
            this.updateLogs(connection, bookId, clientId);
            connection.commit();
            statement.close();
            return this.findById(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book entity){
        Optional<Book> optional = this.findById(entity.getId());
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .update("books")
                    .set("title=" + entity.getTitle(), "price=" + entity.getPrice(), "quantity=" + entity.getQuantity(),
                            "`publish_year`=\"" + entity.getPublishYear()+"\"")
                    .where(builder.eq("id", entity.getId()))
                    .build();
            statement.executeUpdate(query);
            updateAuthors(connection, entity.getAuthors());
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Optional<Book> save(Book entity) {
        Optional<Book> optional = this.findById(entity.getId());
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            final Optional<ValidationException>[] ex = new Optional[1];
            try{
                validator.validate(entity);
                ex[0] = Optional.empty();
            }catch (ValidationException ve){
                ex[0] = Optional.of(ve);
            }
            final String[] query = new String[1];
            optional.ifPresentOrElse(
                    opt -> {
                        try {
                                query[0] = builder
                                        .update("books")
                                        .set("quantity=" + (entity.getQuantity() + opt.getQuantity()))
                                        .where(builder.eq("id", entity.getId()))
                                        .build();
                                statement.executeUpdate(query[0]);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        },
                    ()-> ex[0].ifPresentOrElse(Throwable::printStackTrace,
                            ()-> {
                                try {
                                        query[0] = builder
                                                .insert("books")
                                                .set("id="+entity.getId(), "title=" + entity.getTitle(), "price=" +
                                                                entity.getPrice(), "quantity=" + entity.getQuantity(),
                                                        "`publish_year`=\"" + entity.getPublishYear()+"\"")
                                                .build();
                                        statement.executeUpdate(query[0]);
                                        saveAuthors(connection, entity.getAuthors(), entity.getId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            )
            );
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Collection<Book> findByAuthor(String authorId) {
        List<Book> list = new ArrayList<>();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .select("books.id", "title", "publish_year", " price", "quantity")
                    .from("authors_books")
                    .join("books")
                    .on(builder.eq("authors_books.book_id", "books.id"))
                    .where(builder.eq("authors_books.author_id", authorId))
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<Book> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            getAuthors(connection, list);
            statement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        String criteria = "`title`=\"" + title+"\"";
        return findAll(criteria);
    }

    @Override
    public Collection<Book> findByDate(Timestamp t1, Timestamp t2) {
        String criteria = "publish_year between " + t1 + " and " + t2;
        return findAll(criteria);
    }

    @Override
    public Collection<Book> findByPrice(Double p1, Double p2) {
        String criteria = "price between " + p1 + " and " + p2;
        return findAll(criteria);
    }

    @Override
    public Collection<Book> findByQuantity(Integer quantity) {
        String criteria = "quantity=" + quantity;
        return findAll(criteria);
    }

    private void getAuthors(Connection connection, List<Book> books) throws SQLException {
        Statement statement = connection.createStatement();
        SqlQueryBuilder builder = new SqlQueryBuilder();
        ObjectMapper<NamedEntity> namedEntityMapper = new ObjectMapper<>(NamedEntity.class);
        books.forEach(book -> {
            String query = builder
                    .select("id", "name")
                    .from("authors")
                    .join("authors_books")
                    .on(builder.eq("authors.id", "authors_books.author_id"))
                    .where(builder.eq("authors_books.book_id", book.getId()))
                    .build();
            ResultSet result = null;
            try {
                result = statement.executeQuery(query);
                while (result.next()) {
                    Optional<NamedEntity> optional = namedEntityMapper.map(result);
                    optional.ifPresent(book.getAuthors()::add);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        statement.close();
    }

    private void updateLogs(Connection connection, String bookId, String clientId) throws SQLException {
        Statement statement = connection.createStatement();
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .insert("store_logs")
                .fields("user_id", "book_id")
                .values(clientId, bookId)
                .build();
        statement.executeUpdate(query);
        statement.close();
    }

    private void updateAuthors(Connection connection, List<NamedEntity> authors) throws SQLException {
        Statement statement = connection.createStatement();
        SqlQueryBuilder builder = new SqlQueryBuilder();
        List<String> queries = new ArrayList<>();
        authors.forEach(author -> {
            queries.add(builder.update("authors").set("`name`=" + author.getName())
                    .where(builder.eq("id", author.getId().toString())).build());
        });
        queries.forEach(query -> {
            try {
                statement.addBatch(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        statement.executeBatch();
    }

    private void saveAuthors(Connection connection, List<NamedEntity> authors, String bookId)
            throws SQLException {
        Statement statement = connection.createStatement();
        List<NamedEntity<String>> existingAuthors = new ArrayList<>();
        ObjectMapper<NamedEntity> namedEntityMapper = new ObjectMapper<>(NamedEntity.class);
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder.select("*").from("authors").build();
        statement.execute(query);
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            Optional<NamedEntity> optional = namedEntityMapper.map(result);
            optional.ifPresent(existingAuthors::add);
        }
        List<String> ids = existingAuthors.stream().map(Entity::getId)
                .collect(Collectors.toList());
        List<NamedEntity> unknownAuthors = authors.stream().filter(author -> !ids.contains(author.getId().toString()))
                .collect(Collectors.toList());
        List<String> queries = new ArrayList<>();
        unknownAuthors.forEach(author -> {
            queries.add(builder.insert("authors").set("id=" + author.getId(), "`name`=\"" + author.getName()).build()+"\"");
        });
        statement.close();
        Statement batchStatement = connection.createStatement();
        queries.forEach(q -> {
            try {
                batchStatement.addBatch(q);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        batchStatement.executeBatch();
        batchStatement.close();

        queries.clear();
        authors.forEach(author -> queries.add(
                builder.insert("authors_books").set("book_id=" + bookId, "author_id=" + author.getId()).build()));

        Statement authorBooksStatement = connection.createStatement();
        queries.forEach(q -> {
            try {
                authorBooksStatement.addBatch(q);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        authorBooksStatement.executeBatch();
        authorBooksStatement.close();

    }

    private List<Book> findAll(String criteria) {
        List<Book> list = new ArrayList<>();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .select("books.id", "title", "publish_year", " price", "quantity")
                    .from(BOOKS_TABLE)
                    .where(criteria)
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<Book> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            getAuthors(connection, list);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
