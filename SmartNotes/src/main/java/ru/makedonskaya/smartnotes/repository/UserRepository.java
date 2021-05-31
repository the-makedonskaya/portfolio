package ru.makedonskaya.smartnotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.makedonskaya.smartnotes.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
}
