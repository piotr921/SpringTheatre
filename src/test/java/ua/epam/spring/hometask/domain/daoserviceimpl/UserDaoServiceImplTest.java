package ua.epam.spring.hometask.domain.daoserviceimpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.UserDaoImpl;
import ua.epam.spring.hometask.daoserviceimpl.UserDaoServiceImpl;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserDaoServiceImplTest {

    @Configuration
    @PropertySource("h2-db.properties")
    @EnableTransactionManagement
    static class ContextConfiguration {

        @Autowired
        Environment environment;

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
            dataSource.setUrl(environment.getProperty("jdbc.url"));
            dataSource.setUsername(environment.getProperty("jdbc.username"));
            dataSource.setPassword(environment.getProperty("jdbc.password"));
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public UserService userService() {
            UserService userService = new UserDaoServiceImpl();
            return userService;
        }

        @Bean
        public UserDao userDao() {
            UserDao userDao = new UserDaoImpl(dataSource());
            return userDao;
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Before
    public void before() throws SQLException {
        Resource createTableScript = new ClassPathResource("createUsersTable.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(createTableScript);
        populator.populate(dataSource.getConnection());
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("jan_kow@gmail.com");
        user.setId(1L);
        userService.save(user);
    }

    @Test
    public void shouldGetUserByEmail() {
        // When
        User getUser = userService.getUserByEmail("jan_kow@gmail.com");

        // Then
        assertNotNull(getUser);
        assertEquals("Jan", getUser.getFirstName());
        assertEquals("Kowalski", getUser.getLastName());
    }
}
