package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freenow.domainvalue.EngineType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class CarDTO {

    private Long id;

    @NotEmpty(message = "License Plate must be present")
    private String licensePlate;

    @NotNull(message = "Seat count can't be null")
    private Integer seatCount;

    private Boolean convertible;

    private Double rating;

    private EngineType engineType;

    @NotEmpty(message = "Manufacturer must be present")
    private String manufacturer;
}
