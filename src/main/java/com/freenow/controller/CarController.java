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

@RestController
@RequestMapping("v1/a/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    private CarController(final CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO getCarById(@PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }


    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException {
        carService.delete(carId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarDTO> findAllCars() {
        return CarMapper.makeCarDTOList(carService.findAllCars());
    }
}
