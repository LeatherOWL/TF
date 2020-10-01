package com.threatfabric.threaddetectionapi.v1.mapper;

import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DetectionMapper extends AbstractMapper {

    DetectionMapper INSTANCE = Mappers.getMapper(DetectionMapper.class);

    @Mapping(target = "detectionId", ignore = true)
    DetectionInfo detectionDTOToDetectionInfo(DetectionDTO detectionDTO);

    DetectionDTO detectionInfoToDetectionDTO(DetectionInfo detectionDTO);

    @Mapping(target = "detectionInfos", ignore = true)
    DeviceDTO deviceInfoToDeviceDTO(DeviceInfo deviceDTO);

}
