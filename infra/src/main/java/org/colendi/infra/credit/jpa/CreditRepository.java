package org.colendi.infra.credit.jpa;

import org.colendi.infra.credit.jpa.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
