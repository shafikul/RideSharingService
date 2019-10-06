package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    private CarController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping("v1/d/cars/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO getCarById(@PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @PostMapping("v1/a/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    @DeleteMapping("v1/a/cars/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException {
        carService.delete(carId);
    }

    @GetMapping("v1/p/cars")
    @ResponseStatus(HttpStatus.OK)
    public List<CarDTO> findAllCars() {
        return CarMapper.makeCarDTOList(carService.findAllCars());
    }
}
