package com.threatfabric.threaddetectionapi.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class DetectionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detectionId;
    @NotNull
    private long time;
    @NotNull
    private String nameOfApp;
    @NotNull
    private String typeOfApp;
    @NotNull
    private DetectionType detectionType;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceInfo deviceInfo;
}
