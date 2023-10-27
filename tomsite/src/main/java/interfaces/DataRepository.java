package interfaces;

import javax.servlet.http.HttpServletResponse;

public interface DataRepository {
    void userSave(String username, String email, String password, String uuid);
    void addUUIDCookie(String uuid, HttpServletResponse response);
    void addCookie(String name, String value, HttpServletResponse response);
    void adminSave(String username, String email, String password, String uuid);
    void delUser(String id);
}
