package ru.makedonskaya.smartnotes.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ru.makedonskaya.smartnotes.entity.Role;
import ru.makedonskaya.smartnotes.entity.User;
import ru.makedonskaya.smartnotes.model.dto.UserRegistrationDto;
import ru.makedonskaya.smartnotes.repository.UserRepo;
import ru.makedonskaya.smartnotes.service.IUserService;
import ru.makedonskaya.smartnotes.user.MyUserPrincipal;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        if (StringUtils.isEmpty(user.getTenantId())) {
        	user.setTenantId(UUID.randomUUID().toString());
        	userRepository.save(user);
        }
        return new MyUserPrincipal(user, mapRolesToAuthorities(user.getRoles()));
	}

	public User findByEmail(String email) {
		 return userRepository.findByEmail(email);
	}

	@Override
	public User save(UserRegistrationDto registration) {
		 User user = new User();
	     user.setFirstName(registration.getFirstName());
	     user.setLastName(registration.getLastName());
	     user.setEmail(registration.getEmail());
	     user.setPassword(passwordEncoder.encode(registration.getPassword()));
	     user.setTenantId(UUID.randomUUID().toString());
	     user.setRoles(Arrays.asList(new Role("ROLE_USER")));
	     
	     return userRepository.save(user);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
