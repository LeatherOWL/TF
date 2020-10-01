package com.threatfabric.threaddetectionapi.v1.mapper;

import java.util.Date;

public interface AbstractMapper {

    default Date mapEpochToDate(Long epoch) {
        return new Date(epoch);
    }

    default Long mapDateToEpoch(Date date) {
        return date.toInstant().toEpochMilli();
    }
}
