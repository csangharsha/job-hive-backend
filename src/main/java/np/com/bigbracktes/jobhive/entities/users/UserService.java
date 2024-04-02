package np.com.bigbracktes.jobhive.entities.users;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);
    Optional<User> findUserByUsername(String username);
    User save(User user);
    Optional<User> findUserByEmail(String email);
}
