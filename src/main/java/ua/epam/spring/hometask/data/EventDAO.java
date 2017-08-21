package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EventDAO implements AbstractDomainObjectService<Event> {

    Set<Event> events;

    public EventDAO(Set<Event> events) {
        this.events = events;
    }

    @Override
    public Event save(@Nonnull Event object) {
        events.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        if (events.contains(object)) {
            events.remove(object);
        }
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst().orElse(new Event());
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events;
    }
}
