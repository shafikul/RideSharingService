package com.sagar.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sagar.domainobject.DriverDO;
import com.sagar.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long> {

	List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

	DriverDO findByUsername(String username);

	List<DriverDO> findAllByDeletedFalse();

	DriverDO findByIdAndDeletedFalse(Long id);
}
