package ua.epam.spring.hometask.domain.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
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

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BookingServiceImplTest {

    @Configuration
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
        public EventService eventService() {
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

    @Test
    public void shouldBookTickets() {
        // Given
        Ticket ticket = new Ticket(
                userService.getById(1L),
                eventService.getByName("The Goodfather"),
                eventService.getByName("The Goodfather").getAirDates().first(),
                1);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);

        // When
        bookingService.bookTickets(tickets);

        // Then
        Assert.assertEquals(1L, ticketRepository.getAll().size());
    }

    @Test
    public void shouldGetPurchasedTicketsForEvent() {
        // Given
        Ticket ticket1 = new Ticket(
                userService.getById(1L),
                eventService.getByName("The Goodfather"),
                eventService.getByName("The Goodfather").getAirDates().first(),
                1);

        Ticket ticket2 = new Ticket(
                userService.getById(1L),
                eventService.getByName("The Goodfather"),
                eventService.getByName("The Goodfather").getAirDates().first(),
                2);

        Ticket ticket3 = new Ticket(
                userService.getById(1L),
                eventService.getByName("The Goodfather"),
                eventService.getByName("The Goodfather").getAirDates().first(),
                3);

        Ticket ticket4 = new Ticket(
                userService.getById(1L),
                eventService.getByName("Scary Movie"),
                eventService.getByName("Scary Movie").getAirDates().first(),
                2);

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
        tickets.add(ticket4);

        bookingService.bookTickets(tickets);

        // When
        Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(
                eventService.getByName("The Goodfather"),
                eventService.getByName("The Goodfather").getAirDates().first());

        // Then
        Assert.assertEquals(3, purchasedTickets.size());
    }
}
