package com.threatfabric.threaddetectionapi.v1.mapper;

import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import com.threatfabric.threaddetectionapi.v1.model.DeviceDTO;
import com.threatfabric.threaddetectionapi.v1.model.DeviceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper extends AbstractMapper{

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceInfo deviceDTOToDeviceInfo(DeviceDTO deviceDTO);

    DeviceDTO deviceInfoToDeviceDTO(DeviceInfo deviceDTO);

    @Mapping(target = "deviceInfo", ignore = true)
    DetectionDTO detectionInfoToDetectionDTO(DetectionInfo detectionDTO);
}
