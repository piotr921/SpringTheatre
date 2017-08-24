package ua.epam.spring.hometask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.repostitory.EventRepository;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return repository.getByName(name);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return repository.getForDateRange(from, to);
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return repository.getNextEvents(to);
    }

    @Override
    public Event save(@Nonnull Event object) {
        repository.save(object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        repository.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return repository.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return repository.getAll();
    }
}
