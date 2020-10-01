package com.threatfabric.threaddetectionapi.services;

import com.threatfabric.threaddetectionapi.exceptions.ResourceNotFoundException;
import com.threatfabric.threaddetectionapi.repositories.DetectionRepository;
import com.threatfabric.threaddetectionapi.repositories.DeviceRepository;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DetectionServiceImpl implements DetectionService {

    private final DetectionMapper detectionMapper;
    private final DetectionRepository detectionRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public DetectionDTO getById(Long id) {

        DetectionInfo detectionInfo = detectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detection with id {} was not found", id));

        return detectionMapper.detectionInfoToDetectionDTO(detectionInfo);
    }

    @Override
    @Transactional
    public DetectionInfo save(DetectionDTO detectionDTO) {

        Optional<DeviceInfo> detectionDevice = deviceRepository.findById(detectionDTO.getDeviceInfo().getDeviceId());

        if (detectionDevice.isEmpty()) {
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceModel(detectionDTO.getDeviceInfo().getDeviceModel());
            deviceInfo.setOsVersion(detectionDTO.getDeviceInfo().getOsVersion());
            deviceInfo.setDeviceType(detectionDTO.getDeviceInfo().getDeviceType());
            DeviceInfo save = deviceRepository.save(deviceInfo);
            detectionDTO.getDeviceInfo().setDeviceId(save.getDeviceId());
        }

        return detectionRepository.save(
                detectionMapper.detectionDTOToDetectionInfo(detectionDTO));
    }

    @Override
    public List<DetectionDTO> findAll() {
        return detectionRepository.findAll(Sort.by(Sort.Direction.DESC, "time")).stream()
                .map(detectionMapper::detectionInfoToDetectionDTO).collect(Collectors.toList());
    }

    @Override
    public List<DetectionDTO> findAll(Specification<DetectionInfo> specification) {
        return detectionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "time")).stream().map(
                detectionMapper::detectionInfoToDetectionDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAll() {
        detectionRepository.deleteAll();
    }

}
