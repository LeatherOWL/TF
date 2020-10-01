package com.threatfabric.threaddetectionapi.bootstrap;

import com.threatfabric.threaddetectionapi.repositories.DeviceRepository;
import com.threatfabric.threaddetectionapi.services.DetectionService;
import com.threatfabric.threaddetectionapi.services.DeviceService;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import com.threatfabric.threaddetectionapi.v1.model.DetectionType;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import com.threatfabric.threaddetectionapi.v1.model.DeviceType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final DetectionMapper detectionMapper;
    private final DetectionService detectionService;
    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;

    @Override
    public void run(String... args) {

        detectionService.deleteAll();
        loadData();
    }

    private void loadData() {

        DetectionInfo detectionInfo = new DetectionInfo();
        detectionInfo.setDetectionType(DetectionType.NEW);
        detectionInfo.setTime(Instant.now().toEpochMilli());
        detectionInfo.setNameOfApp("appName");
        detectionInfo.setTypeOfApp("appType");

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceModel("deviceModel");
        deviceInfo.setDeviceType(DeviceType.ANDROID);
        deviceInfo.setOsVersion("osVersion");

        deviceRepository.save(deviceInfo);

        detectionInfo.setDeviceInfo(deviceInfo);

        detectionService.save(detectionMapper.detectionInfoToDetectionDTO(detectionInfo));

        DetectionInfo detectionInfo1 = new DetectionInfo();
        detectionInfo1.setDetectionType(DetectionType.RESOLVED);
        detectionInfo1.setTime(Instant.now().toEpochMilli());
        detectionInfo1.setNameOfApp("appName1");
        detectionInfo1.setTypeOfApp("appType1");
        detectionInfo1.setDeviceInfo(deviceInfo);

        detectionService.save(detectionMapper.detectionInfoToDetectionDTO(detectionInfo1));

        log.info("Detections loaded: {} ", detectionService.findAll().size());
        log.info("Devices loaded: {} ", deviceService.findAll().size());
    }
}
