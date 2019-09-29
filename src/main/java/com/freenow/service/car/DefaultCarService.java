package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

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
     * @throws EntityNotFoundException if no car with the given id was found.
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
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        CarDO car;
        try {
            carDO.setDeleted(false);
            carDO.setDateCreated(ZonedDateTime.now());
            carDO.setUpdatedAt(ZonedDateTime.now());
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
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }

    private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find car with id: " + carId));
    }

    @Override
    public List<CarDO> findAllCars(){
        return carRepository.findAllByDeletedFalse();
    }

    @Override
    public void updateStatus(long driverId, long carId, Boolean status) throws EntityNotFoundException, CarAlreadyInUseException {

    }
}
