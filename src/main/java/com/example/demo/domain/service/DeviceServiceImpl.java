package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DeviceInfoDto;
import com.example.demo.infra.gateway.persistence.model.DeviceInfoEntity;
import com.example.demo.infra.gateway.persistence.repository.DeviceInfoRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    private DeviceInfoRepository deviceInfoRepository;

    @Override
    public List<DeviceInfoDto> createDevices(List<DeviceInfoDto> deviceInfoDtoList) {
        // 將 DeviceInfoDto 轉換成 DeviceInfoEntity 並存入 deviceInfoRepository
        List<DeviceInfoEntity> deviceInfoEntityList = deviceInfoDtoList.stream().map(DeviceInfoEntity::new).toList();
        deviceInfoRepository.saveAll(deviceInfoEntityList);
        return deviceInfoEntityList.stream().map(DeviceInfoDto::new).toList();
    }

    @Override
    public void deleteDevice(String deviceId) {
        // 刪除 deviceInfoRepository 中的資料
        deviceInfoRepository.deleteById(deviceId);
    }

    @Override
    public Page<DeviceInfoDto> getDevices(int page, int pageSize) {
        // 查詢deviceInfoRepository 並轉換成 DeviceInfoDto
        Page<DeviceInfoEntity> deviceInfoEntityPage = deviceInfoRepository.findAll(PageRequest.of(page, pageSize, Sort.by("createTime").descending()));
        return deviceInfoEntityPage.map(DeviceInfoDto::new);
    }

    @Override
    public List<DeviceInfoDto> updateDevice(List<DeviceInfoDto> deviceInfoDtoList) {
        // 將 DeviceInfoDto 轉換成 DeviceInfoEntity 並存入 deviceInfoRepository
        List<DeviceInfoEntity> deviceInfoEntityList = deviceInfoDtoList.stream().map(DeviceInfoEntity::new).toList();
        deviceInfoRepository.saveAll(deviceInfoEntityList);
        return deviceInfoEntityList.stream().map(DeviceInfoDto::new).toList();
    }
}
