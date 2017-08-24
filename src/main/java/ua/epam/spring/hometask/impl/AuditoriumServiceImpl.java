package ua.epam.spring.hometask.impl;

import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private Set<Auditorium> auditoriums = new HashSet<>();

    public AuditoriumServiceImpl(Set<String> propertiesFiles) {
        auditoriums = propertiesFiles.stream().map(fileName -> {
            try {
                InputStream inputStream = new FileInputStream(fileName);
                Properties properties = new Properties();
                properties.load(inputStream);

                Auditorium auditorium = new Auditorium();
                auditorium.setName(properties.getProperty("name"));
                auditorium.setNumberOfSeats(Long.valueOf(properties.getProperty("numberOfSeats")));

                Set<Long> vipSeats = Arrays.stream(properties.getProperty("vipSeats").split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toSet());
                auditorium.setVipSeats(vipSeats);

                return auditorium;
            } catch (IOException e) {
                throw new IllegalArgumentException("Property not found: " + e.getMessage() + "; " + e.getCause());
            }
        }).collect(Collectors.toSet());
    }

/*try {

        input = new FileInputStream("config.properties");

        // load a properties file
        prop.load(input);

        // get the property value and print it out
        System.out.println(prop.getProperty("database"));
        System.out.println(prop.getProperty("dbuser"));
        System.out.println(prop.getProperty("dbpassword"));

    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriums;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums.stream()
                .filter(auditorium -> auditorium.getName().equals(name))
                .findFirst().orElse(null);
    }
}
