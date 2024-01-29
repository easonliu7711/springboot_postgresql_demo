package com.example.demo.application.controller;

import com.example.demo.application.share.CreateDeviceInfoRequest;
import com.example.demo.application.share.UpdateDeviceInfoRequest;
import com.example.demo.domain.service.DeviceService;
import com.example.demo.domain.service.dto.DeviceInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${server.api-base-path}")
public class DeviceController extends BaseController{

    @Resource
    private DeviceService deviceService;

    @Operation(summary = "新增裝置", description = "新增裝置")
    @PostMapping("/v1/auth/devices")
    public String createDevices(@RequestBody List<CreateDeviceInfoRequest> createDeviceInfoRequestList) {
        List<DeviceInfoDto> deviceInfoDtoList = createDeviceInfoRequestList.stream().map(DeviceInfoDto::new).collect(java.util.stream.Collectors.toList());
        deviceService.createDevices(deviceInfoDtoList);
        return "Success";
    }

    @Operation(summary = "刪除裝置", description = "刪除裝置")
    @DeleteMapping("/v1/auth/devices")
    public String deleteDevice(@RequestParam String deviceId) {
        deviceService.deleteDevice(deviceId);
        return "Success";
    }

    @Operation(summary = "取得裝置列表", description = "取得裝置列表")
    @GetMapping("/v1/auth/devices/list")
    public Page<DeviceInfoDto> getDevices(@Schema(description = "要取得的頁面數(從0開始)") @RequestParam int page, @Schema(description = "每頁的筆數") @RequestParam int pageSize) {
        return deviceService.getDevices(page, pageSize);
    }

    @Operation(summary = "更新裝置", description = "更新裝置")
    @PutMapping("/v1/auth/devices")
    public String updateDevice(@RequestBody List<UpdateDeviceInfoRequest> updateDeviceInfoRequestList) {
        List<DeviceInfoDto> deviceInfoDtoList = updateDeviceInfoRequestList.stream().map(DeviceInfoDto::new).toList();
        deviceService.updateDevice(deviceInfoDtoList);
        return "Success";
    }

}
