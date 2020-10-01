package com.threatfabric.threaddetectionapi.specification;

import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetectionInfoSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public DetectionInfoSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public DetectionInfoSpecificationsBuilder with(String key, Object value) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public Specification<DetectionInfo> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(DetectionInfoSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result =  Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
