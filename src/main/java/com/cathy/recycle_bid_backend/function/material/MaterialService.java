package com.cathy.recycle_bid_backend.function.material;

import java.util.List;
import java.util.Map;

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

        String prefix = CATEGORY_PREFIX.get(entity.getCategory());
        if (prefix == null) {
            throw new BusinessException("尚未設定此分類的品項代碼前綴");
        }

        // flush 後才會取得 Hibernate 自動產生的建立時間。
        Material saved = materialRepository.saveAndFlush(entity);
        saved.setCode(prefix + "%03d".formatted(saved.getId()));

        return MaterialRes.of(saved);
    }

    // 修改
    @Transactional
    public MaterialRes update(Long id, MaterialReq req) {

        Material entity = req.applyto(findEntityById(id));

        Material saved = materialRepository.save(entity);

        return MaterialRes.of(saved);
    }

    // 刪除
    @Transactional
    public void delete(Long id) {
        Material entity = findEntityById(id);

        materialRepository.delete(entity);
    }

    // 共用：根據 ID 查詢 Entity
    private Material findEntityById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new BusinessException("找不到此回收品項"));
    }

    // 共用：根據分類取得對應的代碼前綴
    private static final Map<String, String> CATEGORY_PREFIX = Map.of(
            "金屬類", "MET",
            "塑膠類", "PLS",
            "玻璃類", "GLA",
            "紙類", "PAP");

}
