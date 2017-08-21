package ua.epam.spring.hometask.impl;

import ua.epam.spring.hometask.data.UserDAO;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class UserServiceImpl implements UserService {

    private UserDAO users;

    public UserServiceImpl(UserDAO users) {
        this.users = users;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
    @Override
    public User save(@Nonnull User object) {
        users.save(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        users.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users.getAll();
    }
}
