package com.netcracker.edu.smartgreenhouse.server.controller.api;

import com.netcracker.edu.smartgreenhouse.server.domain.DeviceData;
import com.netcracker.edu.smartgreenhouse.server.exception.NotFoundException;
import com.netcracker.edu.smartgreenhouse.server.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.netcracker.edu.smartgreenhouse.server.constant.Format.DATE_FORMAT;

@RestController
@RequestMapping("/api/data")
public class DeviceDataController {
    private final DeviceDataService service;

    @Autowired
    public DeviceDataController(DeviceDataService service) {
        this.service = service;
    }

    @PostMapping("/{deviceId}")
    public void addDeviceData(@PathVariable UUID deviceId, @RequestBody DeviceData deviceData) {
        service.addDeviceData(deviceId, deviceData);
    }

    @GetMapping("/{deviceId}")
    public List<DeviceData> getDeviceData(@PathVariable UUID deviceId,
            @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date toDate) {
        return service.getDeviceData(deviceId, fromDate, toDate);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException exception) {
        return exception.getMessage();
    }
}
