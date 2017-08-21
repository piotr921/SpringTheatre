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
    public void shouldInitalizeServiceWithProperNumberOfUsers() {
        // Given
        UserServiceImpl service = context.getBean("userService", UserServiceImpl.class);

        // Then
        Assert.assertEquals(4, service.getAll().size());
    }
}
