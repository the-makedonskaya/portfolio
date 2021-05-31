package ru.makedonskaya.smartnotes.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.makedonskaya.smartnotes.entity.User;

public class MyUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private User user;

	private Set<? extends GrantedAuthority> authorities;

    public MyUserPrincipal(User user) {
        this.user = user;
    }

	public MyUserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
		this(user);
		this.authorities = new HashSet<GrantedAuthority>(authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getTenantId() {
		return user.getTenantId();
	}
}
