package ua.epam.spring.hometask.data;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuditoriumDAO implements AuditoriumService {

    private Map<Integer, Auditorium> auditoriums;

    public AuditoriumDAO(Map<Integer, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        Set<Auditorium> result = new HashSet<>();
        auditoriums.forEach((id, auditorium) -> {
            if (!result.contains(auditorium)) {
                result.add(auditorium);
            }
        });
        return result;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums.values().stream()
                .filter(auditorium -> auditorium.getName().equals(name))
                .findFirst().orElse(null);
    }
}
