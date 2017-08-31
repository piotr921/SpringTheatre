package ua.epam.spring.hometask.domain.aop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.aop.CounterAspect;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.impl.BookingServiceImpl;
import ua.epam.spring.hometask.impl.EventServiceImpl;
import ua.epam.spring.hometask.impl.UserServiceImpl;
import ua.epam.spring.hometask.repostitory.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CounterAspectTest {

    @Configuration
    @EnableAspectJAutoProxy
    static class ContextConfiguration {

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

        @Bean
        CounterAspect counterAspect() {
            CounterAspect counterAspect = new CounterAspect();
            return counterAspect;
        }
    }

    @Autowired
    AuditoriumService auditoriumService;

    @Autowired
    BookingService bookingService;

    @Autowired
    EventService eventService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserService userService;

    @Autowired
    private CounterAspect aspect;

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
