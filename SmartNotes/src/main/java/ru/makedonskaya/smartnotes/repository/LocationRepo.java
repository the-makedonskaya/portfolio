package ru.makedonskaya.smartnotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.makedonskaya.smartnotes.entity.Location;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long>{

}
