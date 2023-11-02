package repository;

import interfaces.SignUpService;
import models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService {
    private final PasswordEncoder passwordEncoder;
    private final DataRepositoryJdbc data;

    public SignUpServiceImpl(DataRepositoryJdbc dataRep) {
        this.data = dataRep;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public void signUp(String username, String email, String password, String uuid) throws SQLException {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .uuid(uuid)
                .build();

        data.userSave(user);
    }

    @Override
    public void adminSignUp(String username, String email, String password, String uuid) throws SQLException {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .uuid(uuid)
                .build();

        data.adminSave(user);
    }

}
