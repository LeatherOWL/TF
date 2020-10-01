package com.threatfabric.threaddetectionapi.repositories;

import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionRepository extends JpaRepository<DetectionInfo, Long>, JpaSpecificationExecutor<DetectionInfo> {

}
