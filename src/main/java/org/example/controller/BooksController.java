package org.example.controller;

import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.model.Book;
import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", bookDao.getAll());

        return "books/all";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable int id, @ModelAttribute("newOwner") Person person, Model model) {
        model.addAttribute("book", bookDao.getById(id));

        Integer personId = bookDao.getPersonIdByBook(id);

        if (personId == null) {
            model.addAttribute("people", personDao.getAll());
        } else {
            model.addAttribute("personOwner", personDao.getById(personId));
        }

        return "books/profile";
    }

    @GetMapping("/new")
    public String getCreatePage(@ModelAttribute("book")Book book) {
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.getById(id));

        return "books/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDao.create(book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        System.out.println("book = " + book.getId());

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDao.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        bookDao.release(id);

        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/give")
    public String give(@PathVariable int id, @ModelAttribute("newOwner") Person newOwner) {
        bookDao.give(id, newOwner.getId());

        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookDao.delete(id);

        return "redirect:/books";
    }

}
