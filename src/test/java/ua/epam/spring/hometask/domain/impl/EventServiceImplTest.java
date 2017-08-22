package ua.epam.spring.hometask.domain.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.impl.EventServiceImpl;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class EventServiceImplTest {

    private ConfigurableApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("events.xml");
    }

    @Test
    public void shouldGetEventByName() {
        // Given
        EventService service = context.getBean("eventService", EventServiceImpl.class);

        // When
        Event getEvent = service.getByName("The Good, the Bad and the Ugly");

        // Then
        assertEquals("10.0", String.valueOf(getEvent.getBasePrice()));
        assertEquals(EventRating.HIGH, getEvent.getRating());
    }

    @Test
    public void shouldGetEventsByDateRange() {
        // Given
        EventService service = context.getBean("eventService", EventServiceImpl.class);

        // When
        Set<Event> events = service.getForDateRange(LocalDate.of(2017, 9, 1), LocalDate.of(2017, 9, 2));

        // Then
        assertEquals(3, events.size());
    }

    @Test
    public void shouldGetNextEvents() {
        // Given
        EventService service = context.getBean("eventService", EventServiceImpl.class);

        // When
        Set<Event> events = service.getNextEvents(LocalDateTime.of(2017, 9, 1, 12, 0));

        // Then
        assertEquals(3, events.size());
    }
}
