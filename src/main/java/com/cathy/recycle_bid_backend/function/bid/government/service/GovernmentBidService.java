package com.cathy.recycle_bid_backend.function.bid.government.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.data.dao.GovernmentBidRepository;
import com.cathy.recycle_bid_backend.data.entity.GovernmentBid;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.government.dto.GovernmentBidReq;
import com.cathy.recycle_bid_backend.function.bid.government.dto.GovernmentBidRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentBidService {

    private final GovernmentBidRepository governmentBidRepository;

    // 依建立日期查詢，未填日期時查詢全部
    @Transactional(readOnly = true)
    public List<GovernmentBidRes> findAll(LocalDate createdDateFrom, LocalDate createdDateTo) {
        if (createdDateFrom != null && createdDateTo != null && createdDateFrom.isAfter(createdDateTo)) {
            throw new BusinessException("建立日期起日不可晚於迄日");
        }
        if (LocalDate.MAX.equals(createdDateTo)) {
            throw new BusinessException("建立日期迄日超出可查詢範圍");
        }
        List<GovernmentBid> bids;

        if (createdDateFrom != null && createdDateTo != null) {
            // 起訖都有填
            bids = governmentBidRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    createdDateFrom.atStartOfDay(),
                    createdDateTo.plusDays(1).atStartOfDay());
        } else if (createdDateFrom != null) {
            // 只填起日
            bids = governmentBidRepository.findByCreatedAtGreaterThanEqual(createdDateFrom.atStartOfDay());
        } else if (createdDateTo != null) {
            // 只填迄日，包含當天整天
            bids = governmentBidRepository.findByCreatedAtLessThan(createdDateTo.plusDays(1).atStartOfDay());
        } else {
            // 都沒填
            bids = governmentBidRepository.findAll();
        }

        return bids.stream()
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
