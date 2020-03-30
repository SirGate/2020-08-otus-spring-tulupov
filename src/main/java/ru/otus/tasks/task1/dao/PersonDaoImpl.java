package ru.otus.tasks.task1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.task1.domain.Person;
import ru.otus.tasks.task1.service.IOService;

@Repository
public class PersonDaoImpl implements PersonDao {

    final private IOService ioService;

    @Autowired
    public PersonDaoImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Person getNewPerson() {
        ioService.printMessage("question.surname");
        final String familyName = ioService.askStr();
        ioService.printlnString("");
        ioService.printMessage("question.name");
        final String name = ioService.askStr();
        Person person = new Person(familyName, name);
        ioService.printlnString("");
        return person;
    }
}