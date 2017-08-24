package ua.epam.spring.hometask.repostitory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AuditoriumRepositoryImpl implements AuditoriumRepository {

    private Set<Auditorium> auditoriums = new HashSet<>();

    public AuditoriumRepositoryImpl(Set<String> propertiesFiles) {
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
