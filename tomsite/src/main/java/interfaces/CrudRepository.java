package interfaces;

public interface CrudRepository<T> {

    boolean findUser(String username, String password);

    boolean findUserByEmail(String email);

    String findUserByUuid(String uuid);

    String returnUuid(String username);

    boolean findUserByName(String username);

    String returnEmail(String username);
    boolean isAdmin(String username, String password);

    String returnId(String username);

}
