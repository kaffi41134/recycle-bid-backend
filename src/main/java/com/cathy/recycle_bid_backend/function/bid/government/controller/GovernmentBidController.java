package com.cathy.recycle_bid_backend.function.bid.government.controller;

import java.util.List;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cathy.recycle_bid_backend.function.bid.government.dto.GovernmentBidReq;
import com.cathy.recycle_bid_backend.function.bid.government.dto.GovernmentBidRes;
import com.cathy.recycle_bid_backend.function.bid.government.service.GovernmentBidService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/government-bids")
@RequiredArgsConstructor
public class GovernmentBidController {

    private final GovernmentBidService governmentBidService;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidParameter(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.badRequest().body("查詢參數格式錯誤，日期請使用 yyyy-MM-dd");
    }

    // 新增
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody GovernmentBidReq req) {

        governmentBidService.create(req);

        return ResponseEntity.ok().build();
    }

    // 查詢全部
    @GetMapping
    public List<GovernmentBidRes> findAll(
            @RequestParam(name = "createdDateFrom", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateFrom,
            @RequestParam(name = "createdDateTo", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateTo) {
        return governmentBidService.findAll(createdDateFrom, createdDateTo);
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
