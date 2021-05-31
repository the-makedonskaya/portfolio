package ru.makedonskaya.smartnotes.user.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.makedonskaya.smartnotes.user.ITenantService;
import ru.makedonskaya.smartnotes.user.MyUserPrincipal;

@Service
public class TenantService implements ITenantService {

	private static final String DEFAULT_ID = "default_id";

	@Override
	public String getCurrentTenantIdString() throws UsernameNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new UsernameNotFoundException("not auth");
		}
		if (auth.getPrincipal() instanceof MyUserPrincipal) {
			return ((MyUserPrincipal) auth.getPrincipal()).getTenantId();
		}
		return DEFAULT_ID;
	}

}
