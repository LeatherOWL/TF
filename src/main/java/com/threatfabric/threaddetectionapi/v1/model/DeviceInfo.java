package com.threatfabric.threaddetectionapi.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    @NotNull
    private DeviceType deviceType;
    @NotNull
    private String deviceModel;
    @NotNull
    private String osVersion;

    @OneToMany(mappedBy = "deviceInfo", cascade = CascadeType.ALL)
    private Set<DetectionInfo> detectionInfos = new HashSet<>();
}
