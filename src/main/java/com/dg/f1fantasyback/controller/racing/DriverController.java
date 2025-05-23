package com.dg.f1fantasyback.controller.racing;

import com.dg.f1fantasyback.model.dto.driver.DriverDetailDto;
import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.service.DriverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public Iterable<DriverDto> getDrivers() {
        return driverService.getDrivers();
    }

    @GetMapping("/with-prices")
    public Iterable<DriverDetailDto> getDriversWithPrices() {
        return driverService.getDriversWithPrices();
    }

    @GetMapping("/{id}")
    public DriverDto getDriverById(@PathVariable Integer id) {
        return driverService.getDriverById(id);
    }
}
