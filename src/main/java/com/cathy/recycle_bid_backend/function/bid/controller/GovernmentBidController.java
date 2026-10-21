package com.cathy.recycle_bid_backend.function.bid.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cathy.recycle_bid_backend.function.bid.dto.GovernmentBidReq;
import com.cathy.recycle_bid_backend.function.bid.dto.GovernmentBidRes;
import com.cathy.recycle_bid_backend.function.bid.service.GovernmentBidService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/government-bids")
@RequiredArgsConstructor
public class GovernmentBidController {

    private final GovernmentBidService governmentBidService;

    // 新增
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody GovernmentBidReq req) {

        governmentBidService.create(req);

        return ResponseEntity.ok().build();
    }

    // 查詢全部
    @GetMapping
    public List<GovernmentBidRes> findAll() {
        return governmentBidService.findAll();
    }

    // 查詢單筆
    @GetMapping("/{id}")
    public GovernmentBidRes findById(@PathVariable("id") Long id) {
        return governmentBidService.findById(id);
    }

    // 修改
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody GovernmentBidReq req) {

        governmentBidService.update(id, req);

        return ResponseEntity.ok().build();
    }

    // 刪除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        governmentBidService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
