package com.sagar.service.car;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sagar.dataaccessobject.CarRepository;
import com.sagar.domainobject.CarDO;
import com.sagar.domainobject.DriverDO;
import com.sagar.exception.ConstraintsViolationException;
import com.sagar.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

	private final CarRepository carRepository;

	public DefaultCarService(final CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param carId
	 * @return found car
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 */
	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		return findCarChecked(carId);
	}

	/**
	 * Creates a new driver.
	 *
	 * @param carDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a driver already exists with the given username, ... .
	 */
	@Override
	@Transactional
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {
		CarDO car;
		try {			
			car = carRepository.save(carDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a driver: {}", carDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return car;
	}

	/**
	 * Deletes an existing car by id.
	 *
	 * @param carId
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long carId) throws EntityNotFoundException {
		CarDO carDO = findCarChecked(carId);
		DriverDO driverDO = carDO.getDriver();
		if(null != driverDO) {
			driverDO.setCar(null);
		}
		carDO.setDriver(null);
		carDO.setDeleted(true);
		
	}

	private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
		return carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find car with id: " + carId));
	}

	/**
	 * Get all non deleted cars
	 *
	 * @return
	 */
	@Override
	public List<CarDO> findAllCars() {
		return carRepository.findAllByDeletedFalse();
	}
}
