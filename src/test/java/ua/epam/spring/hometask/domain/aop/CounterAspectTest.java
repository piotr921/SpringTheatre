package ua.epam.spring.hometask.domain.aop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.aop.CounterAspect;
import ua.epam.spring.hometask.impl.EventServiceImpl;
import ua.epam.spring.hometask.repostitory.EventRepository;
import ua.epam.spring.hometask.repostitory.EventRepositoryImpl;
import ua.epam.spring.hometask.service.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CounterAspectTest {

    @Configuration
    @EnableAspectJAutoProxy
    static class ContextConfiguration {

        @Bean
        EventService eventService() {

            EventService eventService = new EventServiceImpl();
            return eventService;
        }

        @Bean
        public EventRepository eventRepository() {
            EventRepository eventRepository = new EventRepositoryImpl();
            return eventRepository;
        }

        @Bean
        CounterAspect counterAspect() {
            CounterAspect counterAspect = new CounterAspect();
            return counterAspect;
        }
    }

    @Autowired
    private EventService service;

    @Autowired
    private CounterAspect aspect;

    @Test
    public void shouldCountGettingEventByName() {
        service.getByName("The Goodfather");
        service.getByName("The Goodfather");
        Assert.assertEquals(2, aspect.getNoOfGetEventByNameOperations());
    }
}
