package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

public interface CarService {

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO driverDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    void updateStatus(long driverId, long carId, Boolean status) throws EntityNotFoundException, CarAlreadyInUseException;

    List<CarDO> findAllCars();
}
