package interfaces;

import java.sql.SQLException;

public interface CrudRepository<T> {

    boolean findUser(String username, String password);

    boolean findUserByEmail(String email);

    String findUserByUuid(String uuid) throws SQLException;

    String returnUuid(String username) throws SQLException;

    boolean findUserByName(String username) throws SQLException;

    String returnEmail(String username);
    boolean isAdmin(String username, String password);

    String returnId(String username);

}
