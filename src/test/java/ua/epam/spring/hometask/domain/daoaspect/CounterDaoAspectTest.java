package ua.epam.spring.hometask.domain.daoaspect;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
import ua.epam.spring.hometask.dao.CounterAspectDao;
import ua.epam.spring.hometask.dao.CounterAspectDaoImpl;
import ua.epam.spring.hometask.daoaspect.CounterDaoAspectService;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.impl.BookingServiceImpl;
import ua.epam.spring.hometask.impl.EventServiceImpl;
import ua.epam.spring.hometask.impl.UserServiceImpl;
import ua.epam.spring.hometask.repostitory.AuditoriumRepository;
import ua.epam.spring.hometask.repostitory.AuditoriumRepositoryImpl;
import ua.epam.spring.hometask.repostitory.EventRepository;
import ua.epam.spring.hometask.repostitory.EventRepositoryImpl;
import ua.epam.spring.hometask.repostitory.TicketRepository;
import ua.epam.spring.hometask.repostitory.TicketRepositoryImpl;
import ua.epam.spring.hometask.repostitory.UserRepository;
import ua.epam.spring.hometask.repostitory.UserRepositoryImpl;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CounterDaoAspectTest {

    @Configuration
    @PropertySource("h2-db.properties")
    @EnableTransactionManagement
    @EnableAspectJAutoProxy
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
        EventService eventService() {
            EventService eventService = new EventServiceImpl();
            return eventService;
        }

        @Bean
        public EventRepository eventRepository() {
            EventRepository eventRepository = new EventRepositoryImpl();
            return eventRepository;
        }

        @Bean
        CounterDaoAspectService counterDaoAspectService() {
            CounterDaoAspectService counterDaoAspectService = new CounterDaoAspectService();
            return counterDaoAspectService;
        }

        @Bean
        CounterAspectDao counterAspectDao() {
            CounterAspectDao counterAspectDao = new CounterAspectDaoImpl(dataSource());
            return counterAspectDao;
        }

        @Bean
        TicketRepository ticketRepository() {
            TicketRepository ticketRepository = new TicketRepositoryImpl();
            return ticketRepository;
        }

        @Bean
        AuditoriumService auditoriumService() {

            AuditoriumService auditoriumService = new AuditoriumServiceImpl();
            return auditoriumService;
        }

        @Bean
        AuditoriumRepository auditoriumRepository() {
            Set<String> propertyFiles = new HashSet<>();
            propertyFiles.add("src/main/resources/big.properties");
            propertyFiles.add("src/main/resources/medium.properties");
            propertyFiles.add("src/main/resources/small.properties");
            propertyFiles.add("src/main/resources/exclusive.properties");

            AuditoriumRepository auditoriumRepository = new AuditoriumRepositoryImpl(propertyFiles);
            return auditoriumRepository;
        }

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

        @Bean
        public BookingService bookingService() {
            BookingService bookingService = new BookingServiceImpl();
            return bookingService;
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    EventService eventService;

    @Autowired
    CounterDaoAspectService aspect;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    AuditoriumService auditoriumService;

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;

    @Before
    public void before() throws SQLException {
        Resource createTableScript = new ClassPathResource("createCounterAspectTable.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(createTableScript);
        populator.populate(dataSource.getConnection());
    }

    @After
    public void after() throws SQLException {
        Resource dropTableScript = new ClassPathResource("dropCounterAspectTable.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dropTableScript);
        populator.populate(dataSource.getConnection());
    }

    @Test
    public void shouldCountGettingEventByName() {
        // Given
        eventService.getByName("The Goodfather");
        eventService.getByName("The Goodfather");
        eventService.getByName("The Goodfather 3");

        // Then
        assertEquals(2, aspect.getNoOfGetEventByNameOperations("The Goodfather"));
    }

    @Test
    public void shouldCountQueryingTicketsPrice() {
        // Given
        ticketRepository.clear();

        Event godfatherMovie = eventService.getByName("The Goodfather");
        LocalDateTime firstGodfatherShow = godfatherMovie.getAirDates().first();

        NavigableMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();
        auditoriumMap.put(firstGodfatherShow, auditoriumService.getByName("Exclusive"));

        godfatherMovie.setAuditoriums(auditoriumMap);


        Ticket ticket1 = new Ticket(
                userService.getById(1L),
                godfatherMovie,
                godfatherMovie.getAirDates().first(),
                15);

        Ticket ticket2 = new Ticket(
                userService.getById(1L),
                godfatherMovie,
                godfatherMovie.getAirDates().first(),
                16);

        Set<Long> seats = new HashSet<>();
        seats.add(15L);
        seats.add(16L);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        bookingService.bookTickets(tickets);

        // When
        bookingService.getTicketsPrice(godfatherMovie, firstGodfatherShow, userService.getById(1L), seats);

        // Then
        assertEquals(2, aspect.getNoOfBookTicketsOperations("The Goodfather"));
        assertEquals(1, aspect.getNoOfQueryPriceOperations("The Goodfather"));
    }
}
