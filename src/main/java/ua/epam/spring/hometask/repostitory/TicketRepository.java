package ua.epam.spring.hometask.repostitory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Collection;
import java.util.Set;

@Repository
public interface TicketRepository {

    void addTicket(Ticket object);

    void addTickets(Collection<Ticket> tickets);

    Set<Ticket> getAll();
}
