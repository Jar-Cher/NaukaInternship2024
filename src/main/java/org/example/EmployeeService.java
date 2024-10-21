package org.example;

import java.sql.*;

public class EmployeeService {
    private Connection connection;
    private Statement statement;

    private static String resultSetToString(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return "N/A";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            result.append(resultSet.getMetaData().getColumnName(i)).append("\t");
        }
        result.append("\n");
        do {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                result.append(resultSet.getString(i)).append("\t");
            }
            result.append("\n");
        } while (resultSet.next());
        return result.toString();
    }

    public EmployeeService(String url, String login, String password) {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            String sql = "CREATE TABLE employee " +
                    "(id SERIAL, " +
                    " name VARCHAR(255) not NULL, " +
                    " surname VARCHAR(255) not NULL, " +
                    " birth_date DATE not NULL, " +
                    " department VARCHAR(255) not NULL, " +
                    " wage INTEGER not NULL, " +
                    " PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillData(int entries) {
        try {
            for (int i = 0; i < entries; i++) {
                String name = RandomDataGenerator.getRandomString();
                String surname = RandomDataGenerator.getRandomString();
                String birthDate = RandomDataGenerator.getRandomLocalDate();
                String department = RandomDataGenerator.getRandomString();
                int wage = RandomDataGenerator.getRandomWage();
                statement.executeUpdate(
                        "INSERT INTO \"employee\" (name, surname, birth_date, department, wage)" +
                                String.format("VALUES ('%s', '%s', '%s', '%s', '%d')",
                                        name, surname, birthDate, department, wage)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findBetween(String from, String to) {
        try {
            return resultSetToString(
                    statement.executeQuery(
                            String.format("SELECT * FROM \"employee\" WHERE birth_date >= '%s' AND birth_date <= '%s'",
                                    from, to)
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String findById(int id) {
        try {
            return resultSetToString(
                    statement.executeQuery(String.format("SELECT * FROM \"employee\" WHERE id = %d", id)
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String groupByName() {
        try {
            return resultSetToString(
                    statement.executeQuery(
                            "SELECT name, count(*) FROM \"employee\" GROUP BY name ORDER BY count DESC"
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String list() {
        try {
            return resultSetToString(
                    statement.executeQuery("SELECT * FROM \"employee\""
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
