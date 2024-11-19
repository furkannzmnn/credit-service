package org.colendi.domain.credit;

import org.colendi.domain.credit.model.Credit;
import org.colendi.domain.credit.model.command.SearchCredit;
import org.colendi.domain.credit.port.CreditPort;

import java.util.ArrayList;
import java.util.List;

public class FakeCreditAdapter implements CreditPort {

    private final List<Credit> credits = new ArrayList<>();

    @Override
    public Credit create(Credit credit) {
        credit.setId((long) credits.size() + 1);
        credits.add(credit);
        return credit;
    }

    @Override
    public List<Credit> retrieveByUserId(Long userId) {
        return credits.stream()
                .filter(credit -> credit.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<Credit> searchCredits(SearchCredit searchCredit) {
        return credits.stream()
                .filter(credit -> credit.getUserId().equals(searchCredit.userId()))
                .filter(credit -> credit.getStatus().equals(searchCredit.status()))
                .filter(credit -> credit.getCreatedAt().isAfter(searchCredit.startDate()))
                .filter(credit -> credit.getCreatedAt().isBefore(searchCredit.endDate()))
                .toList();
    }
}
