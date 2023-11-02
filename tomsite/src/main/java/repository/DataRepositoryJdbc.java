package repository;

import interfaces.DataRepository;
import models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;

public class DataRepositoryJdbc implements DataRepository {

    private final DataSource dataSource;

    public DataRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void adminSave(User user) {
        String role = "admin";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            String sql1 = "insert into users_2(username, role, email, password)" +
                    " values ('" + user.getUsername() + "', '" + role + "', '" + user.getEmail() + "', '" + user.getPassword() + "');";
            statement.executeUpdate(sql1);

            String sql2 = "insert into uuid_2(uuid)" +
                    " values ('" + user.getUuid() + "');";
            statement.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void userSave(User user) {
        String role = "user";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            String sql1 = "insert into users_2(username, role, email, password)" +
                    " values ('" + user.getUsername() + "', '" + role  + "', '" + user.getEmail() + "', '" + user.getPassword() + "');";
            statement.executeUpdate(sql1);

            String sql2 = "insert into uuid_2(uuid)" +
                    " values ('" + user.getUuid() + "');";
            statement.executeUpdate(sql2);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delUser(String id) {
        Integer user_id = Integer.parseInt(id);

        try (Connection connection = dataSource.getConnection()) {

            String SQL1 = "DELETE FROM users_2 WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection connection = dataSource.getConnection()) {

            String SQL2 = "DELETE FROM uuid_2 WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL2);
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void addUUIDCookie(String uuid, HttpServletResponse response) {
        Cookie uuidCookie = new Cookie("id", uuid);
        response.addCookie(uuidCookie);
        uuidCookie.setMaxAge(3600);
    }

    @Override
    public void addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        cookie.setMaxAge(3600);
    }

}