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
        this.query += " WHERE " + value;
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
        return this.query;
    }

}
