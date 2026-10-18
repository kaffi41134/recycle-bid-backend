package com.cathy.recycle_bid_backend.function.bid;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cathy.recycle_bid_backend.data.dao.BidRepository;
import com.cathy.recycle_bid_backend.data.entity.Bid;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.dto.BidReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidRes;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    @Transactional(readOnly = true)
    public List<BidRes> findAll() {
        return bidRepository.findAll().stream()
                .map(BidRes::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public BidRes findById(Long id) {
        Bid entity = findEntityById(id);
        return BidRes.of(entity);
    }

    @Transactional
    public BidRes create(BidReq req) {
        Bid saved = bidRepository.saveAndFlush(req.toEntity());
        return BidRes.of(saved);
    }

    @Transactional
    public BidRes update(Long id, BidReq req) {
        Bid entity = req.applyTo(findEntityById(id));
        return BidRes.of(bidRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        Bid entity = findEntityById(id);
        bidRepository.delete(entity);
    }

    private Bid findEntityById(Long id) {
        return bidRepository.findById(id)
                .orElseThrow(() -> new BusinessException("找不到此案件"));
    }

}
