package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EventDAO implements AbstractDomainObjectService<Event> {

    private Set<Event> events;
    private List<LocalDateTime> airDates = Arrays.asList(
            LocalDateTime.of(2017, 9, 1, 12, 0),
            LocalDateTime.of(2017, 9, 8, 12, 0),
            LocalDateTime.of(2017, 9, 10, 15, 30));

    public EventDAO(Set<Event> events) {
        this.events = events;
        for (int i = 0; i < airDates.size(); i++) {
            int finalI = i;
            events.forEach(event -> event.addAirDateTime(airDates.get(finalI)));
        }
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
