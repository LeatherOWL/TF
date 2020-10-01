package com.threatfabric.threaddetectionapi.specification;

import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class DetectionInfoSpecification implements Specification<DetectionInfo> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<DetectionInfo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
    }
}
