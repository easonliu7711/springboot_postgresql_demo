package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DeviceInfoDto;

import java.util.List;

public interface DeviceService {
    List<DeviceInfoDto> createDevices(List<DeviceInfoDto> deviceInfoDtoList);

    void deleteDevice(String deviceId);

    List<DeviceInfoDto> getDevices(int page, int pageSize);

    List<DeviceInfoDto> updateDevice(List<DeviceInfoDto> deviceInfoDtoList);
}
