package org.example.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {

    private int id;

    @NotEmpty(message = "title should not be empty")
    @Size(min = 2, max = 50, message = "title length should be between 2 and 50")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 50, message = "Author length should be between 2 and 50")
    private String author;

    @Max(value = 2022, message = "Enter real year")
    @Min(value = 0, message = "Enter real year")
    private int year;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
