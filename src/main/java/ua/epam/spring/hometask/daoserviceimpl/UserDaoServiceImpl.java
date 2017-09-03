package ua.epam.spring.hometask.daoserviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class UserDaoServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDao
                .getAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(@Nonnull User object) {
        userDao.save(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        userDao.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }
}
