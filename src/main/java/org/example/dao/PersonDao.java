package org.example.dao;

import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        String query = "select * from person";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(int id) {
        String query = "select * from person where id = ?";
        return jdbcTemplate.query(query, new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst().orElse(null);
    }

    public Person getByName(String name) {
        String query = "select * from person where name = ?";
        return jdbcTemplate.query(query, new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void create(Person person) {
        String query = "insert into person (name, year_of_birth) values(?, ?)";
        jdbcTemplate.update(query, person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        String query = "update person set name = ?, year_of_birth = ? where id = ?;";
        jdbcTemplate.update(query, person.getName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        String query = "delete from person where id = ?";
        jdbcTemplate.update(query, id);
    }
}
