package ru.skillbox.demo.controllerRest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.demo.entity.Person;
import ru.skillbox.demo.service.PersonServiceImpl;

import java.util.List;


@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(value = "api/person")
public class PersonController {
    final PersonServiceImpl personService;

    @GetMapping
    public List<Person> getPerson() {
        return personService.findPerson();
    }

    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.findPerson(id);
    }

    @PostMapping
    public String createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping
    public String updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping(path = "/{id}")
    public String deletePerson(@PathVariable long id) {
        return personService.deletePerson(id);
    }

    @PostMapping("/{ownerPersonId}/{subscriptionPersonId}")
    public String subscription(@PathVariable Long ownerPersonId, @PathVariable Long subscriptionPersonId) {
        return personService.subscription(ownerPersonId, subscriptionPersonId);
    }

    @DeleteMapping("/{ownerPersonId}/{unsubscribePersonId}")
    public String unsubscribe(@PathVariable Long ownerPersonId, @PathVariable Long unsubscribePersonId) {
        return personService.unsubscribe(ownerPersonId, unsubscribePersonId);
    }
}
