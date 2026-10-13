package com.cathy.recycle_bid_backend.function.material;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cathy.recycle_bid_backend.data.dao.MaterialRepository;
import com.cathy.recycle_bid_backend.data.entity.Material;
import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.material.dto.MaterialReq;
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

    // 查詢單筆
    @Transactional(readOnly = true)
    public MaterialRes findById(Long id) {
        Material entity = findEntityById(id);
        return MaterialRes.of(entity);
    }

    // 新增
    @Transactional
    public MaterialRes create(MaterialReq req) {

        Material entity = req.toEntity();

        Material saved = materialRepository.save(entity);

        return MaterialRes.of(saved);
    }

    // 修改
    @Transactional
    public MaterialRes update(Long id, MaterialReq req) {

        Material entity = findEntityById(id);

        entity.setName(req.getName());
        entity.setCategory(req.getCategory());
        entity.setUnit(req.getUnit());
        entity.setEnabled(req.getEnabled());

        Material saved = materialRepository.save(entity);

        return MaterialRes.of(saved);
    }



    // 共用：根據 ID 查詢 Entity
    private Material findEntityById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new BusinessException("找不到此回收品項"));
    }

}
