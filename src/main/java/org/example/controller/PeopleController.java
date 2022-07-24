package org.example.controller;

import org.example.dao.PersonDao;
import org.example.model.Person;
import org.example.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAll(Model model) {

        List<Person> people = personDao.getAll();

        System.out.println(people);

        model.addAttribute("people", people);
        return "people/all";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable int id, Model model) {
        model.addAttribute("person", personDao.getById(id));

        return "people/profile";
    }

    @GetMapping("/new")
    public String createNewPersonPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @GetMapping("/{id}/edit")
    public String updatePersonPage(@PathVariable("id") int id,
                                   Model model) {

        model.addAttribute("person", personDao.getById(id));

        return "people/edit_form";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDao.create(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit_form";
        }

        personDao.update(id, person);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personDao.delete(id);

        return "redirect:/people";
    }

}
