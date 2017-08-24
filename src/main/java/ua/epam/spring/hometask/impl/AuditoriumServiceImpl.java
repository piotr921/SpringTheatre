package ua.epam.spring.hometask.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.repostitory.AuditoriumRepository;
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

    @Autowired
    AuditoriumRepository repository;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return repository.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return repository.getByName(name);
    }
}
