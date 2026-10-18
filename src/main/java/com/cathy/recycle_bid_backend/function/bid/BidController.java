package com.cathy.recycle_bid_backend.function.bid;

import java.net.URI;
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

import com.cathy.recycle_bid_backend.function.bid.dto.BidReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidRes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping
    public List<BidRes> findAll() {
        return bidService.findAll();
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
