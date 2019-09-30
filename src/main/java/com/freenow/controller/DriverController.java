package com.freenow.controller;

import com.freenow.config.DriverPrincipal;
import com.freenow.config.DriverPrincipalDetailsService;
import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DriverService;

import java.security.AuthProvider;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.bind.annotation.*;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/d/drivers")
public class DriverController {

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
            @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
            throws EntityNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @PatchMapping("/{driverId}/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public void selectCar(@PathVariable long driverId, @PathVariable long carId) throws EntityNotFoundException,
            CarAlreadyInUseException {
        DriverPrincipal driver =  (DriverPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(driver.getAuthorities().toString().contains("ADMIN") || driver.getId() == driverId){
            driverService.allocateCarToDriver(driverId, carId);
        }else{
            throw new EntityNotFoundException("Invalid credentials");
        }
    }

    @PatchMapping("/{driverId}")
    public void deSelectCar(@PathVariable long driverId) throws EntityNotFoundException {
        DriverPrincipal driver =  (DriverPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(driver.getAuthorities().toString().contains("ADMIN") || driver.getId() == driverId){
            driverService.deAllocateCarFromDriver(driverId);
        }else{
            throw new EntityNotFoundException("Invalid credentials");
        }
    }
}
