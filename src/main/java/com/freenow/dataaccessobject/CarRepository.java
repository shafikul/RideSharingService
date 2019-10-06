package com.freenow.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.freenow.domainobject.CarDO;

public interface CarRepository extends CrudRepository<CarDO, Long> {

	List<CarDO> findAllByDeletedFalse();

	CarDO findByIdAndDeletedFalse(Long id);

}
