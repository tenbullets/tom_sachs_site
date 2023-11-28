package repository;

import interfaces.UsersRepository;
import models.AuthUser;
import models.User;
import models.UsersListDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersRepositoryJdbc implements UsersRepository {
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    private static final String USERS = "select * from users_2";
    private static final String UUID = "select * from uuid_2";

    public UsersRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List allUsers() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);
            List<UsersListDTO> result = new ArrayList<>();

            while (resultSet.next()) {
                UsersListDTO user = UsersListDTO.builder()
                        .username(resultSet.getString("username"))
                        .email(resultSet.getString("email"))
                        .role(resultSet.getString("role"))
                        .build();

                if (!user.getRole().equals("admin")) result.add(user);
            }

            connection.close();
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List allAdmins() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);
            List<UsersListDTO> result = new ArrayList<>();

            while (resultSet.next()) {
                UsersListDTO user = UsersListDTO.builder()
                        .username(resultSet.getString("username"))
                        .email(resultSet.getString("email"))
                        .role(resultSet.getString("role"))
                        .build();

                if (user.getRole().equals("admin")) result.add(user);
            }

            connection.close();
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findUser(String username, String password) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                AuthUser user = AuthUser.builder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();

                if (user.getUsername().equals(username) &&
                        passwordEncoder.matches(password, user.getPassword())) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findUserByEmail(String email) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                UsersListDTO user = UsersListDTO.builder()
                        .email((resultSet.getString("email")))
                        .build();

                if (user.getEmail().equals(email)) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String findUserByUuid(String uuid) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {

            User copy = null;
            ResultSet uuidDb = statement.executeQuery(UUID);
            while (uuidDb.next()) {
                User user = User.builder()
                        .id(uuidDb.getString("user_id"))
                        .uuid(uuidDb.getString("uuid"))
                        .build();

                if(user.getUuid().equals(uuid)) copy = user;
            }

            ResultSet usersDb = statement.executeQuery(USERS);
            while (usersDb.next()) {
                User user = User.builder()
                        .id(usersDb.getString("user_id"))
                        .username(usersDb.getString("username"))
                        .build();
                assert copy != null;

                if(Objects.equals(copy.getId(), user.getId())) return user.getUsername();
            }

            statement.close();
            return "0";
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String returnUuid(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()){

            User copy = null;
            ResultSet usersDb = statement.executeQuery(USERS);
            while (usersDb.next()) {
                User user = User.builder()
                        .id(usersDb.getString("user_id"))
                        .username(usersDb.getString("username"))
                        .build();

                if(user.getUsername().equals(username)) copy = user;
            }

            ResultSet uuidDb = statement.executeQuery(UUID);
            while (uuidDb.next()) {
                User user = User.builder()
                        .id(uuidDb.getString("user_id"))
                        .uuid(uuidDb.getString("uuid"))
                        .build();
                assert copy != null;

                if(Objects.equals(copy.getId(), user.getId())) return user.getUuid();
            }

            statement.close();
            return "0";
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findUserByName(String username) throws SQLException {
        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                UsersListDTO user = UsersListDTO.builder()
                        .username(resultSet.getString("username"))
                        .build();

                if (user.getUsername().equals(username)) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String returnEmail(String username) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                UsersListDTO user = UsersListDTO.builder()
                        .username(resultSet.getString("username"))
                        .email(resultSet.getString("email"))
                        .build();

                if (user.getUsername().equals(username)) return user.getEmail();
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isAdmin(String username, String password) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .role(resultSet.getString("role"))
                        .build();

                if(
                        user.getUsername().equals(username)
                        && passwordEncoder.matches(password, user.getPassword())
                        && user.getRole().equals("admin")
                )
                    return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String returnId(String username) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .id(resultSet.getString("user_id"))
                        .build();

                if (user.getUsername().equals(username)) return user.getId();
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User returnUser(String id) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .id(resultSet.getString("user_id"))
                        .email(resultSet.getString("email"))
                        .build();

                if (user.getId().equals(id)) return user;
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}