# Recycle Bid Backend

資源回收投標系統後端專案。

## 技術

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## 功能

目前預計包含：

- 回收品項 Material CRUD
- 投標資料管理
- 投標紀錄查詢

## 啟動方式

```bash
./mvnw spring-boot:run
```

## Bid API

| 方法 | 路徑 | 功能 | 成功狀態碼 |
| --- | --- | --- | --- |
| GET | `/api/bids` | 查詢全部 | 200 |
| GET | `/api/bids/{id}` | 查詢單筆 | 200 |
| POST | `/api/bids` | 新增 | 201 |
| PUT | `/api/bids/{id}` | 更新整筆資料 | 200 |
| DELETE | `/api/bids/{id}` | 刪除 | 204 |

新增與更新使用相同的 JSON 格式：

```json
{
  "bidNo": "BID-001",
  "contactName": "王小明",
  "contactPhone": "0912345678",
  "materialCode": "MAT-001",
  "itemDescription": "廢鐵 10mm",
  "quantity": 12.345678,
  "unit": "KG",
  "pickupLocation": "台北倉庫",
  "quotationNoticeDate": "2026-10-18",
  "quotationDeadlineDate": "2026-10-20",
  "environmentalCode": "R-1301",
  "referenceStorageLocation": "A-01"
}
```

`bidNo` 由使用者輸入且為必填，日期格式為 `yyyy-MM-dd`；報價截止日不得早於報價通知日。PUT 會以傳入內容覆寫欄位，未傳入的選填欄位會清空。回應另包含系統產生的 `id` 與 `createdAt`。

新增成功會回傳 `Location: /api/bids/{id}`。驗證失敗回傳 400；查詢、更新或刪除不存在的案件時，沿用既有商業錯誤格式，回傳 422 與「找不到此案件」。
