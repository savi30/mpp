package bookstore.repository.logs;

import bookstore.utils.domain.logs.LogsEntry;
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

public class LogsMySqlRepository extends DBRepository<String, LogsEntry> implements LogsRepository {
    private ObjectMapper<LogsEntry> objectMapper;

    public LogsMySqlRepository(Validator<LogsEntry> validator){
        super(validator);
        this.objectMapper = new ObjectMapper<>(LogsEntry.class);
    }

    @Override
    public Collection<LogsEntry> findAll() {
        List<LogsEntry> list = new ArrayList<>();
        try (Connection connection = MySqlDatabaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            SqlQueryBuilder builder = new SqlQueryBuilder();
            String query = builder
                    .select("*")
                    .from("store_logs")
                    .build();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Optional<LogsEntry> optional = objectMapper.map(result);
                optional.ifPresent(list::add);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
