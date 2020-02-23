package com.sagar.controller.mapper;

import com.sagar.datatransferobject.DriverDTO;
import com.sagar.domainobject.DriverDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMapper {

    public static DriverDO makeDriverDO(DriverDTO driverDTO) {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO) {
        return DriverDTO.builder()
                .id(driverDO.getId())
                .password(driverDO.getPassword())
                .username(driverDO.getUsername())
                .coordinate(driverDO.getCoordinate())
                .carDTO(null == driverDO.getCar() ? null : CarMapper.makeCarDTO(driverDO.getCar()))
                .build();
    }

    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers) {
        return drivers.stream()
                .map(DriverMapper::makeDriverDTO)
                .collect(Collectors.toList());
    }
}
