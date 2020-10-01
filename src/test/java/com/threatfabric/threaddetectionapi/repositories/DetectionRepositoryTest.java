package com.threatfabric.threaddetectionapi.repositories;

import com.threatfabric.threaddetectionapi.bootstrap.DataLoader;
import com.threatfabric.threaddetectionapi.services.DetectionService;
import com.threatfabric.threaddetectionapi.services.DetectionServiceImpl;
import com.threatfabric.threaddetectionapi.services.DeviceService;
import com.threatfabric.threaddetectionapi.services.DeviceServiceImpl;
import com.threatfabric.threaddetectionapi.specification.DetectionInfoSpecification;
import com.threatfabric.threaddetectionapi.specification.SearchCriteria;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.mapper.DeviceMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DetectionRepositoryTest {

    @Autowired
    DetectionRepository detectionRepository;
    @Autowired
    DeviceRepository deviceRepository;
    private DetectionMapper detectionMapper = DetectionMapper.INSTANCE;
    private DeviceMapper deviceMapper = DeviceMapper.INSTANCE;

    private DetectionService detectionService;
    private DeviceService deviceService;

    @BeforeEach
    void setup() {

        detectionService = new DetectionServiceImpl(detectionMapper, detectionRepository, deviceRepository);
        deviceService = new DeviceServiceImpl(deviceMapper, deviceRepository);

        DataLoader bootstrap = new DataLoader(detectionMapper, detectionService, deviceRepository, deviceService);
        bootstrap.run();
    }

    @Test
    public void predicateTest() {
        List<DetectionInfo> all = detectionRepository.findAll();
        assertThat(all).hasSize(2);

        DetectionInfoSpecification spec =
                new DetectionInfoSpecification(new SearchCriteria("nameOfApp", "appName"));

        List<DetectionInfo> allPredicate = detectionRepository.findAll(spec);

        assertThat(allPredicate).hasSize(1);
    }

    @Test
    void getAll() {
        List<DetectionDTO> allDTOs = detectionService.findAll();
        assertThat(allDTOs).hasSize(2);
    }

    @Test
    void getDetectionById() {
        DetectionDTO byId = detectionService.getById(1L);
        assertThat(byId).isNotNull();
    }

    //TODO more tests
}
