package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class UserDAO implements AbstractDomainObjectService<User> {

    private Set<User> users;

    public UserDAO(Set<User> users) {
        this.users = users;
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

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users;
    }
}
