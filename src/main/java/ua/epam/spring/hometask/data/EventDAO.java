package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EventDAO implements EventService {

    Map<Integer, Event> events;

    public EventDAO(Map<Integer, Event> events) {
        this.events = events;
    }

    @Override
    public Event save(@Nonnull Event object) {
        Integer key = events.size();
        events.put(key, object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        if (events.containsValue(object)) {
            events.remove(object);
        }
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return events.values().stream()
                .filter(event -> event.getId().equals(id))
                .findFirst().orElse(new Event());
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events.values();
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return events.values().stream()
                .filter(event -> event.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return events.values().stream()
                .filter(event ->
                        !event.getAirDates().subSet(from.atStartOfDay(), true, to.atTime(23,59), true).isEmpty())
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return events.values().stream()
                .filter(event ->
                        !event.getAirDates().subSet(LocalDateTime.now(), true, to, true).isEmpty())
                .collect(Collectors.toSet());
    }
}
