package com.cathy.recycle_bid_backend.function.bid.controller;

import com.cathy.recycle_bid_backend.function.bid.dto.BidDetailReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidImportRes;
import com.cathy.recycle_bid_backend.function.bid.dto.BidNoticeReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidNoticeRes;
import com.cathy.recycle_bid_backend.function.bid.service.BidImportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bids/import")
@RequiredArgsConstructor
public class BidImportController {

    private final BidImportService bidImportService;

    @PostMapping("/notices")
    public List<BidNoticeRes> getNotices(
            @Valid @RequestBody BidNoticeReq req) {
        return bidImportService.findNotices(req);
    }

    @PostMapping("/detail")
    public BidImportRes getDetail(
            @Valid @RequestBody BidDetailReq req) {
        return bidImportService.findDetail(req);
    }

}
