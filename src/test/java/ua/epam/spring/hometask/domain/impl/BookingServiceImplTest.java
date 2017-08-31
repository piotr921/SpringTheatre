package ua.epam.spring.hometask.domain.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
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

import static org.junit.Assert.assertEquals;

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
        ticketRepository.clear();

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
        assertEquals(1L, ticketRepository.getAll().size());
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
        assertEquals(3, purchasedTickets.size());
    }

    @Test
    public void shouldCalcTicketsPrice() {
        // Given
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
        double calculatedPrice = bookingService.getTicketsPrice(godfatherMovie, firstGodfatherShow,
                userService.getById(1L), seats);

        // Then
        assertEquals("60.0", String.valueOf(calculatedPrice));
    }
}
