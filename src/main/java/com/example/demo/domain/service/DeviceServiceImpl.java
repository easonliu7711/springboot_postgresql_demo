package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DeviceInfoDto;
import com.example.demo.infra.gateway.persistence.repository.DeviceInfoRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    private DeviceInfoRepository deviceInfoRepository;

    @Override
    public List<DeviceInfoDto> getDevices() {
        // 查詢deviceInfoRepository 並轉換成 DeviceInfoDto
        return deviceInfoRepository.findAll().stream().map(DeviceInfoDto::new).collect(java.util.stream.Collectors.toList());
    }
}
