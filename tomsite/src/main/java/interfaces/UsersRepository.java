package interfaces;

import models.User;
import java.util.List;

public interface UsersRepository extends EntityRepository<User> {
    List allUsers();
    List allAdmins();
}
