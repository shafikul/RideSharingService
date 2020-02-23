package com.sagar.datatransferobject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sagar.domainvalue.EngineType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private Long id;

    @NotEmpty(message = "License Plate must be present")
    @Size(min = 4, max = 20, message="length should be between 4 to 20")
    private String licensePlate;

    @Min(1)
    @Max(100)
    @NotNull(message = "Seat count can't be null")
    private Integer seatCount;

    private Boolean convertible;

    @Min(1)
    @Max(5)
    private Double rating;

    private EngineType engineType;

    @NotEmpty(message = "Manufacturer must be present")
    @Size(min = 4, max = 20, message="length should be between 4 to 20")
    private String manufacturer;
}
