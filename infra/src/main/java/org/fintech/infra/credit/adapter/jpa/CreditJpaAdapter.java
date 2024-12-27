package org.fintech.infra.credit.adapter.jpa;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fintech.domain.credit.model.Credit;
import org.fintech.domain.credit.model.command.SearchCredit;
import org.fintech.domain.credit.port.CreditPort;
import org.fintech.infra.credit.jpa.CreditRepository;
import org.fintech.infra.credit.jpa.entity.CreditEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CreditJpaAdapter implements CreditPort {

    CreditRepository creditRepository;

    @Override
    public Credit create(Credit credit) {
        return creditRepository.save(CreditEntity.from(credit)).toModel();
    }

    @Override
    public List<Credit> retrieveByUserId(Long userId) {
        return creditRepository.findByUserId(userId)
                .stream()
                .map(CreditEntity::toModel)
                .toList();
    }

    @Override
    public List<Credit> searchCredits(SearchCredit searchCredit) {
        return creditRepository.findAll(CreditSpecification.build(searchCredit))
                .stream()
                .map(CreditEntity::toModel)
                .toList();
    }
}
