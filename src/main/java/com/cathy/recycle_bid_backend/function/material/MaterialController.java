package com.cathy.recycle_bid_backend.function.material;

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

import com.cathy.recycle_bid_backend.function.material.dto.MaterialReq;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialRes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public List<MaterialRes> findAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    public MaterialRes findById(@PathVariable("id") Long id) {
        return materialService.findById(id);
    }

    @PostMapping
    public ResponseEntity<MaterialRes> create(@Valid @RequestBody MaterialReq req) {
        MaterialRes result = materialService.create(req);
        return ResponseEntity.created(URI.create("/api/materials/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public MaterialRes update(@PathVariable("id") Long id, @Valid @RequestBody MaterialReq req) {
        return materialService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
