package ru.makedonskaya.smartnotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.makedonskaya.smartnotes.entity.Label;

@Repository
public interface LabelRepo extends JpaRepository<Label, Long> {
	
}
