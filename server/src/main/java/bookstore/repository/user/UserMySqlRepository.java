package bookstore.repository.user;


import bookstore.utils.domain.user.User;
import bookstore.repository.DBRepository;
import bookstore.repository.config.MySqlDatabaseConnector;
import bookstore.utils.builder.SqlQueryBuilder;
import bookstore.utils.mapper.ObjectMapper;
import bookstore.utils.validator.Validator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMySqlRepository extends DBRepository<String, User> implements UserRepository {
    private static final String USERS_TABLE = "users";

    private ObjectMapper<User> objectMapper;

    public UserMySqlRepository(Validator<User> validator){
        super(validator);
        this.objectMapper = new ObjectMapper<>(User.class);
    }

    @Override
    public Optional<User> findById(String id){
        Optional<User> optional = Optional.empty();
        try(Connection connection = MySqlDatabaseConnector.getConnection()){
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder.select("*").from(USERS_TABLE).where(builder.eq("id", id)).build();
            ResultSet result = statement.executeQuery(query);
            if(result.isBeforeFirst()) {
                result.next();
                optional = objectMapper.map(result);
            }
            statement.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return optional;
    }

    @Override
    public Collection<User> findByName(String name){
        return this.findAll().stream().filter(user -> user.getName().contains(name)).collect(Collectors.toSet());
    }

    @Override
    public Collection<User> findAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .select("*")
                    .from("users")
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<User> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<User> delete(String id) {
        Optional<User> optional = this.findById(id);
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .delete("users")
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
    public Optional<User> update(User entity) {
        Optional<User> optional = this.findById(entity.getId());
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .update("users")
                    .set("`name`=\"" + entity.getName() +"\"")
                    .where(builder.eq("id", entity.getId()))
                    .build();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Optional<User> save(User entity){
        Optional<User> optional = this.findById(entity.getId());
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            SqlQueryBuilder builder = new SqlQueryBuilder();
            Statement statement = connection.createStatement();
            String query = builder
                    .insert("users")
                    .set("id=" + entity.getId(), "`name`=\"" + entity.getName()+"\"")
                    .build();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }
}
