package com.cathy.recycle_bid_backend.function.bid.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.cathy.recycle_bid_backend.exception.BusinessException;
import com.cathy.recycle_bid_backend.function.bid.dto.BidDetailReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidImportRes;
import com.cathy.recycle_bid_backend.function.bid.dto.BidNoticeReq;
import com.cathy.recycle_bid_backend.function.bid.dto.BidNoticeRes;

import tools.jackson.databind.JsonNode;

@Service
public class BidImportService {

    private final RestClient restClient = RestClient.create("https://pcc-api.openfun.app");

    // 查詢公告清單
    public List<BidNoticeRes> findNotices(BidNoticeReq req) {

        // 1. 取得並驗證標案公告資料
        JsonNode root = getTenderData(req.unitId(), req.jobNumber());
        JsonNode records = root.path("records");

        List<BidNoticeRes> result = new ArrayList<>();

        // 2. 逐筆整理公告資料
        for (JsonNode record : records) {

            JsonNode brief = record.path("brief");

            BidNoticeRes notice = new BidNoticeRes(
                    record.path("unit_id").asString(),
                    record.path("job_number").asString(),
                    record.path("date").asString(),
                    brief.path("type").asString(),
                    brief.path("title").asString(),
                    record.path("filename").asString());

            result.add(notice);
        }

        // 3. 公告日期由新到舊排序
        result.sort(
                Comparator.comparing(BidNoticeRes::noticeDate)
                        .reversed());

        return result;
    }

    // 查詢公告明細
    public BidImportRes findDetail(BidDetailReq req) {

        JsonNode root = getTenderData(req.unitId(), req.jobNumber());

        JsonNode records = root.path("records");

        JsonNode selected = null;

        for (JsonNode record : records) {

            boolean sameDate = req.noticeDate().equals(
                    record.path("date").asString());

            boolean sameFilename = req.filename().equals(
                    record.path("filename").asString());

            if (sameDate && sameFilename) {
                if (selected != null) {
                    throw new BusinessException("找到多筆相同的公告");
                }
                selected = record;
            }
        }

        if (selected == null) {
            throw new BusinessException("找不到指定公告，請重新查詢公告清單");
        }

        JsonNode brief = selected.path("brief");
        JsonNode detail = selected.path("detail");

        return new BidImportRes(
                selected.path("unit_id").asString(),
                selected.path("job_number").asString(),
                selected.path("unit_name").asString(),
                brief.path("title").asString(),
                selected.path("date").asString(),
                brief.path("type").asString(),
                brief.path("category").asString(),
                detail.path("招標資料:招標方式").asString(),
                detail.path("領投開標:截止投標").asString(),
                detail.path("領投開標:開標時間").asString(),
                detail.path("領投開標:開標地點").asString(),
                detail.path("機關資料:聯絡人").asString(),
                detail.path("機關資料:聯絡電話").asString(),
                detail.path("採購資料:預算金額").asString(),
                detail.path("領投開標:是否須繳納押標金").asString(),
                detail.path("其他:履約地點").asString(),
                detail.path("url").asString());
    }

    // 共用：取得並驗證標案公告資料
    private JsonNode getTenderData(String unitId, String jobNumber) {
        JsonNode root = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/tender")
                        .queryParam("unit_id", unitId)
                        .queryParam("job_number", jobNumber)
                        .build())
                .retrieve()
                .body(JsonNode.class);

        if (root == null || !root.path("records").isArray()) {
            throw new BusinessException("無法取得標案公告資料");
        }

        if (root.path("records").isEmpty()) {
            throw new BusinessException("查無標案公告資料");
        }

        return root;
    }
}
