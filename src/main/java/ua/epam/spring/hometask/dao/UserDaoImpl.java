package ua.epam.spring.hometask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private final String SAVE_USER_QUERY = "INSERT INTO users (id, firstname, lastname, email) VALUES (? ,? ,? ,?)";
    private final String REMOVE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private final String GET_ALL_USERS_QUERY = "SELECT * FROM users";

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(@Nonnull User object) {
        jdbcTemplate.update(SAVE_USER_QUERY,
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail());
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        jdbcTemplate.update(REMOVE_USER_QUERY, object.getId());
    }

    @Override
    public User getById(@Nonnull Long id) {
        User user = (User) jdbcTemplate.queryForObject(
                GET_USER_BY_ID_QUERY,
                new Object[]{id},
                new BeanPropertyRowMapper<>(User.class));
        return user;
    }

//    Customer customer = (Customer)getJdbcTemplate().queryForObject(
//            sql, new Object[] { custId },
//            new BeanPropertyRowMapper(Customer.class));
//
//	return customer;

    @Nonnull
    @Override
    public Collection<User> getAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_ALL_USERS_QUERY);
        List<User> users = new ArrayList<>();
        rows.forEach(row -> {
            User rowUser = new User();
            BigDecimal idDecimal = (BigDecimal) row.get("ID");
            rowUser.setId(idDecimal.longValue());
            rowUser.setFirstName((String) row.get("FIRSTNAME"));
            rowUser.setLastName((String) row.get("LASTNAME"));
            rowUser.setEmail((String) row.get("EMAIL"));
            users.add(rowUser);
        });
        return users;
    }
}
