package com.example.demo.infra.gateway.persistence.model;

import com.example.demo.domain.service.dto.DeviceInfoDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@ToString
@Entity
@Table(name = "device_info")
@EntityListeners(AuditingEntityListener.class)
public class DeviceInfoEntity {

    @Id
    @Column(name = "device_id", unique = true, length = 50)
    private String deviceId;
    @Column(name = "device_name", length = 50)
    private String deviceName;
    @Column(name = "device_type", length = 50)
    private String deviceType;
    @Column(name = "device_status", length = 10)
    private String deviceStatus;
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public DeviceInfoEntity(DeviceInfoDto deviceInfoDto) {
        this.deviceId = deviceInfoDto.getDeviceId();
        this.deviceName = deviceInfoDto.getDeviceName();
        this.deviceType = deviceInfoDto.getDeviceType();
        this.deviceStatus = deviceInfoDto.getDeviceStatus();
    }
}
