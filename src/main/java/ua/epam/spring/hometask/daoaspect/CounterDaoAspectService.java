package ua.epam.spring.hometask.daoaspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.CounterAspectDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@Aspect
public class CounterDaoAspectService {

    @Autowired
    CounterAspectDao aspectDao;

    enum Operations {
        GET_EVENT_BY_NAME,
        QUERY_PRICE,
        BOOK_TICKETS
    }

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..)) && args(name)")
    public void countHowManyTimesEventAccessedByName(String name) {
        aspectDao.addOperationOccurrence(Operations.GET_EVENT_BY_NAME.name(), name);
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..)) && args(event, dateTime, user, seats)")
    public void countHowManyTimesEventPriceQueried(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        aspectDao.addOperationOccurrence(Operations.QUERY_PRICE.name(), event.getName());
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..)) && args(tickets)")
    public void countHowManyTimesTicketsForEventWereBooked(Set<Ticket> tickets) {
        tickets.forEach(ticket -> aspectDao.addOperationOccurrence(Operations.BOOK_TICKETS.name(), ticket.getEvent().getName()));
    }

    public int getNoOfGetEventByNameOperations(String eventName) {
        return aspectDao.getOccurancesOfOperationForEvent(Operations.GET_EVENT_BY_NAME.name(), eventName);
    }

    public int getNoOfQueryPriceOperations(String eventName) {
        return aspectDao.getOccurancesOfOperationForEvent(Operations.QUERY_PRICE.name(), eventName);
    }

    public int getNoOfBookTicketsOperations(String eventName) {
        return aspectDao.getOccurancesOfOperationForEvent(Operations.BOOK_TICKETS.name(), eventName);
    }
}
