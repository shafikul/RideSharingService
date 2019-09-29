package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {

    public static CarDO makeCarDO(CarDTO carDTO){

        return CarDO.builder()
                .seatCount(carDTO.getSeatCount())
                .manufacturer(carDTO.getManufacturer())
                .convertible(carDTO.getConvertible())
                .engineType(carDTO.getEngineType())
                .licensePlate(carDTO.getLicensePlate())
                .rating(carDTO.getRating())
                .build();

    }

    public static CarDTO makeCarDTO(CarDO carDO) {
        return CarDTO.builder()
                .id(carDO.getId())
                .seatCount(carDO.getSeatCount())
                .manufacturer(carDO.getManufacturer())
                .convertible(carDO.getConvertible())
                .engineType(carDO.getEngineType())
                .licensePlate(carDO.getLicensePlate())
                .rating(carDO.getRating())
                .build();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
        return cars.stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }
}
