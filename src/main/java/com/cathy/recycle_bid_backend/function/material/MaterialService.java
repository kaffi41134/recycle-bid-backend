package com.cathy.recycle_bid_backend.function.material;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.data.dao.MaterialRepository;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    // 查全部
    @Transactional(readOnly = true)
    public List<MaterialRes> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialRes::of)
                .toList();
    }


}
