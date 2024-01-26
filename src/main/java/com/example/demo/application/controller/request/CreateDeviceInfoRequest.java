package com.example.demo.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "新增裝置資訊")
@Data
@ToString
public class CreateDeviceInfoRequest {

    @Schema(description = "裝置名稱")
    private String deviceName;
    @Schema(description = "裝置類型")
    private String deviceType;
    @Schema(description = "裝置狀態")
    private String deviceStatus;


}
