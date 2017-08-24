package ua.epam.spring.hometask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.repostitory.AuditoriumRepository;
import ua.epam.spring.hometask.repostitory.EventRepository;
import ua.epam.spring.hometask.repostitory.TicketRepository;
import ua.epam.spring.hometask.service.BookingService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Autowired
    private EventRepository eventRepository;


    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double basePrice = eventRepository.getByName(event.getName()).getBasePrice();

        NavigableMap<LocalDateTime, Auditorium> auditoriums = eventRepository.getByName(event.getName()).getAuditoriums();
        Auditorium eventAuditorium = auditoriums.get(dateTime);
        long noOfVipSeats = seats.stream()
                .filter(seat -> eventAuditorium.getVipSeats().contains(seat))
                .count();

        return (seats.size() - noOfVipSeats) * basePrice + noOfVipSeats * basePrice * 2;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        ticketRepository.addTickets(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketRepository.getAll().stream()
                .filter(ticket -> ticket.getEvent().equals(event))
                .filter(ticket -> ticket.getEvent().getAirDates().contains(dateTime))
                .collect(Collectors.toSet());
    }
}
