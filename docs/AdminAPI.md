## 1. 고객 목록 조회

### 📍 고객 목록 조회

* **Method:** `GET`
* **URL:** `/users`
* **Auth:** JWT 필요

### Query Parameters

| 파라미터      | 타입      | 필수 | 기본값         | 설명               |            |              |
| --------- | ------- | -- | ----------- | ---------------- | ---------- | ------------ |
| keyword   | String  | N  | -           | 검색 키워드 (이름, 이메일) |            |              |
| page      | Integer | N  | 1           | 페이지 번호           |            |              |
| size      | Integer | N  | 10          | 페이지당 항목 수        |            |              |
| sortBy    | String  | N  | `createdAt` | 정렬 기준 (`name`    | `email`    | `createdAt`) |
| sortOrder | String  | N  | `desc`      | 정렬 순서 (`asc`     | `desc`)    |              |
| status    | String  | N  | -           | 상태 필터 (`ACTIVE`  | `INACTIVE` | `SUSPENDED`) |

### Response

```json
{
  "status": 200,
  "message": "고객 목록 조회 성공",
  "data": {
    "content": [
      {
        "userId": 1,
        "name": "홍길동",
        "email": "hong@example.com",
        "phoneNumber": "010-1234-5678",
        "status": "ACTIVE",
        "orderCount": 3,
        "totalPrice": 150000,
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

| 상태코드 | 상황          | message              |
| ---- | ----------- | -------------------- |
| 400  | 유효하지 않은 정렬값 | 유효하지 않은 정렬 값입니다.     |
| 400  | 페이지 번호 오류   | 페이지 번호는 1 이상이어야 합니다. |
| 401  | 비로그인        | 로그인이 필요한 작업입니다.      |

---

## 2. 고객 상세 조회

### 📍 고객 상세 조회

* **Method:** `GET`
* **URL:** `/users/{userId}`
* **Auth:** JWT 필요

### Path Variable

| 파라미터   | 타입   | 설명    |
| ------ | ---- | ----- |
| userId | Long | 고객 ID |

### Response

```json
{
  "status": 200,
  "message": "고객 상세 조회 성공",
  "data": {
    "name": "홍길동",
    "email": "hong@example.com",
    "phoneNumber": "010-1234-5678",
    "status": "ACTIVE",
    "orderCount": 3,
    "totalPrice": 150000,
    "createdAt": "2024-01-01T00:00:00"
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황         | message         |
| ---- | ---------- | --------------- |
| 401  | 비로그인       | 로그인이 필요한 작업입니다. |
| 404  | 존재하지 않는 고객 | 존재하지 않는 고객입니다.  |

---

## 3. 고객 정보 수정

### 📍 고객 정보 수정

* **Method:** `PATCH`
* **URL:** `/users/{userId}`
* **Auth:** JWT 필요

### Path Variable

| 파라미터   | 타입   | 설명    |
| ------ | ---- | ----- |
| userId | Long | 고객 ID |

### Request Body

| 필드          | 타입     | 필수 | 설명                     |
| ----------- | ------ | -- | ---------------------- |
| name        | String | N  | 이름                     |
| email       | String | N  | 이메일                    |
| phoneNumber | String | N  | 전화번호 (`010-XXXX-XXXX`) |

### Response

```json
{
  "status": 200,
  "message": "고객 정보 수정 성공",
  "data": {
    "userId": 1,
    "name": "홍길동",
    "email": "hong@example.com",
    "phoneNumber": "010-1234-5678",
    "status": "ACTIVE",
    "createdAt": "2024-01-01T00:00:00",
    "modifiedAt": "2024-06-01T12:00:00"
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황         | message             |
| ---- | ---------- | ------------------- |
| 400  | 이메일 형식 오류  | 올바른 이메일 형식이 아닙니다.   |
| 400  | 전화번호 형식 오류 | 전화번호 형식이 올바르지 않습니다. |
| 409  | 이메일 중복     | 이미 사용 중인 이메일입니다.    |
| 401  | 비로그인       | 로그인이 필요한 작업입니다.     |
| 404  | 존재하지 않는 고객 | 존재하지 않는 고객입니다.      |

---

## 4. 고객 상태 변경

### 📍 고객 상태 변경

* **Method:** `PATCH`
* **URL:** `/users/{userId}/status`
* **Auth:** JWT 필요

### Path Variable

| 파라미터   | 타입   | 설명    |
| ------ | ---- | ----- |
| userId | Long | 고객 ID |

### Request Body

| 필드     | 타입     | 필수 | 설명               |            |              |
| ------ | ------ | -- | ---------------- | ---------- | ------------ |
| status | String | Y  | 변경할 상태 (`ACTIVE` | `INACTIVE` | `SUSPENDED`) |

### Response

```json
{
  "status": 200,
  "message": "고객 상태 변경 성공",
  "data": {
    "userId": 1,
    "status": "INACTIVE"
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황          | message         |
| ---- | ----------- | --------------- |
| 400  | 유효하지 않은 상태값 | 유효하지 않은 상태값입니다. |
| 401  | 비로그인        | 로그인이 필요한 작업입니다. |
| 404  | 존재하지 않는 고객  | 존재하지 않는 고객입니다.  |

---

## 5. 고객 삭제

### 📍 고객 삭제

* **Method:** `DELETE`
* **URL:** `/users/{userId}`
* **Auth:** JWT 필요

> ℹ️ Soft Delete 처리됩니다.

### Path Variable

| 파라미터   | 타입   | 설명    |
| ------ | ---- | ----- |
| userId | Long | 고객 ID |

### Response

없음 (`data: null`)

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황         | message         |
| ---- | ---------- | --------------- |
| 401  | 비로그인       | 로그인이 필요한 작업입니다. |
| 403  | 권한 없음      | 접근이 거부되었습니다.    |
| 404  | 존재하지 않는 고객 | 존재하지 않는 고객입니다.  |

---

## 부록: 열거형 값 정의

### UserStatus

| 값           | 설명  |
| ----------- | --- |
| `ACTIVE`    | 활성  |
| `INACTIVE`  | 비활성 |
| `SUSPENDED` | 정지  |

</details>

<details>
<summary>📌 <b>상품 API</b> </summary>

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
````

### 에러 응답

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "에러 메시지",
  "timestamp": "2026-04-30T11:25:50"
}
```