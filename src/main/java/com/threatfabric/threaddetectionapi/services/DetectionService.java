package com.threatfabric.threaddetectionapi.services;

import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DetectionService {
    DetectionDTO getById(Long id);
    DetectionInfo save(DetectionDTO detectionDTO);
    List<DetectionDTO> findAll();

    void deleteAll();

    List<DetectionDTO> findAll(Specification<DetectionInfo> build);
}
