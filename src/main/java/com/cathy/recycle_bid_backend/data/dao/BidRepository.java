package com.cathy.recycle_bid_backend.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathy.recycle_bid_backend.data.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
