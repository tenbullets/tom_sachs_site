package interfaces;

import models.User;

import javax.servlet.http.HttpServletResponse;

public interface DataRepository {
    void adminSave(User user);
    void userSave(User user);
    void delUser(String id);
    void addUUIDCookie(String uuid, HttpServletResponse response);
    void addCookie(String name, String value, HttpServletResponse response);
}
