package com.cathy.recycle_bid_backend.function.bid.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.data.dao.GovernmentBidRepository;
import com.cathy.recycle_bid_backend.data.entity.GovernmentBid;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.dto.GovernmentBidReq;
import com.cathy.recycle_bid_backend.function.bid.dto.GovernmentBidRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentBidService {

    private final GovernmentBidRepository governmentBidRepository;

    // 查詢全部
    @Transactional(readOnly = true)
    public List<GovernmentBidRes> findAll() {
        return governmentBidRepository.findAll()
                .stream()
                .map(GovernmentBidRes::of)
                .toList();
    }

    // 查詢單筆
    @Transactional(readOnly = true)
    public GovernmentBidRes findById(Long id) {
        GovernmentBid entity = findEntityById(id);

        return GovernmentBidRes.of(entity);
    }

    // 新增
    @Transactional
    public void create(GovernmentBidReq req) {
        governmentBidRepository.save(req.toEntity());
    }

    // 修改
    @Transactional
    public void update(Long id, GovernmentBidReq req) {
        GovernmentBid entity = findEntityById(id);
        req.applyTo(entity);

        governmentBidRepository.save(entity);
    }

    // 刪除
    @Transactional
    public void delete(Long id) {
        GovernmentBid entity = findEntityById(id);

        governmentBidRepository.delete(entity);
    }

    // 共用：取得既有案件
    private GovernmentBid findEntityById(Long id) {
        return governmentBidRepository.findById(id)
                .orElseThrow(() -> new BusinessException("找不到此政府標案"));
    }
}
