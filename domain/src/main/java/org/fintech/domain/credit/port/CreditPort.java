package org.fintech.domain.credit.port;

import org.fintech.domain.credit.model.Credit;
import org.fintech.domain.credit.model.command.SearchCredit;

import java.util.List;

public interface CreditPort {

    Credit create(Credit credit);

    List<Credit> retrieveByUserId(Long userId);

    List<Credit> searchCredits(SearchCredit searchCredit);
}
