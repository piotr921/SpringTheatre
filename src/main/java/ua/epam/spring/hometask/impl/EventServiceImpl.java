package ua.epam.spring.hometask.impl;

import ua.epam.spring.hometask.data.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService{

    private EventDAO events;

    public EventServiceImpl(EventDAO events) {
        this.events = events;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return events.getAll().stream()
                .filter(event -> event.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return events.getAll().stream()
                .filter(event ->
                        !event.getAirDates().subSet(from.atStartOfDay(), true, to.atTime(23,59), true).isEmpty())
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return events.getAll().stream()
                .filter(event ->
                        !event.getAirDates().subSet(LocalDateTime.now(), true, to, true).isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public Event save(@Nonnull Event object) {
        events.save(object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        events.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return events.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events.getAll();
    }
}
