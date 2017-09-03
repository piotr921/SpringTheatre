package ua.epam.spring.hometask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CounterAspectDaoImpl implements CounterAspectDao {

    private JdbcTemplate jdbcTemplate;
    private final String ADD_OCCURRENCE = "INSERT INTO counteraspects (eventtype, eventname) VALUES (? ,?)";
    private final String COUNT_OCCURRENCES = "SELECT COUNT (*) FROM counteraspects WHERE eventtype = ? AND eventname = ?";

    @Autowired
    public CounterAspectDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addOperationOccurrence(String operationType, String eventName) {
        jdbcTemplate.update(ADD_OCCURRENCE, operationType, eventName);
    }

    @Override
    public int getOccurancesOfOperationForEvent(String operationType, String eventName) {
        return jdbcTemplate.queryForObject(COUNT_OCCURRENCES, new Object[]{operationType, eventName}, Integer.class);
    }
}
