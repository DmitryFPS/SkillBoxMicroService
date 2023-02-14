package ru.skillbox.demo.controllerRest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Получение пользователей", description = "Позволяет получить пользователей")
    @Tag(name = "getPerson", description = "Получение пользователей")
    @GetMapping
    public List<Person> getPerson() {
        return personService.findPerson();
    }

    @Operation(summary = "Получение пользователя", description = "Позволяет получить пользователя")
    @Tag(name = "getPerson", description = "Получение пользователя")
    @GetMapping(path = "/{id}")
    public Person getPerson(@PathVariable long id) {
        return personService.findPerson(id);
    }

    @Operation(summary = "Добавление пользователя", description = "Позволяет сохранить нового пользователя")
    @Tag(name = "createPerson", description = "Добавление пользователя")
    @PostMapping
    public String createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @Operation(summary = "Изменение пользователя", description = "Позволяет изменить данные пользователя")
    @Tag(name = "updatePerson", description = "Изменение пользователя")
    @PutMapping
    public String updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @Operation(summary = "Удаление пользователя", description = "Позволяет удалить пользователя")
    @Tag(name = "deletePerson", description = "Удаление пользователя")
    @DeleteMapping(path = "/{id}")
    public String deletePerson(@PathVariable long id) {
        return personService.deletePerson(id);
    }

    @Operation(summary = "Подписаться на пользователя", description = "Позволяет подписаться на другого пользователя")
    @Tag(name = "subscription", description = "Подписаться на пользователя")
    @PostMapping("/{ownerPersonId}/{subscriptionPersonId}")
    public String subscription(@PathVariable Long ownerPersonId, @PathVariable Long subscriptionPersonId) {
        return personService.subscription(ownerPersonId, subscriptionPersonId);
    }

    @Operation(summary = "Отписаться от пользователя", description = "Позволяет отписаться от пользователя")
    @Tag(name = "unsubscribe", description = "Отписаться от пользователя")
    @DeleteMapping("/{ownerPersonId}/{unsubscribePersonId}")
    public String unsubscribe(@PathVariable Long ownerPersonId, @PathVariable Long unsubscribePersonId) {
        return personService.unsubscribe(ownerPersonId, unsubscribePersonId);
    }
}
