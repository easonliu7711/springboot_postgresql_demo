package com.example.demo.domain.service.dto;

import com.example.demo.application.share.DeviceInfoRequest;
import com.example.demo.infra.gateway.persistence.model.DeviceInfoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema
@Data
@ToString
public class DeviceInfoDto {

    @Schema(description = "裝置ID")
    private String deviceId;
    @Schema(description = "裝置名稱")
    private String deviceName;
    @Schema(description = "裝置類型")
    private String deviceType;
    @Schema(description = "裝置狀態")
    private String deviceStatus;
    @Schema(description = "建立時間")
    private LocalDateTime createTime;
    @Schema(description = "更新時間")
    private LocalDateTime updateTime;

    public DeviceInfoDto(DeviceInfoEntity deviceInfoEntity) {
        this.deviceId = deviceInfoEntity.getDeviceId();
        this.deviceName = deviceInfoEntity.getDeviceName();
        this.deviceType = deviceInfoEntity.getDeviceType();
        this.deviceStatus = deviceInfoEntity.getDeviceStatus();
        this.createTime = deviceInfoEntity.getCreateTime();
        this.updateTime = deviceInfoEntity.getUpdateTime();
    }

    public DeviceInfoDto(DeviceInfoRequest deviceInfoRequest) {
        this.deviceId = deviceInfoRequest.getDeviceId();
        this.deviceName = deviceInfoRequest.getDeviceName();
        this.deviceType = deviceInfoRequest.getDeviceType();
        this.deviceStatus = deviceInfoRequest.getDeviceStatus();
    }
}
