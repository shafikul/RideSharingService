package com.freenow.service.driver;

import java.util.List;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

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
