package ru.makedonskaya.smartnotes.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.makedonskaya.smartnotes.entity.User;
import ru.makedonskaya.smartnotes.model.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	
	User findByEmail(String email);

    User save(UserRegistrationDto registration);

}
