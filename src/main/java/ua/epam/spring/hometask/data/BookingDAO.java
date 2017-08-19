package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingDAO implements BookingService {

    private EventDAO events;
    private UserDAO users;

    public BookingDAO(EventDAO events, UserDAO users) {
        this.events = events;
        this.users = users;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime,
                                  @Nullable User user, @Nonnull Set<Long> seats) {
        return 0;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {

    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        Set<Ticket> ticketsForEventAtTime = new HashSet<>();
        users.getAll().forEach(user -> ticketsForEventAtTime.addAll(user.getTickets()));

        return ticketsForEventAtTime.stream()
                .filter(ticket -> isForEventAtTime(ticket, event, dateTime))
                .collect(Collectors.toSet());
    }

    private boolean isForEventAtTime(Ticket ticket, Event event, LocalDateTime dateTime) {
        return ticket.getEvent().equals(event) && ticket.getEvent().getAirDates().contains(dateTime);
    }
}
