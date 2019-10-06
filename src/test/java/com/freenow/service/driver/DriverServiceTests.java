package com.freenow.service.driver;

import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

public class DriverServiceTests {

	@Mock
	private DriverRepository driverRepository;
	@Mock
	private CarRepository carRepository;

	private DriverService driverService;
	private CarDO carDO;
	private DriverDO driverDO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		driverService = new DefaultDriverService(driverRepository, carRepository);
	}

	@After
	public void teardown() {
		driverService = null;
	}

	@Test
	public void shouldAllocateCarToDriver()
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {

		long driverId = 1;
		long carId = 1;

		DriverDO driverDO = getDriverMockObjecct(driverId);
		CarDO carDO = getCarMockObject(carId);

		Mockito.when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(carDO);

		Mockito.when(driverRepository.findByIdAndDeletedFalse(driverId)).thenReturn(driverDO);

		driverService.allocateCarToDriver(driverId, carId);

		Assert.assertTrue(true);
	}

	@Test(expected = EntityNotFoundException.class)
	public void shouldThrowEntityNotFoundExceptionOnCarAllocation()
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {
		long driverId = 1;
		long carId = 1;
		Mockito.when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(null);
		driverService.allocateCarToDriver(driverId, carId);
	}

	@Test(expected = CarAlreadyInUseException.class)
	public void shouldThrowCarAlreadyInUseExceptionOnCarAllocation()
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {
		long driverId = 1;
		long carId = 1;

		DriverDO driverDO = getDriverMockObjecct(driverId);
		CarDO carDO = getCarMockObject(carId);
		carDO.setDriver(driverDO);

		Mockito.when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(carDO);

		Mockito.when(driverRepository.findByIdAndDeletedFalse(driverId)).thenReturn(driverDO);
		driverService.allocateCarToDriver(driverId, carId);
	}

	@Test(expected = ConstraintsViolationException.class)
	public void shouldThrowConstraintViolationExceptionOnCarAllocation()
			throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException {
		long driverId = 1;
		long carId = 1;

		DriverDO driverDO = getDriverMockObjecct(driverId);
		driverDO.setOnlineStatus(OnlineStatus.OFFLINE);
		CarDO carDO = getCarMockObject(carId);

		Mockito.when(carRepository.findByIdAndDeletedFalse(carId)).thenReturn(carDO);

		Mockito.when(driverRepository.findByIdAndDeletedFalse(driverId)).thenReturn(driverDO);
		driverService.allocateCarToDriver(driverId, carId);
	}

	@Test
	public void shouldDeAllocateCarFromDriver() throws EntityNotFoundException {
		long driverId = 1;
		DriverDO driverDO = getDriverMockObjecct(driverId);
		Optional<DriverDO> driverOptional = Optional.of(driverDO);
		Mockito.when(driverRepository.findById(1L)).thenReturn(driverOptional);
		driverService.deAllocateCarFromDriver(1L);
	}

	@Test(expected = EntityNotFoundException.class)
	public void shouldThrowEntityNotFoundExceptionOnCarDealocation() throws EntityNotFoundException {
		long driverId = 1;
		Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.empty());
		driverService.deAllocateCarFromDriver(driverId);
	}

	private DriverDO getDriverMockObjecct(Long driverId) {
		driverDO = new DriverDO("driver-1", "pass-1");
		driverDO.setId(1L);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
		return driverDO;
	}

	private CarDO getCarMockObject(Long carId) {
		carDO = new CarDO();
		carDO.setId(1L);
		carDO.setRating(5.0);
		return carDO;
	}
}
