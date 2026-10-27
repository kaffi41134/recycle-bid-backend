package com.cathy.recycle_bid_backend.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cathy.recycle_bid_backend.data.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {
}
