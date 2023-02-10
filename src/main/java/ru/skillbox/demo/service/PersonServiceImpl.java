package ru.skillbox.demo.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.entity.Person;
import ru.skillbox.demo.repository.PersonRepository;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class PersonServiceImpl implements PersonService {
    final PersonRepository personRepository;

    public List<Person> findPerson() {
        return personRepository.findAll();
    }

    public Person findPerson(long id) {
        return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String createPerson(Person person) {
        Person savedPerson = personRepository.save(person);
        return String.format("Пользователь %s добавлен в базу с id = %s ", savedPerson.getFirstName(), savedPerson.getId());
    }

    public String updatePerson(Person person) {
        checkUser(person.getId());
        updateSubscribe(person);
        Person savedPerson = personRepository.save(person);
        return String.format("Пользователь: id = %s, Имя %s, успешно обновлен", savedPerson.getId(), savedPerson.getFirstName());
    }

    public String deletePerson(long id) {
        checkUser(id);
        personRepository.deleteById(id);
        return String.format("Пользователь: id = %s успешно удален", id);
    }

    @Transactional
    @Override
    public String subscription(Long ownerPersonId, Long subscriptionPersonId) {
        try {
            personRepository.createSubscription(ownerPersonId, subscriptionPersonId);
            return HttpStatus.OK.toString();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @Override
    public String unsubscribe(Long ownerPersonId, Long unsubscribePersonId) {
        try {
            personRepository.unSubscription(ownerPersonId, unsubscribePersonId);
            return HttpStatus.OK.toString();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateSubscribe(Person person) {
        Person finedPerson = personRepository.findById(person.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (person.getPersons() == null) {
            person.setPersons(finedPerson.getPersons());
        }
    }

    private void checkUser(long id) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
