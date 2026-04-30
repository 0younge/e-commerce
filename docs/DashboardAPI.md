# 대시보드 도메인 API 명세

> **Base URL:** `/dashboard`
> **인증 방식:** JWT Bearer Token (`Authorization: Bearer <token>`)
> **권한:** 모든 API는 `SUPER_ADMIN` 권한 + `ACTIVE` 상태 필요

---

## 공통 응답 형식

### 성공 응답

```json
{
  "status": 200,
  "message": "성공 메시지",
  "data": {},
  "timestamp": "2026-04-26T14:44:38"
}
```

### 에러 응답

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "에러 메시지",
  "timestamp": "2026-04-26T14:44:38"
}
```

---

## 공통 에러

| 상태코드 | 상황      | message                |
| ---- | ------- | ---------------------- |
| 401  | 비로그인    | 로그인이 필요한 작업입니다.        |
| 403  | 권한 없음   | Access Denied          |
| 403  | 비활성 관리자 | 활성 상태 관리자만 접근할 수 있습니다. |
| 404  | 관리자 없음  | 존재하지 않는 유저입니다.         |

---

## 1. Summary 통계 조회

- **Method:** `GET`
- **URL:** `/dashboard/summary`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`

### Response

```json
{
  "status": 200,
  "message": "Summary 통계 조회 성공",
  "data": {
    "totalAdminCount": 10,
    "activeAdminCount": 8,
    "totalUserCount": 3000,
    "activeUserCount": 2500,
    "totalProductCount": 500,
    "lowStockProductCount": 12,
    "totalOrderCount": 10000,
    "todayOrderCount": 45,
    "totalReviewCount": 8000,
    "averageRating": 4.3
  },
  "timestamp": "2026-04-26T14:44:38"
}
```

### 상태코드

`200 OK`

---

## 2. Widgets 데이터 조회

- **Method:** `GET`
- **URL:** `/dashboard/widgets`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`

### Response

```json
{
  "status": 200,
  "message": "Widgets 데이터 조회 성공",
  "data": {
    "totalRevenue": 150000000,
    "todayRevenue": 3200000,
    "readyOrderCount": 120,
    "shippingOrderCount": 85,
    "deliveredOrderCount": 9500,
    "lowStockProductCount": 12,
    "outOfStockProductCount": 3
  },
  "timestamp": "2026-04-26T14:44:38"
}
```

### 상태코드

`200 OK`

---

## 3. Charts 데이터 조회

- **Method:** `GET`
- **URL:** `/dashboard/charts`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`

### Response

```json
{
  "status": 200,
  "message": "Charts 데이터 조회 성공",
  "data": {
    "rating1Count": 30,
    "rating2Count": 50,
    "rating3Count": 200,
    "rating4Count": 800,
    "rating5Count": 1500,
    "activeUserCount": 2500,
    "inactiveUserCount": 400,
    "suspendedUserCount": 100,
    "productCountByCategory": [
      { "category": "전자기기", "count": 120 }
    ]
  },
  "timestamp": "2026-04-26T14:44:38"
}
```

### 상태코드

`200 OK`

---

## 4. 최근 주문 조회

- **Method:** `GET`
- **URL:** `/dashboard/recent-orders`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`

### Response

```json
{
  "status": 200,
  "message": "최근 주문 조회 성공",
  "data": {
    "orders": [
      {
        "orderId": 1001,
        "userName": "홍길동",
        "totalPrice": 59000,
        "status": "SHIPPING",
        "createdAt": "2026-04-26T13:22:10"
      }
    ]
  },
  "timestamp": "2026-04-26T14:44:38"
}
```

### 상태코드

`200 OK`

---

## 부록: 열거형 값 정의

### OrderStatus

| 값         | 설명    |
| --------- | ----- |
| READY     | 주문 준비 중 |
| SHIPPING  | 배송 중  |
| DELIVERED | 배송 완료 |

### UserStatus

| 값         | 설명  |
| --------- | --- |
| ACTIVE    | 활성  |
| INACTIVE  | 비활성 |
| SUSPENDED | 정지  |

### AdminStatus

| 값         | 설명   |
| --------- | ---- |
| ACTIVE    | 활성   |
| INACTIVE  | 비활성  |
| SUSPENDED | 정지   |
| PENDING   | 승인대기 |
| REJECTED  | 거부   |