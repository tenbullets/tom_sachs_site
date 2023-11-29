package interfaces;

import javax.servlet.http.HttpServletResponse;

public interface RegService {
    boolean formAttached(String username, String email, String password, String secondPassword);
    boolean emailValidate(String email);
    boolean passwordValidate(String password);
    boolean usernameValidate(String username);
    boolean passwordsMatch(String pass1, String pass2);
    void delProdCookie(HttpServletResponse response);
}
