package com.threatfabric.threaddetectionapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threatfabric.threaddetectionapi.services.DetectionService;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DetectionControllerTest {

    private DetectionMapper detectionMapper = DetectionMapper.INSTANCE;

    @Mock
    private DetectionService detectionService;

    @InjectMocks
    DetectionController detectionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(detectionController)
                .setControllerAdvice(DetectionController.DetectionControllerAdvice.class)
                .build();
    }

    @Test
    void listDetections() throws Exception {

        when(detectionService.findAll()).thenReturn(asList(new DetectionDTO(), new DetectionDTO()));

        mockMvc.perform(get("/v1/detection/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findDetectionById() throws Exception {
        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setDetectionId(1L);

        when(detectionService.getById(anyLong())).thenReturn(detectionDTO);

        mockMvc.perform(get("/v1/detection/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detectionId", equalTo(1)));
    }

    @Test
    void postDetection() throws Exception {

        // given
        DetectionDTO detectionDTO = new DetectionDTO();
        detectionDTO.setDetectionId(1L);
        detectionDTO.setNameOfApp("bla");
        detectionDTO.setDetectionType(DetectionType.NEW);
        Date date = new Date();
        detectionDTO.setTime(date);
        detectionDTO.setTypeOfApp("bla");

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceId(1L);
        deviceDTO.setDeviceModel("bla");
        deviceDTO.setDeviceType(DeviceType.ANDROID);
        deviceDTO.setOsVersion("bla");

        detectionDTO.setDeviceInfo(deviceDTO);

        DetectionInfo detectionInfo = detectionMapper.detectionDTOToDetectionInfo(detectionDTO);
        detectionInfo.setDetectionId(detectionDTO.getDetectionId());

        when(detectionService.save(any(DetectionDTO.class))).thenReturn(detectionInfo);

        // when/then
        mockMvc.perform(post("/v1/detection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(detectionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.detectionId", equalTo(1)))
                .andExpect(jsonPath("$.time", equalTo(date.toInstant().toEpochMilli())))
                .andExpect(jsonPath("$.nameOfApp", equalTo("bla")))
                .andExpect(jsonPath("$.detectionType", equalTo("NEW")))
                .andExpect(jsonPath("$.deviceInfo.deviceId", equalTo(1)))
                .andExpect(jsonPath("$.deviceInfo.deviceType", equalTo("ANDROID")))
                .andExpect(jsonPath("$.deviceInfo.deviceModel", equalTo("bla")))
                .andExpect(jsonPath("$.deviceInfo.osVersion", equalTo("bla")));
    }

    //TODO more tests

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
