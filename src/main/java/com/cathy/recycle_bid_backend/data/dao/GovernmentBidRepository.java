package com.cathy.recycle_bid_backend.data.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathy.recycle_bid_backend.data.entity.GovernmentBid;

public interface GovernmentBidRepository
        extends JpaRepository<GovernmentBid, Long> {

    // 起訖都有填：建立時間 >= 起日零點，且 < 迄日的隔天零點
    List<GovernmentBid> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            LocalDateTime createdFrom, LocalDateTime createdBefore);

    // 只填起日
    List<GovernmentBid> findByCreatedAtGreaterThanEqual(LocalDateTime createdFrom);

    // 只填迄日
    List<GovernmentBid> findByCreatedAtLessThan(LocalDateTime createdBefore);
}
