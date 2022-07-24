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

        Person prevPerson = personDAO.getByName(newPerson.getName());

        if (prevPerson != null && prevPerson.getId() != newPerson.getId()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
