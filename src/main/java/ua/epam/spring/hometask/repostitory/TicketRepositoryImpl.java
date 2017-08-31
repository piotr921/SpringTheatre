package ua.epam.spring.hometask.repostitory;

import ua.epam.spring.hometask.domain.Ticket;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TicketRepositoryImpl implements TicketRepository {

    private Set<Ticket> tickets = new HashSet<>();

    @Override
    public void addTicket(Ticket object) {
        tickets.add(object);
    }

    @Override
    public void addTickets(Collection<Ticket> ticketsToAdd) {
        tickets.addAll(ticketsToAdd);
    }

    @Override
    public Set<Ticket> getAll() {
        return tickets;
    }

    @Override
    public void clear() {
        tickets.clear();
    }
}
