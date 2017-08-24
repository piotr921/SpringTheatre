package ua.epam.spring.hometask.domain.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AuditoriumServiceImplTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        AuditoriumService auditoriumService() {
            Set<String> propertyFiles = new HashSet<>();
            propertyFiles.add("src/main/resources/big.properties");
            propertyFiles.add("src/main/resources/medium.properties");
            propertyFiles.add("src/main/resources/small.properties");
            propertyFiles.add("src/main/resources/exclusive.properties");

            AuditoriumService auditoriumService = new AuditoriumServiceImpl(propertyFiles);
            return auditoriumService;
        }
    }

    @Autowired
    private AuditoriumService service;

    @Test
    public void shouldGetAuditoriumByName() {
        // When
        Auditorium getAuditorium = service.getByName("Big");

        // Then
        assertEquals(100, getAuditorium.getNumberOfSeats());
        assertTrue(getAuditorium.getVipSeats().containsAll(Arrays.asList(82L, 83L, 84L, 85L, 86L, 87L)));
    }

    @Test
    public void shouldGetAllAuditoriums() {
        // When
        Set<Auditorium> getCollection = service.getAll();

        // Then
        assertEquals(4, getCollection.size());
    }
}
