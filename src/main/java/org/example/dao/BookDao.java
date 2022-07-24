package org.example.dao;

import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        String query = "select * from book";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getById(int id) {
        String query = "select * from book where id = ?";
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findFirst().orElse(null);
    }

    public void create(Book book) {
        String query = "insert into book(title, author, year) values (?, ?, ?)";
        jdbcTemplate.update(query, book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        String query = "update book set title = ?, author = ?, year = ? where id = ?";
        jdbcTemplate.update(query, updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        String query = "delete from book where id = ?";
        jdbcTemplate.update(query, id);
    }
}
