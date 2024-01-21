package com.example.demo.application.share;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema
@Data
@ToString
public class UpdateDeviceInfoRequest {

    @Schema(description = "裝置ID")
    private String deviceId;
    @Schema(description = "裝置名稱")
    private String deviceName;
    @Schema(description = "裝置類型")
    private String deviceType;
    @Schema(description = "裝置狀態")
    private String deviceStatus;

}
