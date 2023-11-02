package interfaces;

import java.sql.SQLException;

public interface SignUpService {
    void signUp(String username, String email, String password, String uuid) throws SQLException;
    void adminSignUp(String username, String email, String password, String uuid) throws SQLException;
}
