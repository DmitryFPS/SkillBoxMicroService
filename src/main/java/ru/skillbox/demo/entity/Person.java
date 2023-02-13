package ru.skillbox.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString()
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "person")
@SQLDelete(sql = "UPDATE person SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String firstName;
    String lastName;
    boolean deleted = Boolean.FALSE;

    public Person(String firstName, String lastName, boolean deleted, List<Person> persons) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.persons = persons;
    }

    public Person(Long id, String firstName, String lastName, boolean deleted, List<Person> persons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = deleted;
        this.persons = persons;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_subscription",
            joinColumns = @JoinColumn(name = "owner_person_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_person_id")
    )
    @ToString.Exclude
    List<Person> persons;
}
