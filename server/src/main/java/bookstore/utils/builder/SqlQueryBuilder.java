package bookstore.utils.builder;

public class SqlQueryBuilder {
    private String query = "";

    public SqlQueryBuilder select(String... value) {
        this.query += " SELECT " + String.join(",", value);
        return this;
    }

    public SqlQueryBuilder from(String value) {
        this.query += " FROM " + String.join(",", value);
        return this;
    }

    public SqlQueryBuilder where(String value) {
        if (value != null && !value.isEmpty()) {
            this.query += " WHERE " + value;
        }
        return this;
    }

    public String and(String first, String second) {
        return first + " AND " + second + " ";
    }

    public String or(String first, String second) {
        return first + " OR " + second + " ";
    }

    public String eq(String first, String second) {
        return first + "=" + second + " ";
    }

    public String ne(String first, String second) {
        return first + "<>" + second + " ";
    }

    public String build() {
        String result = this.query;
        this.query = "";
        return result;
    }

    public SqlQueryBuilder join(String value) {
        this.query += " JOIN " + value + " ";
        return this;
    }

    public SqlQueryBuilder on(String value) {
        this.query += " ON " + value + " ";
        return this;
    }

    public SqlQueryBuilder groupBy(String... value) {
        this.query += " GROUP BY " + String.join(",", value) + " ";
        return this;
    }

    public SqlQueryBuilder update(String value) {
        this.query += " UPDATE " + value + " ";
        return this;
    }

    public SqlQueryBuilder set(String... value) {
        this.query += " SET " + String.join(",", value) + " ";
        return this;
    }

    public SqlQueryBuilder delete(String value) {
        this.query += " DELETE FROM " + value + " ";
        return this;
    }

    public SqlQueryBuilder insert(String value) {
        this.query += " INSERT INTO " + value + " ";
        return this;
    }

    public SqlQueryBuilder fields(String... value) {
        this.query += " (" + String.join(",", value) + ") ";
        return this;
    }

    public SqlQueryBuilder values(String... value) {
        this.query += " VALUES (" + String.join(",", value) + ") ";
        return this;
    }
}
