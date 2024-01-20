package com.example.demo.application.share;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeviceInfoRequest {

    private String deviceId;
    private String deviceName;
    private String deviceType;
    private String deviceStatus;

}
