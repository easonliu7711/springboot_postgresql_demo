package com.example.demo.application.controller;

import com.example.demo.application.share.DeviceInfoRequest;
import com.example.demo.domain.service.DeviceService;
import com.example.demo.domain.service.dto.DeviceInfoDto;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @GetMapping("/v1/devices")
    public List<DeviceInfoDto> getDevices(@RequestParam int page, @RequestParam int pageSize) {
        return deviceService.getDevices(page, pageSize);
    }

    @PostMapping("/v1/devices")
    public String createDevices(@RequestBody List<DeviceInfoRequest> deviceInfoRequestList) {
        List<DeviceInfoDto> deviceInfoDtoList = deviceInfoRequestList.stream().map(DeviceInfoDto::new).collect(java.util.stream.Collectors.toList());
        deviceService.createDevices(deviceInfoDtoList);
        return "Success";
    }

    @DeleteMapping("/v1/device/{deviceId}")
    public String deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
        return "Success";
    }

    @PutMapping("/v1/devices")
    public String updateDevice(@RequestBody List<DeviceInfoRequest> deviceInfoRequestList) {
        List<DeviceInfoDto> deviceInfoDtoList = deviceInfoRequestList.stream().map(DeviceInfoDto::new).toList();
        deviceService.updateDevice(deviceInfoDtoList);
        return "Success";
    }

}
