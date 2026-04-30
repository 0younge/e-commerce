# 리뷰 도메인 API 명세

> **Base URL:** `/reviews`
> **인증 방식:** JWT Bearer Token (`Authorization: Bearer <token>`)
> **권한:** 별도 표시가 없는 경우 인증 필요, 관리자 권한에서만 삭제 가능

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

### 페이지네이션 응답

```json
{
  "status": 200,
  "message": "성공 메시지",
  "data": {
    "content": [],
    "page": 1,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
  },
  "timestamp": "2026-04-26T14:44:38"
}
```

---

## 1. 리뷰 목록 조회

- **Method:** `GET`
- **URL:** `/reviews`
- **Auth:** JWT 필요

### Query Parameters

| 파라미터      | 타입      | 필수 | 기본값        | 설명                            |
| --------- | ------- | -- | ---------- | ----------------------------- |
| keyword   | String  | N  | -          | 유저명, 상품명                      |
| page      | Integer | N  | 1          | 페이지 번호                        |
| size      | Integer | N  | 10         | 페이지 크기                        |
| sortBy    | String  | N  | `createdAt` | 정렬 기준 (`rating`, `createdAt`) |
| sortOrder | String  | N  | `desc`     | 정렬 순서 (`asc`, `desc`)         |
| rating    | Integer | N  | -          | 평점 필터 (`1~5`)                 |

### Response

```json
{
  "status": 200,
  "message": "리뷰 목록 조회 성공",
  "data": {
    "content": [
      {
        "reviewId": 1,
        "orderNumber": "ORD-20240101-001",
        "userName": "홍길동",
        "productName": "상품명",
        "rating": 5,
        "content": "리뷰 내용",
        "createdAt": "2024-01-01T00:00:00"
      }
    ],
    "page": 1,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황     | message              |
| ---- | ------ | -------------------- |
| 400  | 정렬값 오류 | 유효하지 않은 정렬 순서입니다.    |
| 400  | 페이지 오류 | 페이지 번호는 1 이상이어야 합니다. |
| 401  | 비로그인   | 로그인이 필요한 작업입니다.      |

---

## 2. 리뷰 상세 조회

- **Method:** `GET`
- **URL:** `/reviews/{reviewId}`
- **Auth:** JWT 필요

### Path Variable

| 파라미터     | 타입   | 설명    |
| -------- | ---- | ----- |
| reviewId | Long | 리뷰 ID |

### Response

```json
{
  "status": 200,
  "message": "리뷰 상세 조회 성공",
  "data": {
    "productName": "상품명",
    "userName": "홍길동",
    "userEmail": "hong@example.com",
    "rating": 5,
    "content": "리뷰 내용",
    "createdAt": "2024-01-01T00:00:00"
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message         |
| ---- | ----- | --------------- |
| 401  | 비로그인  | 로그인이 필요한 작업입니다. |
| 404  | 리뷰 없음 | 존재하지 않는 리뷰입니다.  |

---

## 3. 리뷰 삭제

- **Method:** `DELETE`
- **URL:** `/reviews/{reviewId}`
- **Auth:** JWT 필요
- **Role:** `SUPER_ADMIN`, `OPERATION_ADMIN`

> Soft Delete 적용

### Path Variable

| 파라미터     | 타입   | 설명    |
| -------- | ---- | ----- |
| reviewId | Long | 리뷰 ID |

### Response

없음 (`data: null`)

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message         |
| ---- | ----- | --------------- |
| 401  | 비로그인  | 로그인이 필요한 작업입니다. |
| 403  | 권한 없음 | 접근이 거부되었습니다.    |
| 404  | 리뷰 없음 | 존재하지 않는 리뷰입니다.  |

---

## 공통 에러

| 상태코드 | 상황     |
| ---- | ------ |
| 400  | 잘못된 요청 |
| 401  | 인증 필요  |
| 403  | 권한 없음  |
| 404  | 데이터 없음 |
| 500  | 서버 오류  |