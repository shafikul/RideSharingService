package com.sagar.service.driver;

import java.util.List;

import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.OnlineStatus;
import com.sagar.exception.CarAlreadyInUseException;
import com.sagar.exception.ConstraintsViolationException;
import com.sagar.exception.EntityNotFoundException;

public interface DriverService {

	DriverDO find(Long driverId) throws EntityNotFoundException;

	DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

	void delete(Long driverId) throws EntityNotFoundException;

	void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

	List<DriverDO> find(OnlineStatus onlineStatus);

	void allocateCarToDriver(long driverId, long carId)
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException;

	void deAllocateCarFromDriver(long driverId) throws EntityNotFoundException;
}
