package com.threatfabric.threaddetectionapi.integration;

import com.threatfabric.threaddetectionapi.bootstrap.DataLoader;
import com.threatfabric.threaddetectionapi.repositories.DeviceRepository;
import com.threatfabric.threaddetectionapi.services.DetectionService;
import com.threatfabric.threaddetectionapi.services.DeviceService;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionType;
import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import com.threatfabric.threaddetectionapi.v1.model.DeviceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static com.threatfabric.threaddetectionapi.controllers.DetectionController.DETECTION_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DetectionITTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DetectionService detectionService;
    @Autowired
    private DeviceService deviceService;
    private DetectionMapper detectionMapper = DetectionMapper.INSTANCE;

    @BeforeEach
    void setup() {
        DataLoader bootstrap = new DataLoader(detectionMapper, detectionService, deviceRepository, deviceService);
        bootstrap.run();
    }

    @Test
    void getAll() {
        DetectionDTO[] detectionDTOs = restTemplate.getForObject(DETECTION_BASE_URL + "/all", DetectionDTO[].class);
        assertThat(detectionDTOs).hasSize(2);
    }

    @Test
    void getAllWithPredicate() {
        DetectionDTO[] detectionDTOs = restTemplate.getForObject(DETECTION_BASE_URL + "/all?nameOfApp=appName",
                DetectionDTO[].class);
        assertThat(detectionDTOs).hasSize(1);
    }

    @Test
    void getAllWithPredicate2() {
        DetectionDTO[] detectionDTOs = restTemplate.getForObject(DETECTION_BASE_URL +
                "/all?nameOfApp=appName&typeOfApp=appType", DetectionDTO[].class);
        assertThat(detectionDTOs).hasSize(1);
    }

    @Test
    void getAllWithPredicate3() {
        DetectionDTO[] detectionDTOs = restTemplate.getForObject(DETECTION_BASE_URL +
                "/all?nameOfApp=appName&typeOfApp=appType1", DetectionDTO[].class);
        assertThat(detectionDTOs).hasSize(0);
    }

    @Test
    void validationTest() {

        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setDetectionId(22L);
        Date date = new Date();
        detectionDTO.setTime(date);
//        detectionDTO.setNameOfApp("appName2");
        detectionDTO.setTypeOfApp("appType2");
        detectionDTO.setDetectionType(DetectionType.NEW);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceId(3L);
        deviceDTO.setDeviceType(DeviceType.ANDROID);
        deviceDTO.setDeviceModel("deviceMode2");
        deviceDTO.setOsVersion("osVersion");

        detectionDTO.setDeviceInfo(deviceDTO);

        ResponseEntity<String> response = restTemplate.postForEntity(DETECTION_BASE_URL, detectionDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("[\"nameOfApp: must not be null\"]");
    }

    @Test
    void constraintViolationTest() {
        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setDetectionId(22L);
        Date date = new Date();
        detectionDTO.setTime(date);
        detectionDTO.setNameOfApp("appName2");
        detectionDTO.setTypeOfApp("appType2");
        detectionDTO.setDetectionType(DetectionType.NEW);

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceId(4L);
//        deviceDTO.setDeviceType(DeviceType.ANDROID);
        deviceDTO.setDeviceModel("deviceMode2");
        deviceDTO.setOsVersion("osVersion");

        detectionDTO.setDeviceInfo(deviceDTO);

        ResponseEntity<String> response = restTemplate.postForEntity(DETECTION_BASE_URL, detectionDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("[\"deviceType: must not be null\"]");
    }

    //TODO add more tests
}
