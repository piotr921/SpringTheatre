package ua.epam.spring.hometask.domain.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.impl.EventServiceImpl;
import ua.epam.spring.hometask.repostitory.EventRepository;
import ua.epam.spring.hometask.repostitory.EventRepositoryImpl;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class EventServiceImplTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public EventService eventService() {
            EventService eventService = new EventServiceImpl();
            return eventService;
        }

        @Bean
        public EventRepository eventRepository() {
            EventRepository eventRepository = new EventRepositoryImpl();
            return eventRepository;
        }
    }

    @Autowired
    private EventService service;

    @Test
    public void shouldGetEventByName() {
        // When
        Event getEvent = service.getByName("The Goodfather");

        // Then
        assertEquals("20.0", String.valueOf(getEvent.getBasePrice()));
        assertEquals(EventRating.HIGH, getEvent.getRating());
    }

    @Test
    public void shouldGetEventsByDateRange() {
        // When
        Set<Event> events = service.getForDateRange(LocalDate.of(2017, 9, 8), LocalDate.of(2017, 9, 9));

        // Then
        assertEquals(1, events.size());
    }

    @Test
    public void shouldGetNextEvents() {
        // When
        Set<Event> events = service.getNextEvents(LocalDateTime.of(2017, 9, 1, 22, 0));

        // Then
        assertEquals(2, events.size());
    }
}
