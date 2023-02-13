package ru.skillbox.demo.dataInitialization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skillbox.demo.entity.Person;
import ru.skillbox.demo.repository.PersonRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitialization {

    private final PersonRepository repository;

    @PostConstruct
    public void initialization() {
        if (checkPresenceOfTables()) {
            Person person1 = new Person();
            person1.setFirstName("Dima");
            person1.setLastName("Orlov");
            repository.save(person1);

            Person person2 = new Person();
            person2.setFirstName("Pavel");
            person2.setLastName("Kudashev");
            repository.save(person2);

            Person person3 = new Person();
            person3.setFirstName("Olya");
            person3.setLastName("Mokina");

            List<Person> people = Arrays.asList(person1, person2, person3);
            repository.saveAll(people);

        } else {
            System.out.println("Таблиц нет!");
            System.out.println("Таблицы создаются");
        }
    }

    private boolean checkPresenceOfTables() {
        return repository.checkIfTherePersonTable();
    }
}
