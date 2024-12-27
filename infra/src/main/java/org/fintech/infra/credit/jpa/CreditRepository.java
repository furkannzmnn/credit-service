package org.fintech.infra.credit.jpa;

import org.fintech.infra.credit.jpa.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CreditRepository extends JpaRepository<CreditEntity, Long>, JpaSpecificationExecutor<CreditEntity> {
    List<CreditEntity> findByUserId(Long userId);
}
