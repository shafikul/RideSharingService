package com.freenow.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;

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
