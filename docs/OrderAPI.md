# 주문 도메인 API 명세

> **Base URL:** `/orders`
> **인증 방식:** JWT Bearer Token (`Authorization: Bearer <token>`)
> **권한:** 모든 API는 인증 필요, 관리자 권한 기반 접근 제어

---

## 공통 응답 형식

### 성공 응답

```json
{
  "status": 200,
  "message": "성공 메시지",
  "data": {},
  "timestamp": "2026-04-29T19:56:32"
}
```

### 에러 응답

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "에러 메시지",
  "timestamp": "2026-04-29T19:56:32"
}
```

---

## 1. 주문 생성

- **Method:** `POST`
- **URL:** `/orders`
- **Auth:** JWT 필요

### Request Body

| 필드        | 타입      | 필수 | 설명    |
| --------- | ------- | -- | ----- |
| userId    | Long    | Y  | 고객 ID |
| productId | Long    | Y  | 상품 ID |
| quantity  | Integer | Y  | 주문 수량 |

### Response

```json
{
  "status": 201,
  "message": "주문 생성 성공",
  "data": {
    "orderId": 1,
    "number": "20260429_1_1",
    "userId": 1,
    "productId": 1,
    "quantity": 2,
    "totalPrice": 2000000,
    "status": "READY",
    "createdAt": "2026-04-29T19:56:32"
  },
  "timestamp": "2026-04-29T19:56:32"
}
```

### 상태코드

`201 Created`

### Error

| 상태코드 | 상황       | message            |
| ---- | -------- | ------------------ |
| 400  | 수량 오류    | 최소 주문 수량은 1 이상입니다. |
| 400  | 재고 부족    | 재고가 부족합니다.         |
| 400  | 상품 상태 오류 | 주문 불가능한 상품입니다.     |
| 404  | 유저 없음    | 사용자를 찾을 수 없습니다.    |
| 404  | 상품 없음    | 상품을 찾을 수 없습니다.     |

---

## 2. 주문 목록 조회

- **Method:** `GET`
- **URL:** `/orders`
- **Auth:** JWT 필요

### Query Parameters

| 파라미터      | 타입      | 필수 | 기본값        | 설명        |
| --------- | ------- | -- | ---------- | --------- |
| keyword   | String  | N  | -          | 주문번호, 고객명 |
| page      | Integer | N  | 1          | 페이지 번호    |
| size      | Integer | N  | 10         | 페이지 크기    |
| sort      | String  | N  | createdAt  | 정렬 기준     |
| direction | String  | N  | desc       | 정렬 순서     |
| status    | String  | N  | -          | 상태 필터     |

### Response

```json
{
  "status": 200,
  "message": "주문 목록 조회 성공",
  "data": {
    "content": [],
    "page": 1,
    "size": 10,
    "totalElements": 0,
    "totalPages": 0
  },
  "timestamp": "2026-04-29T20:10:29"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message         |
| ---- | ----- | --------------- |
| 401  | 비로그인  | 로그인이 필요한 작업입니다. |
| 403  | 권한 없음 | 접근이 거부되었습니다.    |

---

## 3. 주문 상세 조회

- **Method:** `GET`
- **URL:** `/orders/{orderId}`
- **Auth:** JWT 필요

### Path Variable

| 파라미터    | 타입   | 설명    |
| ------- | ---- | ----- |
| orderId | Long | 주문 ID |

### Response

```json
{
  "status": 200,
  "message": "주문 상세 조회 성공",
  "data": {
    "number": "ORD-001",
    "userName": "홍길동",
    "productName": "노트북",
    "quantity": 1,
    "totalPrice": 1000000,
    "status": "DELIVERED",
    "createdAt": "2026-04-30T10:45:56"
  },
  "timestamp": "2026-04-30T10:50:08"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message         |
| ---- | ----- | --------------- |
| 401  | 비로그인  | 로그인이 필요한 작업입니다. |
| 403  | 권한 없음 | 접근이 거부되었습니다.    |
| 404  | 주문 없음 | 주문을 찾을 수 없습니다.  |

---

## 4. 주문 상태 변경

- **Method:** `PATCH`
- **URL:** `/orders/{orderId}`
- **Auth:** JWT 필요

### Path Variable

| 파라미터    | 타입   | 설명    |
| ------- | ---- | ----- |
| orderId | Long | 주문 ID |

### Request Body

| 필드     | 타입     | 필수 | 설명                                                   |
| ------ | ------ | -- | ---------------------------------------------------- |
| status | String | Y  | 주문 상태 (`READY`, `SHIPPING`, `DELIVERED`, `CANCELED`) |

### Response

없음 (`data: null`)

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황     | message        |
| ---- | ------ | -------------- |
| 400  | 상태값 오류 | 잘못된 상태값입니다.    |
| 404  | 주문 없음  | 주문을 찾을 수 없습니다. |

---

## 5. 주문 취소

- **Method:** `PATCH`
- **URL:** `/orders/{orderId}/cancel`
- **Auth:** JWT 필요

### Path Variable

| 파라미터    | 타입   | 설명    |
| ------- | ---- | ----- |
| orderId | Long | 주문 ID |

### Request Body

| 필드           | 타입     | 필수 | 설명    |
| ------------ | ------ | -- | ----- |
| cancelReason | String | Y  | 취소 사유 |

### Response

없음 (`data: null`)

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황       | message             |
| ---- | -------- | ------------------- |
| 400  | 취소 사유 없음 | 취소 사유는 필수입니다.       |
| 400  | 상태 제한    | 준비중 상태에서만 취소 가능합니다. |
| 404  | 주문 없음    | 주문을 찾을 수 없습니다.      |

---

## 공통 에러

| 상태코드 | 상황     |
| ---- | ------ |
| 400  | 잘못된 요청 |
| 401  | 인증 필요  |
| 403  | 권한 없음  |
| 404  | 데이터 없음 |
| 500  | 서버 오류  |