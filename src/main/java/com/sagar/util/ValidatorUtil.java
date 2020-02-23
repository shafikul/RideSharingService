package com.sagar.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sagar.config.DriverPrincipal;
import com.sagar.exception.UnauthorizeAccessException;

public class ValidatorUtil {

	public static boolean authorizeAdminOrDriverUser(long driverId) throws UnauthorizeAccessException {
		DriverPrincipal driver = (DriverPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!checkIfAdminUser(driver) && driver.getId() != driverId) {
			throw new UnauthorizeAccessException("Invalid credentials");
		}
		return true;
	}

	private static boolean checkIfAdminUser(DriverPrincipal driver) {
		return driver.getAuthorities().toString().contains("ADMIN");
	}
}
