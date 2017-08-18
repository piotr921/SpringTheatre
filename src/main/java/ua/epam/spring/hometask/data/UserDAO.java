package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class UserDAO implements UserService {

    private Map<Integer, User> users;

    public UserDAO(Map<Integer, User> users) {
        this.users = users;
    }

    @Override
    public User save(@Nonnull User object) {
        Integer key = users.size();
        users.put(key, object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        if (users.containsValue(object)) {
            users.remove(object);
        }
    }

    @Nullable
    @Override
    public User getById(@Nonnull Long id) {
        return users.values().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
