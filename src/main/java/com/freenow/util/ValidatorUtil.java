package com.freenow.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.freenow.config.DriverPrincipal;
import com.freenow.exception.UnauthorizeAccessException;

public class ValidatorUtil {

	public static void authorizeAdminOrDriverUser(long driverId) throws UnauthorizeAccessException {
		DriverPrincipal driver = (DriverPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!checkIfAdminUser(driver) && driver.getId() != driverId) {
			throw new UnauthorizeAccessException("Invalid credentials");
		}
	}

	private static boolean checkIfAdminUser(DriverPrincipal driver) {
		return driver.getAuthorities().toString().contains("ADMIN");
	}
}
