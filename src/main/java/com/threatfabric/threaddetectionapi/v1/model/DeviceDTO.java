package com.threatfabric.threaddetectionapi.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private Long deviceId;
    @NotNull
    private DeviceType deviceType;
    @NotNull
    private String deviceModel;
    @NotNull
    private String osVersion;
    @NotNull
    private Set<DetectionDTO> detectionInfos;
}
