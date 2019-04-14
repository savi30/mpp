package bookstore.repository.book;

import bookstore.repository.DBRepository;
import bookstore.utils.builder.SqlQueryBuilder;
import bookstore.utils.domain.book.Book;
import bookstore.utils.domain.core.Entity;
import bookstore.utils.domain.core.NamedEntity;
import bookstore.utils.exception.ValidationException;
import bookstore.utils.validator.Validator;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookRepositoryImpl extends DBRepository<String, Book> implements BookRepository {
    private static final String BOOKS_TABLE = "books";

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(Validator<Book> validator, JdbcTemplate jdbcTemplate) {
        super(validator);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Book> buy(String bookId, String clientId) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .update("books")
                .set(builder.eq("quantity", "quantity - 1"))
                .where(builder.eq("id", bookId))
                .build();
        String updateLogsQuery = builder
                .insert("store_logs")
                .fields("user_id", "book_id")
                .values(clientId, bookId)
                .build();
        this.jdbcTemplate.update(query);
        this.jdbcTemplate.update(updateLogsQuery);
        return findById(bookId);
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidationException {
        Optional<Book> optional = this.findById(entity.getId());
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .update("books")
                .set("title=" + entity.getTitle(), "price=" + entity.getPrice(), "quantity=" + entity.getQuantity(),
                        "`publish_year`=\"" + entity.getPublishYear() + "\"")
                .where(builder.eq("id", entity.getId()))
                .build();
        this.jdbcTemplate.update(query);
        this.updateAuthors(entity.getAuthors());
        return optional;
    }


    @Override
    public Optional<Book> save(Book entity) throws ValidationException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .insert("books")
                .set("id=" + entity.getId(), "title=" + entity.getTitle(), "price=" +
                                entity.getPrice(), "quantity=" + entity.getQuantity(),
                        "`publish_year`=\"" + entity.getPublishYear() + "\"")
                .build();
        this.jdbcTemplate.update(query);
        saveAuthors(entity.getAuthors(), entity.getId());
        return findById(entity.getId());
    }

    @Override
    public Optional<Book> delete(String s) {
        Optional<Book> book = this.findById(s);
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .delete(BOOKS_TABLE)
                .where(builder.eq("id", s))
                .build();
        this.jdbcTemplate.update(query);
        return book;
    }

    @Override
    public Collection<Book> findByAuthor(String author) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .select("books.id", "title", "publish_year", " price", "quantity")
                .from("authors_books")
                .join(BOOKS_TABLE)
                .on(builder.eq("authors_books.book_id", "books.id"))
                .where(builder.eq("authors_books.author_id", author))
                .build();
        Collection<Book> books = (Collection<Book>) jdbcTemplate.query(query, new BeanPropertyRowMapper(Book.class));
        getAuthors(books);
        return books;
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        String criteria = "`title`=\"" + title + "\"";
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

    @Override
    public Optional<Book> findById(String s) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .select("*")
                .from(BOOKS_TABLE)
                .where(builder.eq("id", s))
                .build();
        Book book = (Book) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(Book.class));
        getAuthors(Arrays.asList(book));
        return Optional.of(book);
    }

    @Override
    public Collection<Book> findAll() {
        return this.findAll("");
    }

    private List<Book> findAll(String criteria) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder
                .select("books.id", "title", "publish_year", " price", "quantity")
                .from(BOOKS_TABLE)
                .where(criteria)
                .build();
        List<Book> books = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class));
        getAuthors(books);
        return books;
    }

    private void getAuthors(Collection<Book> books) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        books.forEach(book -> {
            String query = builder
                    .select("id", "name")
                    .from("authors")
                    .join("authors_books")
                    .on(builder.eq("authors.id", "authors_books.author_id"))
                    .where(builder.eq("authors_books.book_id", book.getId()))
                    .build();
            Optional<List<NamedEntity>> author = Optional
                    .of((List<NamedEntity>) jdbcTemplate.query(query, new BeanPropertyRowMapper(NamedEntity.class)));
            author.ifPresent(book.getAuthors()::addAll);
        });
    }

    private void updateAuthors(List<NamedEntity> authors) {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        List<String> queries = new ArrayList<>();
        authors.forEach(author -> {
            queries.add(builder.update("authors").set("`name`=" + author.getName())
                    .where(builder.eq("id", author.getId().toString())).build());
        });
        queries.forEach(query -> {
            this.jdbcTemplate.update(query);
        });
    }

    private void saveAuthors(List<NamedEntity> authors, String bookId) {
        List<NamedEntity<String>> existingAuthors;
        SqlQueryBuilder builder = new SqlQueryBuilder();
        String query = builder.select("*").from("authors").build();
        existingAuthors = this.jdbcTemplate.query(query, new BeanPropertyRowMapper<>());
        List<String> ids = existingAuthors.stream().map(Entity::getId)
                .collect(Collectors.toList());
        List<NamedEntity> unknownAuthors = authors.stream().filter(author -> !ids.contains(author.getId().toString()))
                .collect(Collectors.toList());
        List<String> queries = new ArrayList<>();
        unknownAuthors.forEach(author -> queries
                .add(builder.insert("authors").set("id=" + author.getId(), "`name`=\"" + author.getName())
                        .build() + "\""));
        queries.forEach(q -> {
            this.jdbcTemplate.update(q);
        });
        queries.clear();
        authors.forEach(author -> queries.add(
                builder.insert("authors_books").set("book_id=" + bookId, "author_id=" + author.getId()).build()));
        queries.forEach(q -> {
            this.jdbcTemplate.update(q);
        });
    }
}

