package org.colendi.infra.credit.jpa;

import org.colendi.infra.credit.jpa.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CreditRepository extends JpaRepository<CreditEntity, Long>, JpaSpecificationExecutor<CreditEntity> {
    List<CreditEntity> findByUserId(Long userId);
}
