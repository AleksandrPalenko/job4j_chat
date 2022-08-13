package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;


public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("select distinct pr from Person pr join fetch pr.role")
    List<Person> findAll();

    @Query("select distinct p from Person p join fetch p.role where p.id = :pId")
    Optional<Person> findById(@Param("pId") int id);

    @Query("select distinct p from Person p join fetch p.role where p.login = :pLogin")
    Optional<Person> findPersonByLogin(@Param("pLogin") String personLogin);
}
