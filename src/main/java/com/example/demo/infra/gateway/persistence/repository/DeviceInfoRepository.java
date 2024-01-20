package com.example.demo.infra.gateway.persistence.repository;

import com.example.demo.infra.gateway.persistence.model.DeviceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfoEntity, String> {
}
