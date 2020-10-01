package com.threatfabric.threaddetectionapi.controllers;

import com.threatfabric.threaddetectionapi.services.DeviceService;
import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static java.lang.Long.valueOf;

@RestController
@RequestMapping(DeviceController.DEVICE_BASE_URL)
@AllArgsConstructor
public class DeviceController {

    public static final String DEVICE_BASE_URL = "/v1/device";

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO getDeviceById(@PathVariable String id) {
        return deviceService.getById(valueOf(id));
    }
}
