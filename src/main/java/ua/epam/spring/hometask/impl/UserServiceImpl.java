package ua.epam.spring.hometask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.repostitory.UserRepository;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User object) {
        repository.save(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        repository.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return repository.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return repository.getAll();
    }
}
