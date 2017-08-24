package ua.epam.spring.hometask.repostitory;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

public interface AuditoriumRepository {

    Set<Auditorium> getAll();

    Auditorium getByName(String name);
}
