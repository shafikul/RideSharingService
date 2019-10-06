package com.freenow.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.freenow.domainobject.DriverDO;

public class DriverPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private DriverDO driver;

	public DriverPrincipal(DriverDO driver) {
		super();
		this.driver = driver;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.driver.getUsername().equalsIgnoreCase("ADMIN")) {
			return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
		}
		return Collections.singleton(new SimpleGrantedAuthority("DRIVER"));
	}

	public Long getId() {
		return this.driver.getId();
	}

	@Override
	public String getPassword() {
		return driver.getPassword();
	}

	@Override
	public String getUsername() {
		return driver.getUsername();
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
}
