# 상품 도메인 API 명세

> **Base URL:** `/products`
> **인증 방식:** JWT Bearer Token (`Authorization: Bearer <token>`)
> **권한:** 별도 표시가 없는 경우 인증 불필요, 관리자 기능은 권한 필요 (`SUPER_ADMIN`, `OPERATION_ADMIN`)

---

## 공통 응답 형식

### 성공 응답

```json
{
  "status": 200,
  "message": "성공 메시지",
  "data": {},
  "timestamp": "2026-04-30T11:25:50"
}
```

### 에러 응답

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "에러 메시지",
  "timestamp": "2026-04-30T11:25:50"
}
```

---

## 1. 상품 등록

- **Method:** `POST`
- **URL:** `/products`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`, `OPERATION_ADMIN`

### Request Body

| 필드       | 타입     | 필수 | 설명           |
| -------- | ------ | -- | ------------ |
| name     | String | Y  | 상품명          |
| category | String | Y  | 카테고리         |
| price    | Long   | Y  | 가격 (0 이상)    |
| quantity | Long   | Y  | 재고 수량 (0 이상) |

### Response

```json
{
  "status": 201,
  "message": "상품 등록 성공",
  "data": {
    "productId": 1,
    "adminId": 1,
    "name": "상품명",
    "category": "카테고리",
    "price": 50000,
    "quantity": 100,
    "status": "FOR_SALE",
    "createdAt": "2026-04-30T11:25:49",
    "modifiedAt": "2026-04-30T11:25:49"
  },
  "timestamp": "2026-04-30T11:25:50"
}
```

### 상태코드

`201 Created`

### Error

| 상태코드 | 상황      | message      |
| ---- | ------- | ------------ |
| 400  | 필수값 누락  | 필수 입력값입니다.   |
| 400  | 값 범위 오류 | 0 이상이어야 합니다. |
| 401  | 비로그인    | 인증이 필요합니다.   |
| 403  | 권한 없음   | 권한이 없습니다.    |

---

## 2. 상품 목록 조회

- **Method:** `GET`
- **URL:** `/products`
- **Auth:** 불필요

### Query Parameters

| 파라미터     | 타입      | 필수 | 기본값 | 설명     |
| -------- | ------- | -- | --- | ------ |
| page     | Integer | N  | 0   | 페이지 번호 |
| size     | Integer | N  | 20  | 페이지 크기 |
| name     | String  | N  | -   | 상품명 검색 |
| category | String  | N  | -   | 카테고리   |
| status   | String  | N  | -   | 상태 필터  |
| sort     | String  | N  | -   | 정렬     |

### Response

```json
{
  "status": 200,
  "message": "상품 목록 조회 성공",
  "data": {
    "content": [],
    "page": 0,
    "size": 20,
    "totalElements": 0,
    "totalPages": 0
  },
  "timestamp": "2026-04-30T11:29:10"
}
```

### 상태코드

`200 OK`

---

## 3. 상품 상세 조회

- **Method:** `GET`
- **URL:** `/products/{productId}`
- **Auth:** 불필요

### Path Variable

| 파라미터      | 타입   | 설명    |
| --------- | ---- | ----- |
| productId | Long | 상품 ID |

### Response

```json
{
  "status": 200,
  "message": "상품 상세 조회 성공",
  "data": {
    "productId": 1,
    "name": "상품명",
    "category": "카테고리",
    "price": 10000,
    "quantity": 10,
    "status": "FOR_SALE",
    "createdAt": "2026-04-30T02:23:31"
  },
  "timestamp": "2026-04-30T11:29:51"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message        |
| ---- | ----- | -------------- |
| 404  | 상품 없음 | 상품을 찾을 수 없습니다. |

---

## 4. 상품 수정

- **Method:** `PUT`
- **URL:** `/products/{productId}`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`, `OPERATION_ADMIN`

### Path Variable

| 파라미터      | 타입   | 설명    |
| --------- | ---- | ----- |
| productId | Long | 상품 ID |

### Request Body

| 필드       | 타입     | 필수 | 설명   |
| -------- | ------ | -- | ---- |
| name     | String | Y  | 상품명  |
| category | String | Y  | 카테고리 |
| price    | Long   | Y  | 가격   |

### Response

```json
{
  "status": 200,
  "message": "상품 수정 성공",
  "data": {
    "productId": 1,
    "name": "수정된 상품",
    "price": 60000
  },
  "timestamp": "2026-04-30T11:30:24"
}
```

### 상태코드

`200 OK`

---

## 5. 재고 변경

- **Method:** `PATCH`
- **URL:** `/products/{productId}/quantity`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`, `OPERATION_ADMIN`

### Path Variable

| 파라미터      | 타입   | 설명    |
| --------- | ---- | ----- |
| productId | Long | 상품 ID |

### Request Body

| 필드       | 타입   | 필수 | 설명 |
| -------- | ---- | -- | -- |
| quantity | Long | Y  | 재고 |

### Response

```json
{
  "status": 200,
  "message": "재고 변경 성공",
  "data": {
    "productId": 1,
    "quantity": 50,
    "status": "FOR_SALE"
  },
  "timestamp": "2026-04-30T11:31:09"
}
```

### 상태코드

`200 OK`

---

## 6. 상품 삭제

- **Method:** `DELETE`
- **URL:** `/products/{productId}`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`, `OPERATION_ADMIN`

> Soft Delete 적용

### Path Variable

| 파라미터      | 타입   | 설명    |
| --------- | ---- | ----- |
| productId | Long | 상품 ID |

### Response

없음 (`data: null`)

### 상태코드

`200 OK`

---

## 7. 권한별 접근 정리

| API   | 인증  | 권한                           |
| ----- | --- | ---------------------------- |
| 상품 등록 | 필요  | SUPER_ADMIN, OPERATION_ADMIN |
| 상품 조회 | 불필요 | 전체                           |
| 상품 수정 | 필요  | SUPER_ADMIN, OPERATION_ADMIN |
| 재고 변경 | 필요  | SUPER_ADMIN, OPERATION_ADMIN |
| 상품 삭제 | 필요  | SUPER_ADMIN, OPERATION_ADMIN |

---

## 공통 에러

| 상태코드 | 상황     |
| ---- | ------ |
| 400  | 잘못된 요청 |
| 401  | 인증 필요  |
| 403  | 권한 없음  |
| 404  | 데이터 없음 |
| 500  | 서버 오류  |