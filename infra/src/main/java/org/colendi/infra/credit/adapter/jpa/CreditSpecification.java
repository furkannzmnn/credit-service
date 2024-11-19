package org.colendi.infra.credit.adapter.jpa;

import org.colendi.domain.credit.model.command.SearchCredit;
import org.colendi.infra.credit.jpa.entity.CreditEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CreditSpecification {

    public static Specification<CreditEntity> build(SearchCredit criteria) {
        return Specification.where(hasUserId(criteria.userId()))
                .and(hasStatus(criteria.status().toString()))
                .and(createdAfter(criteria.startDate()))
                .and(createdBefore(criteria.endDate()));
    }

    private static Specification<CreditEntity> hasUserId(Long userId) {
        return (root, query, cb) ->
                userId != null ? cb.equal(root.get("userId"), userId) : null;
    }

    private static Specification<CreditEntity> hasStatus(String status) {
        return (root, query, cb) ->
                status != null ? cb.equal(root.get("status"), status) : null;
    }

    private static Specification<CreditEntity> createdAfter(LocalDate startDate) {
        return (root, query, cb) ->
                startDate != null ? cb.greaterThanOrEqualTo(root.get("createdDate"), startDate) : null;
    }

    private static Specification<CreditEntity> createdBefore(LocalDate endDate) {
        return (root, query, cb) ->
                endDate != null ? cb.lessThanOrEqualTo(root.get("createdDate"), endDate) : null;
    }
}
