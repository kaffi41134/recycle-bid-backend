package com.cathy.recycle_bid_backend.data.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cathy.recycle_bid_backend.data.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = """
            SELECT * FROM bids b
            WHERE (
                   :keyword = ''
                OR LOWER(b.bid_no)           LIKE CONCAT('%', :keyword, '%')
                OR LOWER(b.material_code)    LIKE CONCAT('%', :keyword, '%')
                OR LOWER(b.item_description) LIKE CONCAT('%', :keyword, '%')
                OR LOWER(b.unit)             LIKE CONCAT('%', :keyword, '%')
                OR LOWER(b.pickup_location)  LIKE CONCAT('%', :keyword, '%')
                OR CAST(trim_scale(b.quantity) AS TEXT)
                   LIKE CONCAT('%', :keyword, '%')
                OR CAST(trim_scale(b.award_amount) AS TEXT)
                   LIKE CONCAT('%', :keyword, '%')
                OR TO_CHAR(b.award_date, 'YYYY-MM-DD')
                   LIKE CONCAT('%', :keyword, '%')
            )
            AND (
                CAST(:from AS DATE) IS NULL
                OR b.award_date >= CAST(:from AS DATE)
            )
            AND (
                CAST(:to AS DATE) IS NULL
                OR b.award_date <= CAST(:to AS DATE)
            )
            ORDER BY b.created_at DESC
            """, nativeQuery = true)
    List<Bid> search(
            @Param("keyword") String keyword,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);
}
