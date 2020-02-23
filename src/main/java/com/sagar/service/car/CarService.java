package com.sagar.service.car;

import java.util.List;

import com.sagar.domainobject.CarDO;
import com.sagar.exception.ConstraintsViolationException;
import com.sagar.exception.EntityNotFoundException;

public interface CarService {

	CarDO find(Long carId) throws EntityNotFoundException;

	CarDO create(CarDO driverDO) throws ConstraintsViolationException;

	void delete(Long carId) throws EntityNotFoundException;

	List<CarDO> findAllCars();
}
