package com.threatfabric.threaddetectionapi.services;

import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;

import java.util.Collection;

public interface DeviceService {
    Long save(DeviceDTO deviceDTO);

    Collection<DeviceInfo> findAll();

    DeviceDTO getById(Long id);
}
