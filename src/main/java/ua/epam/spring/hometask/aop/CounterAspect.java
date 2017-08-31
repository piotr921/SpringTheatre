package ua.epam.spring.hometask.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Aspect
public class CounterAspect {

    enum Operations {
        GET_EVENT_BY_NAME,
        QUERY_PRICE,
        BOOK_TICKETS
    }

    private Map<Operations, Map<String, Integer>> methodCallCounter;

    public CounterAspect() {
        methodCallCounter = new HashMap<>();
        methodCallCounter.put(Operations.GET_EVENT_BY_NAME, new HashMap<>());
        methodCallCounter.put(Operations.QUERY_PRICE, new HashMap<>());
        methodCallCounter.put(Operations.BOOK_TICKETS, new HashMap<>());
    }

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..)) && args(name)")
    public void countHowManyTimesEventAccessedByName(String name) {
        incrementCounter(Operations.GET_EVENT_BY_NAME, name);
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..)) && args(event, dateTime, user, seats)")
    public void countHowManyTimesEventPriceQueried(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        incrementCounter(Operations.QUERY_PRICE, event.getName());
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..)) && args(tickets)")
    public void countHowManyTimesTicketsForEventWereBooked(Set<Ticket> tickets) {
        tickets.forEach(ticket -> incrementCounter(Operations.BOOK_TICKETS, ticket.getEvent().getName()));
    }

    private void incrementCounter(Operations operation, String methodName) {
        Map<String, Integer> operationCounter = methodCallCounter.get(operation);
        operationCounter.merge(methodName, 1, (key, value) -> value + 1);
        methodCallCounter.put(operation, operationCounter);
    }

    public int getNoOfGetEventByNameOperations(String eventName) {
        return methodCallCounter.get(Operations.GET_EVENT_BY_NAME).get(eventName);
    }

    public int getNoOfQueryPriceOperations(String eventName) {
        return methodCallCounter.get(Operations.QUERY_PRICE).get(eventName);
    }

    public int getNoOfBookTicketsOperations(String eventName) {
        return methodCallCounter.get(Operations.BOOK_TICKETS).get(eventName);
    }
}
