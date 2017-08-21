package ua.epam.spring.hometask.domain.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.impl.UserServiceImpl;

public class UserServiceImplTest {

    private  ConfigurableApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("users.xml");
    }

    @Test
    public void shouldInitalizeServiceWithProperNumberOfUsers() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);

        // Then
        Assert.assertEquals(4, service.getAll().size());
    }

    @Test
    public void shouldGetUserById() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);

        // When
        User getUser = service.getById(1L);

        // Then
        Assert.assertEquals(getUser.getFirstName(), "Jan");
        Assert.assertEquals(getUser.getLastName(), "Kowalski");
        Assert.assertEquals(getUser.getEmail(), "jan_kow@gmail.com");
    }

    @Test
    public void shouldGetUserByEmail() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);

        // When
        User getUser = service.getUserByEmail("ivan89@gmail.com");

        // Then
        Assert.assertEquals(getUser.getFirstName(), "Ivan");
        Assert.assertEquals(getUser.getLastName(), "Novak");
        Assert.assertEquals(getUser.getEmail(), "ivan89@gmail.com");
    }

    @Test
    public void shouldRemoveUser() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);
        User toRemove = context.getBean("user3", User.class);

        // When
        service.remove(toRemove);

        // Then
        Assert.assertEquals(3, service.getAll().size());
    }

    @Test
    public void shouldAddUser() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);
        User toAdd = context.getBean("user5", User.class);

        // When
        service.save(toAdd);

        // Then
        Assert.assertEquals(5, service.getAll().size());
        Assert.assertEquals(service.getById(5L).getFirstName(), "Anna");
        Assert.assertEquals(service.getById(5L).getLastName(), "Malinowska");
        Assert.assertEquals(service.getById(5L).getEmail(), "AnMal@gmail.com");
    }
}
