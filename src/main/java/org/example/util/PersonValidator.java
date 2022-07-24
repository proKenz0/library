package org.example.util;

import org.example.dao.PersonDao;
import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDAO;

    @Autowired
    public PersonValidator(PersonDao personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person newPerson = (Person) o;

        Optional<Person> personOptional = personDAO.getByName(newPerson.getName());

        if (personOptional.isPresent() && personOptional.get().getName().equals(newPerson.getName())) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
