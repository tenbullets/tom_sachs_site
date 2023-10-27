package repository;

import interfaces.DataRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

import static javax.management.remote.JMXConnectorFactory.connect;

public class DataRepositoryJdbc implements DataRepository {
    private final Connection connection;
    private final Statement statement;
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    public DataRepositoryJdbc(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    @Override
    public void userSave(String username, String email, String password, String uuid) {
        String role = "user";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            Statement statement = connection.createStatement();

            String sql1 = "insert into users_2(username, role, email, password)" +
                    " values ('" + username + "', '" + role + "', '" + email + "', '" + password + "');";
            statement.executeUpdate(sql1);

            String sql2 = "insert into uuid_2(uuid)" +
                    " values ('" + uuid + "');";
            statement.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delUser(String id) {
        Integer user_id = Integer.parseInt(id);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            String SQL1 = "DELETE FROM users_2 WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            String SQL2 = "DELETE FROM uuid_2 WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL2);
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void adminSave(String username, String email, String password, String uuid) {
        String role = "admin";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            Statement statement = connection.createStatement();

            String sql1 = "insert into users_2(username, role, email, password)" +
                    " values ('" + username + "', '" + role + "', '" + email + "', '" + password + "');";
            statement.executeUpdate(sql1);

            String sql2 = "insert into uuid_2(uuid)" +
                    " values ('" + uuid + "');";
            statement.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addUUIDCookie(String uuid, HttpServletResponse response) {
        Cookie uuidCookie = new Cookie("id", uuid);
        response.addCookie(uuidCookie);
        uuidCookie.setMaxAge(3600 * 24);
    }

    @Override
    public void addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        cookie.setMaxAge(3600);
    }

}