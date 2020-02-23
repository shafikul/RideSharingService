package com.sagar.service.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sagar.dataaccessobject.DriverRepository;
import com.sagar.datatransferobject.SearchDTO;
import com.sagar.domainobject.CarDO;
import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.OnlineStatus;
import com.sagar.domainvalue.OperatorType;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some search(driver & car) specific things.
 * <p/>
 */
@Service
public class DefaultSearchService implements SearchService {

	public final DriverRepository driverRepository;

	public DefaultSearchService(final DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	/**
	 * Search driver and car related information based on user name, online status,
	 * license plate, car rating
	 *
	 * @param searchDTO
	 * @return
	 */
	@Override
	public List<DriverDO> searchDriverInfo(SearchDTO searchDTO) {
		List<DriverDO> list = driverRepository.findAllByDeletedFalse();
		return list.stream().filter(driverDO -> filterByStringField(driverDO.getUsername(), searchDTO.getUsername()))
				.filter(driverDO -> filterByOnlineStatue(driverDO.getOnlineStatus(), searchDTO.getOnlineStatus()))
				.filter(driverDO -> filterByLicensePlate(searchDTO, driverDO))
				.filter(driverDO -> filterByRating(searchDTO, driverDO))
				.filter(driverDO -> filterBySeatCount(searchDTO, driverDO)).collect(Collectors.toList());
	}

	private boolean filterByLicensePlate(SearchDTO searchDTO, DriverDO driverDO) {
		CarDO car = driverDO.getCar();
		String searchField = searchDTO.getLicensePlate();
		if (null == searchField || searchField.isEmpty())
			return true;
		if (null == car) {
			return false;
		}
		String licensePlate = car.getLicensePlate();
		if (null == licensePlate || licensePlate.isEmpty())
			return false;
		if (licensePlate.contains(searchField))
			return true;
		return false;
	}

	private boolean filterByRating(SearchDTO searchDTO, DriverDO driverDO) {
		CarDO car = driverDO.getCar();
		Double searchField = searchDTO.getRating();
		OperatorType operatorType = OperatorType.valueOfByName(searchDTO.getOperator());
		if (null == searchField || operatorType == null)
			return true;
		if (null == car || null == car.getRating()) {
			return false;
		}
		return acceptRatingFilterCondition(operatorType, searchField, car.getRating());
	}

	private boolean filterBySeatCount(SearchDTO searchDTO, DriverDO driverDO) {
		CarDO car = driverDO.getCar();
		Integer searchField = searchDTO.getSeatCount();
		OperatorType operatorType = OperatorType.valueOfByName(searchDTO.getOperator());
		if (null == searchField || operatorType == null)
			return true;
		if (null == car || null == car.getSeatCount()) {
			return false;
		}
		return acceptSeatCountFilterCondition(operatorType, searchField, car.getSeatCount());
	}

	private boolean acceptRatingFilterCondition(OperatorType operator, Double searchRating, Double rating) {
		switch (operator) {
		case GREATER_THAN:
			return rating > searchRating;
		case GREATER_THAN_EQUAL:
			return rating >= searchRating;
		case LESS_THAN:
			return rating < searchRating;
		case LESS_THAN_EQUAL:
			return rating <= searchRating;
		default:
			return false;
		}
	}

	private boolean acceptSeatCountFilterCondition(OperatorType operator, Integer searchSeatCount, Integer seatCount) {
		switch (operator) {
		case EQUAL:
			return seatCount.equals(searchSeatCount);
		case NOT_EQUAL:
			return !seatCount.equals(searchSeatCount);
		case GREATER_THAN:
			return seatCount > searchSeatCount;
		case GREATER_THAN_EQUAL:
			return seatCount >= searchSeatCount;
		case LESS_THAN:
			return seatCount < searchSeatCount;
		case LESS_THAN_EQUAL:
			return seatCount <= searchSeatCount;
		default:
			return false;
		}
	}

	private Boolean filterByStringField(String existingField, String searchField) {
		if (null == searchField || searchField.isEmpty())
			return true;
		if (null == existingField || existingField.isEmpty())
			return false;
		if (existingField.contains(searchField))
			return true;
		return false;
	}

	private Boolean filterByOnlineStatue(OnlineStatus exitingStatus, String givenStatus) {
		if (null == givenStatus || givenStatus.isEmpty())
			return true;
		if (exitingStatus.name().equalsIgnoreCase(givenStatus))
			return true;
		return false;
	}

	public static <T> Boolean genericNullCheck(T item) {
		return null == item;
	}
}
