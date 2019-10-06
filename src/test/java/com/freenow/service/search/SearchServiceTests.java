package com.freenow.service.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.SearchDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;

public class SearchServiceTests {

	@Mock
	private DriverRepository driverRepository;

	private SearchService searchService;
	private DriverDO driverDO;
	private SearchDTO searchDTO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		searchService = new DefaultSearchService(driverRepository);

		List<DriverDO> driverList = new ArrayList<DriverDO>();
		driverDO = new DriverDO("driver-1", "pass123");
		driverDO.setId((long) 100);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
		driverList.add(driverDO);

		searchDTO = SearchDTO.builder().username("driver-1").onlineStatus("ONLINE").build();
		searchDTO.setRating(4.0);
		searchDTO.setLicensePlate("LICENCE-1");

		CarDO carDO = new CarDO();
		carDO.setId((long) 1);
		carDO.setLicensePlate("LICENCE-1");
		carDO.setRating(4.0);

		driverDO.setCar(carDO);

		Mockito.when(driverRepository.findAllByDeletedFalse()).thenReturn(driverList);
	}

	@After
	public void tearDown() {
		searchService = null;
	}

	@Test
	public void shouldSearchDriverInfo() {
		List<DriverDO> driverList = searchService.searchDriverInfo(searchDTO);

		Assert.assertEquals((long) 1, driverList.size());
		Assert.assertEquals("driver-1", driverList.get(0).getUsername());
	}
}
