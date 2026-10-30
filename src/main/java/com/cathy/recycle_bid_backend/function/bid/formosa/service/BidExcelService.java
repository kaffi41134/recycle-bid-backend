package com.cathy.recycle_bid_backend.function.bid.formosa.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.cathy.recycle_bid_backend.function.bid.formosa.dto.BidRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidExcelService {

    private final BidService bidService;

    public byte[] export(
            String keyword,
            LocalDate awardDateFrom,
            LocalDate awardDateTo) throws IOException {

        // 1. 共用列表查詢，取得全部符合條件的案件
        List<BidRes> bids = bidService.findAll(
                keyword, awardDateFrom, awardDateTo);

        try (
                var workbook = new XSSFWorkbook();
                var output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("台塑案件");

            // 2. 建立共用格式，同一份格式可以套用到多個儲存格
            var headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            setBorder(headerStyle);

            CellStyle textStyle = workbook.createCellStyle();
            setBorder(textStyle);

            var dataFormat = workbook.createDataFormat();

            CellStyle quantityStyle = workbook.createCellStyle();
            quantityStyle.setDataFormat(
                    dataFormat.getFormat("#,##0.######"));
            setBorder(quantityStyle);

            CellStyle amountStyle = workbook.createCellStyle();
            amountStyle.setDataFormat(
                    dataFormat.getFormat("#,##0.##"));
            setBorder(amountStyle);

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(
                    dataFormat.getFormat("yyyy-mm-dd"));
            setBorder(dateStyle);
            dateStyle.setAlignment(HorizontalAlignment.LEFT);

            // 3. 寫入第一列欄位標題
            String[] headers = {
                    "標售案號",
                    "材料編號",
                    "品名規格",
                    "數量",
                    "單位",
                    "決標金額",
                    "提貨地點",
                    "決標日"
            };

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 4. 從第二列開始寫入案件
            int rowIndex = 1;

            for (BidRes bid : bids) {
                Row row = sheet.createRow(rowIndex++);

                setText(row.createCell(0), bid.getBidNo(), textStyle);
                setText(row.createCell(1), bid.getMaterialCode(), textStyle);
                setText(row.createCell(2), bid.getItemDescription(), textStyle);

                setNumber(
                        row.createCell(3),
                        bid.getQuantity(),
                        quantityStyle);

                setText(row.createCell(4), bid.getUnit(), textStyle);

                setNumber(
                        row.createCell(5),
                        bid.getAwardAmount(),
                        amountStyle);

                setText(row.createCell(6), bid.getPickupLocation(), textStyle);

                Cell dateCell = row.createCell(7);
                // 先套樣式
                dateCell.setCellStyle(dateStyle);

                if (bid.getAwardDate() != null) {
                    dateCell.setCellValue(
                            bid.getAwardDate().atStartOfDay());

                }
            }

            // 5. 設定欄寬，並凍結標題列
            int[] columnWidths = { 14, 14, 36, 14, 10, 14, 40, 16 };

            for (int i = 0; i < columnWidths.length; i++) {
                sheet.setColumnWidth(i, columnWidths[i] * 256);
            }

            sheet.createFreezePane(0, 1);

            // 6.列印設定
            sheet.setFitToPage(true); // 啟用「縮放以符合頁面」

            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true); // 橫向
            printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
            printSetup.setFitWidth((short) 1); // 寬度縮成 1 頁
            printSetup.setFitHeight((short) 0); // 高度不限頁數（0 = 自動）

            // 每一頁都重複印第一列標題
            sheet.setRepeatingRows(CellRangeAddress.valueOf("1:1"));

            // 7. 將 Excel 寫入記憶體，回傳檔案內容
            workbook.write(output);
            return output.toByteArray();
        }
    }

    private void setText(Cell cell, String value, CellStyle style) {
        cell.setCellValue(value == null ? "" : value);
        cell.setCellStyle(style);
    }

    private void setNumber(
            Cell cell,
            BigDecimal value,
            CellStyle style) {
        // 先套樣式，null 也有框線
        cell.setCellStyle(style);

        if (value == null) {
            return;
        }

        // Excel 數字約支援 15 位有效數字。
        // 超過時改存文字，避免數量或金額被改變。
        if (value.stripTrailingZeros().precision() > 15) {
            cell.setCellValue(value.toPlainString());
            return;
        }

        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private void setBorder(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}
