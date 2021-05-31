package ru.makedonskaya.smartnotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.makedonskaya.smartnotes.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long>{

}
