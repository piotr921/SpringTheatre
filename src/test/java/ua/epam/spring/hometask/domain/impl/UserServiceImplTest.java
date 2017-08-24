package ua.epam.spring.hometask.domain.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.impl.UserServiceImpl;
import ua.epam.spring.hometask.repostitory.UserRepository;
import ua.epam.spring.hometask.repostitory.UserRepositoryImpl;
import ua.epam.spring.hometask.service.UserService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {


    @Configuration
    static class ContextConfiguration {

        @Bean
        public UserService userService() {
            UserService userService = new UserServiceImpl();
            return userService;
        }

        @Bean
        public UserRepository userRepository() {
            UserRepository userRepository = new UserRepositoryImpl();
            return userRepository;
        }
    }

    @Autowired
    private UserService service;

    @Test
    public void shouldGetUserById() {
        // When
        User getUser = service.getById(1L);

        // Then
        assertEquals(getUser.getFirstName(), "Jan");
        assertEquals(getUser.getLastName(), "Kowalski");
        assertEquals(getUser.getEmail(), "jan_kow@gmail.com");
    }

    @Test
    public void shouldGetUserByEmail() {
        // When
        User getUser = service.getUserByEmail("ivan89@gmail.com");

        // Then
        assertEquals(getUser.getFirstName(), "Ivan");
        assertEquals(getUser.getLastName(), "Novak");
        assertEquals(getUser.getEmail(), "ivan89@gmail.com");
    }

    @Test
    public void shouldRemoveUser() {
        // Given
        int startSize = service.getAll().size();

        User toRemove = new User();
        toRemove.setId(2L);
        toRemove.setFirstName("John");
        toRemove.setLastName("Smith");
        toRemove.setEmail("jSmith@gmail.com");

        // When
        service.remove(toRemove);

        // Then
        assertEquals(startSize - 1, service.getAll().size());
    }

    @Test
    public void shouldAddUser() {
        // Given
        int startSize = service.getAll().size();

        User toAdd = new User();
        toAdd.setId(5L);
        toAdd.setFirstName("Added");
        toAdd.setLastName("User");
        toAdd.setEmail("added@gmail.com");

        // When
        service.save(toAdd);

        // Then
        assertEquals(startSize + 1, service.getAll().size());
        assertEquals(service.getById(5L).getFirstName(), "Added");
        assertEquals(service.getById(5L).getLastName(), "User");
        assertEquals(service.getById(5L).getEmail(), "added@gmail.com");
    }
}
