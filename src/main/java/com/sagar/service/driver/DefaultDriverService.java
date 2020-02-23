package com.sagar.service.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sagar.dataaccessobject.CarRepository;
import com.sagar.dataaccessobject.DriverRepository;
import com.sagar.domainobject.CarDO;
import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.GeoCoordinate;
import com.sagar.domainvalue.OnlineStatus;
import com.sagar.exception.CarAlreadyInUseException;
import com.sagar.exception.ConstraintsViolationException;
import com.sagar.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final DriverRepository driverRepository;
	private final CarRepository carRepository;

	public DefaultDriverService(final DriverRepository driverRepository, CarRepository carRepository) {
		this.driverRepository = driverRepository;
		this.carRepository = carRepository;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param driverId
	 * @return found driver
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	public DriverDO find(Long driverId) throws EntityNotFoundException {
		return findDriverChecked(driverId);
	}

	/**
	 * Creates a new driver.
	 *
	 * @param driverDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a driver already exists with the given username, ... .
	 */
	@Override
	public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
		DriverDO driver;
		try {
			driver = driverRepository.save(driverDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return driver;
	}

	/**
	 * Deletes an existing driver by id.
	 *
	 * @param driverId
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long driverId) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setCar(null);
		driverDO.setDeleted(true);
	}

	/**
	 * Update the location for a driver.
	 *
	 * @param driverId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
	}

	/**
	 * Find all drivers by online state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<DriverDO> find(OnlineStatus onlineStatus) {
		return driverRepository.findByOnlineStatus(onlineStatus);
	}

	/**
	 * Allocate car from a driver based on availability & car existence
	 *
	 * @param driverId
	 * @param carId
	 * @throws EntityNotFoundException
	 * @throws CarAlreadyInUseException
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void allocateCarToDriver(long driverId, long carId)
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {
		CarDO carDO = carRepository.findByIdAndDeletedFalse(carId);
		if (null == carDO) {
			throw new EntityNotFoundException("Could not find car entity with id: " + carId);
		}
		DriverDO driverDO = findNonDeletedDriver(driverId);
		if (null == carDO.getDriver()) {
			driverDO.setCar(carDO);
			driverRepository.save(driverDO);
		} else {
			throw new CarAlreadyInUseException("Car already in use!!!");
		}
	}

	/**
	 * Deallocate a car from a driver
	 *
	 * @param driverId
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void deAllocateCarFromDriver(long driverId) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setCar(null);
		driverRepository.save(driverDO);
	}

	private DriverDO findNonDeletedDriver(Long driverId) throws EntityNotFoundException, ConstraintsViolationException {
		DriverDO driverDO = driverRepository.findByIdAndDeletedFalse(driverId);
		if (null == driverDO) {
			throw new EntityNotFoundException("Could not find entity with id: " + driverId);
		}
		if (driverDO.getOnlineStatus() == OnlineStatus.OFFLINE) {
			throw new ConstraintsViolationException("Driver is OFFLINE NOW");
		}
		return driverDO;
	}

	private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
		return driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
	}
}
