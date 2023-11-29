package service;

import interfaces.RegService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegServiceImpl implements RegService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_USERNAME_REGEX =
            Pattern.compile("^[a-z0-9_-]{3,16}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean formAttached(String username, String email, String password, String secondPassword) {
        return usernameValidate(username) && emailValidate(email) && passwordValidate(password) && passwordsMatch(password, secondPassword);
    }

    @Override
    public boolean emailValidate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean passwordValidate(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    @Override
    public boolean usernameValidate(String username) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        return matcher.matches();
    }

    @Override
    public boolean passwordsMatch(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    @Override
    public void delProdCookie(HttpServletResponse response) {
        Cookie prodCookie = new Cookie("prod", "");
        Cookie countCookie = new Cookie("count", "");

        response.addCookie(prodCookie);
        response.addCookie(countCookie);
        prodCookie.setMaxAge(0);
        countCookie.setMaxAge(0);
    }
}
