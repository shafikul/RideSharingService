package com.sagar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sagar.controller.mapper.DriverMapper;
import com.sagar.datatransferobject.DriverDTO;
import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.OnlineStatus;
import com.sagar.exception.CarAlreadyInUseException;
import com.sagar.exception.ConstraintsViolationException;
import com.sagar.exception.EntityNotFoundException;
import com.sagar.exception.UnauthorizeAccessException;
import com.sagar.service.driver.DriverService;
import com.sagar.util.ValidatorUtil;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
public class DriverController {

	private final DriverService driverService;

	@Autowired
	public DriverController(final DriverService driverService) {
		this.driverService = driverService;
	}

	@GetMapping("v1/d/drivers")
	public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) {
		return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
	}

	@GetMapping("v1/p/drivers/{driverId}")
	public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException {
		return DriverMapper.makeDriverDTO(driverService.find(driverId));
	}

	@PostMapping("v1/a/drivers")
	@ResponseStatus(HttpStatus.CREATED)
	public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
		DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
		return DriverMapper.makeDriverDTO(driverService.create(driverDO));
	}

	@DeleteMapping("v1/a/drivers/{driverId}")
	public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException {
		driverService.delete(driverId);
	}

	@PutMapping("v1/d/drivers/{driverId}")
	public void updateLocation(@PathVariable long driverId, @RequestParam double longitude,
			@RequestParam double latitude) throws EntityNotFoundException {
		driverService.updateLocation(driverId, longitude, latitude);
	}

	@PatchMapping("v1/d/drivers/{driverId}/{carId}")
	@ResponseStatus(HttpStatus.OK)
	public void selectCar(@PathVariable long driverId, @PathVariable long carId) throws EntityNotFoundException,
			CarAlreadyInUseException, ConstraintsViolationException, UnauthorizeAccessException {
		ValidatorUtil.authorizeAdminOrDriverUser(driverId);
		driverService.allocateCarToDriver(driverId, carId);
	}

	@PatchMapping("v1/d/drivers/{driverId}")
	public void deSelectCar(@PathVariable long driverId) throws EntityNotFoundException, UnauthorizeAccessException {
		ValidatorUtil.authorizeAdminOrDriverUser(driverId);
		driverService.deAllocateCarFromDriver(driverId);
	}
}
