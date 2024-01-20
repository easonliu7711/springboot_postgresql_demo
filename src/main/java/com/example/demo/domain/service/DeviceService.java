package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DeviceInfoDto;
import java.util.List;

public interface DeviceService {
    List<DeviceInfoDto> getDevices();
}
