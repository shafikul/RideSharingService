package com.sagar.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sagar.config.DriverPrincipal;
import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.OnlineStatus;
import com.sagar.exception.UnauthorizeAccessException;
import com.sagar.service.driver.DriverService;
import com.sagar.util.ValidatorUtil;

public class TestDriver {
	@InjectMocks
	DriverController driverController;

	@MockBean
	DriverService DriverService;

	@Mock
	Authentication authentication;

	@Mock
	SecurityContext securityContext;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	public void shouldGetDriver() throws UnauthorizeAccessException {
		Long driverId = 1L;
		DriverPrincipal driverPrincipal = new DriverPrincipal(getDriverMockObjecct(driverId));
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(driverPrincipal);
		Assert.assertTrue(ValidatorUtil.authorizeAdminOrDriverUser(driverId));

	}

	@Test(expected = UnauthorizeAccessException.class)
	public void shouldGetDriverThrowUnauthorizeAccessException() throws UnauthorizeAccessException {
		Long driverId = 1L;
		DriverPrincipal driverPrincipal = new DriverPrincipal(getUnauthenticatedDriverMockObjecct(100L));
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(driverPrincipal);
		ValidatorUtil.authorizeAdminOrDriverUser(driverId);

	}

	private DriverDO getDriverMockObjecct(Long driverId) {
		DriverDO driverDO = new DriverDO("admin", "password");
		driverDO.setId(driverId);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
		return driverDO;
	}

	private DriverDO getUnauthenticatedDriverMockObjecct(Long driverId) {
		DriverDO driverDO = new DriverDO("driver", "password");
		driverDO.setId(driverId);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
		return driverDO;
	}
}
