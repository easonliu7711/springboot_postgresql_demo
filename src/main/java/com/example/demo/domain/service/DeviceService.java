package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DeviceInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeviceService {
    List<DeviceInfoDto> createDevices(List<DeviceInfoDto> deviceInfoDtoList);

    void deleteDevice(String deviceId);

    Page<DeviceInfoDto> getDevices(int page, int pageSize);

    List<DeviceInfoDto> updateDevice(List<DeviceInfoDto> deviceInfoDtoList);
}
