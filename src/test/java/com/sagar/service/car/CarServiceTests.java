package com.sagar.service.car;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sagar.dataaccessobject.CarRepository;
import com.sagar.domainobject.CarDO;
import com.sagar.domainvalue.EngineType;
import com.sagar.exception.EntityNotFoundException;

public class CarServiceTests {

	@Mock
	private CarRepository carRepository;
	private CarService carService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		carService = new DefaultCarService(carRepository);

		long carId = 1;
		Optional<CarDO> carDO = Optional.of(new CarDO());
		carDO.get().setId(carId);
		carDO.get().setEngineType(EngineType.DIESEL);
		Mockito.when(carRepository.findById((long) 1)).thenReturn(carDO);

		CarDO car = new CarDO();
		car.setId(carId);

		List<CarDO> carList = new ArrayList<>();
		carList.add(car);
		Mockito.when(carRepository.findAllByDeletedFalse()).thenReturn(carList);
	}

	@After
	public void tearDown() {
		carService = null;
	}

	@Test
	public void shouldFindSingleCar() throws EntityNotFoundException {
		long carId = 1;
		CarDO car = carService.find(carId);
		assertTrue(car.getId() == carId);
	}

	@Test
	public void shouldFindAllCars() {
		List<CarDO> cars = carService.findAllCars();
		Assert.assertEquals(cars.size(), 1);
	}
}
