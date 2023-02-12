package ru.skillbox.demo.service;

import ru.skillbox.demo.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> findPerson();

    Person findPerson(long id);

    String createPerson(Person person);

    String updatePerson(Person person);

    String deletePerson(long id);

    String subscription(Long ownerPersonId, Long subscriptionPersonId);

    String unsubscribe(Long ownerPersonId, Long unsubscribePersonId);
}
