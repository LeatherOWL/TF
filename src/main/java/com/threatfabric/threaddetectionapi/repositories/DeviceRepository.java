package com.threatfabric.threaddetectionapi.repositories;

import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceInfo, Long> {
}
