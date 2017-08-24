package ua.epam.spring.hometask.repostitory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Set<User> users;

    {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");
        user1.setEmail("jan_kow@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setEmail("jSmith@gmail.com");

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Ivan");
        user3.setLastName("Novak");
        user3.setEmail("ivan89@gmail.com");

        User user4 = new User();
        user4.setId(4L);
        user4.setFirstName("Anna");
        user4.setLastName("Malinowska");
        user4.setEmail("AnMal@gmail.com");

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        this.users = users;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public User save(@Nonnull User object) {
        users.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        if (users.contains(object)) {
            users.remove(object);
        }
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(new User());
    }

    @Override
    public Collection<User> getAll() {
        return users;
    }
}
