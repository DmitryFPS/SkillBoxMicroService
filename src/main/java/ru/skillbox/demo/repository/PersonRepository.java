package ru.skillbox.demo.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.skillbox.demo.entity.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @NonNull
    List<Person> findAll();

    @Modifying
    @Query(value = "insert into person_subscription (owner_person_id, subscription_person_id) values ( :ownerPersonId, :subscriptionPersonId)", nativeQuery = true)
    void createSubscription(@Param("ownerPersonId") Long ownerPersonId, @Param("subscriptionPersonId") Long subscriptionPersonId);

    @Modifying
    @Query(value = "delete from person_subscription where owner_person_id = :ownerPersonId and subscription_person_id = :subscriptionPersonId", nativeQuery = true)
    void unSubscription(@Param("ownerPersonId") Long ownerPersonId, @Param("subscriptionPersonId") Long subscriptionPersonId);
}
