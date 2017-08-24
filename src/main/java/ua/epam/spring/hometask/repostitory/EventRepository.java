package ua.epam.spring.hometask.repostitory;

import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public interface EventRepository {

    Event getByName(String name);

    Set<Event> getForDateRange(LocalDate from, LocalDate to);

    Set<Event> getNextEvents(LocalDateTime to);

    Event save(Event object);

    void remove(Event object);

    Event getById(Long id);

    Collection<Event > getAll();
}
