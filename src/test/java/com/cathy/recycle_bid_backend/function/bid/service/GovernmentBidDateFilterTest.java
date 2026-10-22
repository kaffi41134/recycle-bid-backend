package com.cathy.recycle_bid_backend.function.bid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.cathy.recycle_bid_backend.data.dao.GovernmentBidRepository;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.dto.GovernmentBidRes;

import jakarta.persistence.EntityManager;

@DataJpaTest
class GovernmentBidDateFilterTest {

    @Autowired
    private GovernmentBidRepository repository;

    @Autowired
    private EntityManager entityManager;

    private GovernmentBidService service;

    @BeforeEach
    void setUp() {
        service = new GovernmentBidService(repository);
        for (String timestamp : new String[] {
                "2026-10-20T23:59:59", "2026-10-21T00:00:00",
                "2026-10-21T23:59:59.999999", "2026-10-22T00:00:00" }) {
            entityManager.createNativeQuery("insert into GOVERNMENT_BIDS (CREATED_AT) values (:createdAt)")
                    .setParameter("createdAt", LocalDateTime.parse(timestamp))
                    .executeUpdate();
        }
    }

    @Test
    void includesTheWholeEndDateAndExcludesTheNextMidnight() {
        LocalDate date = LocalDate.of(2026, 10, 21);
        assertThat(service.findAll(date, date))
                .extracting(GovernmentBidRes::getCreatedAt)
                .containsExactlyInAnyOrder(
                        date.atStartOfDay(), LocalDateTime.parse("2026-10-21T23:59:59.999999"));
    }

    @Test
    void supportsEitherBoundAndUnfilteredQueries() {
        LocalDate date = LocalDate.of(2026, 10, 21);
        assertThat(service.findAll(null, null)).hasSize(4);
        assertThat(service.findAll(date, null)).hasSize(3);
        assertThat(service.findAll(null, date)).hasSize(3);
        assertThat(service.findAll(date.plusDays(2), null)).isEmpty();
    }

    @Test
    void rejectsReversedDates() {
        assertThatThrownBy(() -> service.findAll(LocalDate.of(2026, 10, 22), LocalDate.of(2026, 10, 21)))
                .isInstanceOf(BusinessException.class)
                .hasMessage("建立日期起日不可晚於迄日");
    }
}
