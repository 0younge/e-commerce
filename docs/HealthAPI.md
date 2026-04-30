# 헬스체크 API 명세

> **Base URL:** `/health`
> **인증 방식:** 불필요 (Public API)
> **권한:** 누구나 접근 가능

---

## 1. 헬스 체크

- **Method:** `GET`
- **URL:** `/health`
- **Auth:** 불필요

### Request

없음

### Response

```
HTTP/1.1 200 OK
```

### 상태코드

`200 OK`

### Error

| 상태코드 | 상황    | message        |
| ---- | ----- | -------------- |
| 500  | 서버 장애 | 서버 오류가 발생했습니다. |