package com.example.demo.domain.service.dto;

import com.example.demo.application.share.DeviceInfoRequest;
import com.example.demo.infra.gateway.persistence.model.DeviceInfoEntity;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class DeviceInfoDto {

    private String deviceId;
    private String deviceName;
    private String deviceType;
    private String deviceStatus;
    private LocalDateTime createTime;

    public DeviceInfoDto(DeviceInfoEntity deviceInfoEntity) {
        this.deviceId = deviceInfoEntity.getDeviceId();
        this.deviceName = deviceInfoEntity.getDeviceName();
        this.deviceType = deviceInfoEntity.getDeviceType();
        this.deviceStatus = deviceInfoEntity.getDeviceStatus();
        this.createTime = deviceInfoEntity.getCreateTime();
    }

    public DeviceInfoDto(DeviceInfoRequest deviceInfoRequest) {
        this.deviceId = deviceInfoRequest.getDeviceId();
        this.deviceName = deviceInfoRequest.getDeviceName();
        this.deviceType = deviceInfoRequest.getDeviceType();
        this.deviceStatus = deviceInfoRequest.getDeviceStatus();
    }
}
