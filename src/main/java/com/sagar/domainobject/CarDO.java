package com.sagar.domainobject;

import com.sagar.domainvalue.EngineType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "cars",
uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"license_plate"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name="car_id")
    private Long id;

    @Column(nullable = false, name="license_plate")
    @NotNull(message = "License Plate can not be null!")
    private String licensePlate;

    @Column(nullable = false, name = "seat_count")
    @NotNull(message = "Seat count can't be null")
    private Integer seatCount;

    @Column(nullable = false)
    @Builder.Default
    private Boolean convertible = false;

    @Column(nullable = false)
    private Double rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "engine_type")
    private EngineType engineType;

    @Column(nullable = false)
    @NotNull(message = "Manufacturer can not be null!")
    private String manufacturer;

    @Column(name = "updated_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @OneToOne(mappedBy = "car")
    private DriverDO driver;

}