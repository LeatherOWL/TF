package com.threatfabric.threaddetectionapi.services;

import com.threatfabric.threaddetectionapi.repositories.DetectionRepository;
import com.threatfabric.threaddetectionapi.repositories.DeviceRepository;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class DetectionServiceTest {

    private DetectionService detectionService;

    @Mock
    DetectionRepository detectionRepository;
    @Mock
    DeviceRepository deviceRepository;

    private DetectionMapper detectionMapper = DetectionMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        detectionService = new DetectionServiceImpl(detectionMapper, detectionRepository, deviceRepository);
    }

    @Test
    void testFindById() {

        DetectionInfo detectionInfo = new DetectionInfo();

        when(detectionRepository.findById(anyLong())).thenReturn(of(detectionInfo));

        DetectionDTO byId = detectionService.getById(1L);

        assertThat(byId).isNotNull();
    }

    //TODO more tests
}
