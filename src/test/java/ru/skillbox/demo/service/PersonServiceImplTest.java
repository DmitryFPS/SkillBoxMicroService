package ru.skillbox.demo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.entity.Person;
import ru.skillbox.demo.repository.PersonRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RequiredArgsConstructor
class PersonServiceImplTest {

    private final PersonRepository repository = mock(PersonRepository.class);
    private final PersonServiceImpl personService = new PersonServiceImpl(repository);

    @Test
    void findPersonTest() {
        Person person = new Person(1L, "Dmitriy", "Orlov", false, Collections.emptyList());
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        Person findPerson = personService.findPerson(Mockito.anyLong());
        Assertions.assertNotNull(findPerson);
        Mockito.verify(repository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void findPersonByErrorTest() {
        when(repository.findById(Mockito.anyLong())).thenThrow(ResponseStatusException.class);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            personService.findPerson(Mockito.anyLong());
        });
        Mockito.verify(repository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void findAllPersonTest() {
        Person person1 = new Person(1L, "Dmitriy1", "Orlov1", false, Collections.emptyList());
        Person person2 = new Person(2L, "Dmitriy2", "Orlov2", false, Collections.emptyList());
        Person person3 = new Person(3L, "Dmitriy3", "Orlov3", false, Collections.emptyList());
        Person person4 = new Person(4L, "Dmitriy4", "Orlov4", false, Collections.emptyList());

        List<Person> people = Arrays.asList(person1, person2, person3, person4);
        when(repository.findAll()).thenReturn(people);

        List<Person> findAllPeople = personService.findPerson();
        Assertions.assertEquals(4, findAllPeople.size());
        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void findAllPersonByEmptyListTest() {
        List<Person> people = Collections.emptyList();
        when(repository.findAll()).thenReturn(people);
        List<Person> findAllPeople = personService.findPerson();
        Assertions.assertEquals(0, findAllPeople.size());
        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void createPersonTest() {
        Person savePerson = new Person("Dmitriy1", "Orlov1", false, Collections.emptyList());
        Person savedPerson = new Person(1L, "Dmitriy1", "Orlov1", false, Collections.emptyList());
        when(repository.save(savePerson)).thenReturn(savedPerson);
        String person = personService.createPerson(savePerson);
        Assertions.assertEquals("Пользователь Dmitriy1 добавлен в базу с id = 1", person);
        Mockito.verify(repository, times(1)).save(savePerson);
    }

    @Test
    void updatePersonTest() {
        Person updatePerson = new Person(1L, "Dmitriy", "Orlov", false, Collections.emptyList());
        Person updatedPerson = new Person(1L, "Dmitriy777", "Orlov777", false, Collections.emptyList());
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(updatePerson));
        when(repository.save(updatePerson)).thenReturn(updatedPerson);
        String person = personService.updatePerson(updatePerson);
        Assertions.assertEquals("Пользователь: id = 1, Имя Dmitriy777, успешно обновлен", person);
        Mockito.verify(repository, times(1)).existsById(Mockito.anyLong());
        Mockito.verify(repository, times(1)).findById(Mockito.anyLong());
        Mockito.verify(repository, times(1)).save(updatePerson);
    }

    @Test
    void deletePersonTest() {
        Person person = new Person(1L, "Dmitriy", "Orlov", false, Collections.emptyList());
        when(repository.existsById(person.getId())).thenReturn(true);
        doNothing().when(repository).delete(person);
        String deletedPerson = personService.deletePerson(1L);
        Assertions.assertEquals("Пользователь: id = 1 успешно удален", deletedPerson);
    }

    @Test
    void subscriptionTest() {
        Person person1 = new Person(1L, "Dmitriy1", "Orlov1", false, Collections.emptyList());
        Person person2 = new Person(2L, "Dmitriy2", "Orlov2", false, Collections.emptyList());
        doNothing().when(repository).createSubscription(person2.getId(), person1.getId());
        String status = personService.subscription(person2.getId(), person1.getId());
        Assertions.assertEquals(status, "200 OK");
        Mockito.verify(repository, times(1)).createSubscription(person2.getId(), person1.getId());
    }

    @Test
    void subscriptionByExceptionTest() {
        doThrow(ResponseStatusException.class)
                .when(repository).createSubscription(777L, 888L);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            personService.subscription(777L, 888L);
        });
    }

    @Test
    void unsubscribeTest() {
        doNothing().when(repository).unSubscription(Mockito.anyLong(), Mockito.anyLong());
        String status = personService.unsubscribe(Mockito.anyLong(), Mockito.anyLong());
        Assertions.assertEquals(status, "200 OK");
        Mockito.verify(repository, times(1)).unSubscription(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void unsubscribeByExceptionTest() {
        doThrow(ResponseStatusException.class)
                .when(repository).unSubscription(777L, 888L);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            personService.unsubscribe(777L, 888L);
        });
    }
}
