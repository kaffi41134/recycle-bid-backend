package com.cathy.recycle_bid_backend.function.bid.formosa.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.cathy.recycle_bid_backend.data.dao.BidRepository;
import com.cathy.recycle_bid_backend.data.entity.Bid;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.formosa.dto.BidReq;
import com.cathy.recycle_bid_backend.function.bid.formosa.dto.BidRes;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    // 依關鍵字與決標日期查詢
    @Transactional(readOnly = true)
    public List<BidRes> findAll(
            String keyword,
            LocalDate awardDateFrom,
            LocalDate awardDateTo) {

        // 1. 檢查日期範圍是否正確
        if (awardDateFrom != null
                && awardDateTo != null
                && awardDateFrom.isAfter(awardDateTo)) {
            throw new BusinessException("決標日期起日不可晚於迄日");
        }

        // 2. 整理關鍵字：沒有輸入時用空字串
        String searchText = keyword == null
                ? ""
                : keyword.strip().toLowerCase(Locale.ROOT);

        // 3. 查詢資料，並轉成前端需要的回應格式
        return bidRepository.search(
                searchText, awardDateFrom, awardDateTo)
                .stream()
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
