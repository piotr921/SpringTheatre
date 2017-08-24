package ua.epam.spring.hometask.repostitory;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;

public interface UserRepository {
    User getUserByEmail(String email);

    User save(User object);

    void remove(User object);

    User getById(Long id);

    Collection<User> getAll();
}
