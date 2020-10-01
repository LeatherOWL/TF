package com.threatfabric.threaddetectionapi.services;

import com.threatfabric.threaddetectionapi.exceptions.ResourceNotFoundException;
import com.threatfabric.threaddetectionapi.repositories.DeviceRepository;
import com.threatfabric.threaddetectionapi.v1.mapper.DeviceMapper;
import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public Long save(DeviceDTO deviceDTO) {
        return deviceRepository.save(deviceMapper.deviceDTOToDeviceInfo(deviceDTO)).getDeviceId();
    }

    @Override
    public Collection<DeviceInfo> findAll() {
        return deviceRepository.findAll();

    }

    @Override
    public DeviceDTO getById(Long id) {
        DeviceInfo deviceInfo = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device with id {} was not found", id));

        return deviceMapper.deviceInfoToDeviceDTO(deviceInfo);
    }
}
