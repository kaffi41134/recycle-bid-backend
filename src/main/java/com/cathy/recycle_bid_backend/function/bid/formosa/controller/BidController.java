package com.cathy.recycle_bid_backend.function.bid.formosa.controller;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cathy.recycle_bid_backend.function.bid.formosa.dto.BidReq;
import com.cathy.recycle_bid_backend.function.bid.formosa.dto.BidRes;
import com.cathy.recycle_bid_backend.function.bid.formosa.service.BidExcelService;
import com.cathy.recycle_bid_backend.function.bid.formosa.service.BidService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final BidExcelService bidExcelService;

    @GetMapping
    public List<BidRes> findAll(
            @RequestParam(name = "keyword", required = false) String keyword,

            @RequestParam(name = "awardDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate awardDateFrom,

            @RequestParam(name = "awardDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate awardDateTo) {

        return bidService.findAll(keyword, awardDateFrom, awardDateTo);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
            @RequestParam(name = "keyword", required = false) String keyword,

            @RequestParam(name = "awardDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate awardDateFrom,

            @RequestParam(name = "awardDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate awardDateTo)
            throws IOException {

        // 1. 取得符合條件的 Excel 檔案內容
        byte[] file = bidExcelService.export(
                keyword, awardDateFrom, awardDateTo);

        // 2. 設定下載檔名，支援中文
        String disposition = ContentDisposition.attachment()
                .filename("台塑案件.xlsx", StandardCharsets.UTF_8)
                .build()
                .toString();

        // 3. 將檔案傳給瀏覽器
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition)
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(file.length)
                .body(file);
    }

    @GetMapping("/{id}")
    public BidRes findById(@PathVariable("id") Long id) {
        return bidService.findById(id);
    }

    @PostMapping
    public ResponseEntity<BidRes> create(@Valid @RequestBody BidReq req) {
        BidRes result = bidService.create(req);
        return ResponseEntity.created(URI.create("/api/bids/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public BidRes update(@PathVariable("id") Long id, @Valid @RequestBody BidReq req) {
        return bidService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        bidService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
