package com.example.demo.application.controller;

import com.example.demo.domain.service.DeviceService;
import com.example.demo.domain.service.dto.DeviceInfoDto;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @GetMapping("/v1/devices")
    public List<DeviceInfoDto> getDevices() {
        return deviceService.getDevices();
    }

}
