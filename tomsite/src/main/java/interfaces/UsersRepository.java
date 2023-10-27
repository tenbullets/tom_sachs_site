package interfaces;

import interfaces.CrudRepository;
import models.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List allUsers();
    List allAdmins();
}
