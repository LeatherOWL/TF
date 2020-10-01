package com.threatfabric.threaddetectionapi.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetectionDTO {

    private Long detectionId;
    @NotNull
    private Date time;
    @NotNull
    private String nameOfApp;
    @NotNull
    private String typeOfApp;
    @NotNull
    private DetectionType detectionType;
    @NotNull
    private DeviceDTO deviceInfo;
}
