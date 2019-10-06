package com.freenow.service.car;

import java.util.List;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

public interface CarService {

	CarDO find(Long carId) throws EntityNotFoundException;

	CarDO create(CarDO driverDO) throws ConstraintsViolationException;

	void delete(Long carId) throws EntityNotFoundException;

	List<CarDO> findAllCars();
}
