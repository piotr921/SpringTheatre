package ua.epam.spring.hometask.domain.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.util.Set;

public class AuditoriumServiceImplTest {

    private ConfigurableApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("auditoriums.xml");
    }

    @Test
    public void shouldGetAuditoriumByName() {
        // Given
        AuditoriumService service = context.getBean("auditoriumService", AuditoriumService.class);

        // When
        Auditorium getAuditorium = service.getByName("Small");

        // Then
        Assert.assertEquals(50, getAuditorium.getNumberOfSeats());
    }

    @Test
    public void shouldGetAllAuditoriums() {
        // Given
        AuditoriumService service = context.getBean("auditoriumService", AuditoriumService.class);

        // When
        Set<Auditorium> getCollection = service.getAll();

        // Then
        Assert.assertEquals(4, getCollection.size());
    }
}
