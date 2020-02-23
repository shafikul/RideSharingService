package com.sagar.dataaccessobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sagar.domainobject.CarDO;

public interface CarRepository extends CrudRepository<CarDO, Long> {

	List<CarDO> findAllByDeletedFalse();

	CarDO findByIdAndDeletedFalse(Long id);

}
