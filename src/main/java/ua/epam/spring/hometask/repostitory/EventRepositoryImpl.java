package ua.epam.spring.hometask.repostitory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.domain.EventRating.HIGH;
import static ua.epam.spring.hometask.domain.EventRating.LOW;
import static ua.epam.spring.hometask.domain.EventRating.MID;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private Set<Event> events;

    {
        NavigableSet<LocalDateTime> godfatherAirDates = new TreeSet<>();
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 1, 15, 30));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 3, 18, 30));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 10, 15, 0));

        NavigableSet<LocalDateTime> godfather3AirDates = new TreeSet<>();
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 2, 20, 15));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 8, 18, 30));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 9, 17, 0));

        NavigableSet<LocalDateTime> scaryMovieAirDates = new TreeSet<>();
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 1, 20, 15));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 3, 16, 30));
        godfatherAirDates.add(LocalDateTime.of(2017, 9, 5, 17, 45));

        Event event1 = new Event();
        event1.setName("The Goodfather");
        event1.setBasePrice(20);
        event1.setRating(HIGH);
        event1.setAirDates(godfatherAirDates);

        Event event2 = new Event();
        event1.setName("The Goodfather 3");
        event1.setBasePrice(17.50);
        event1.setRating(MID);
        event1.setAirDates(godfather3AirDates);

        Event event3 = new Event();
        event1.setName("Scary Movie");
        event1.setBasePrice(12);
        event1.setRating(LOW);
        event1.setAirDates(scaryMovieAirDates);

        events = new HashSet<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
    }

    @Override
    public Event getByName(@Nonnull String name) {
        return events.stream()
                .filter(event -> event.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return events.stream()
                .filter(event ->
                        !event.getAirDates().subSet(from.atStartOfDay(), true, to.atTime(23, 59), true).isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return events.stream()
                .filter(event ->
                        !event.getAirDates().subSet(LocalDateTime.now(), true, to, true).isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public Event save(Event object) {
        events.add(object);
        return object;
    }

    @Override
    public void remove(Event object) {
        events.remove(object);
    }

    @Override
    public Event getById(Long id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst().orElse(new Event());
    }

    @Override
    public Collection<Event> getAll() {
        return events;
    }
}
