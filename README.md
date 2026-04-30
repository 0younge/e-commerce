# E-commerce

## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [프로젝트 계기](#프로젝트-계기)
3. [주요기능](#주요기능)
4. [개발기간](#개발기간)
5. [기술스택](#기술스택)
6. [개발 컨벤션](#개발-컨벤션)
7. [Trouble Shooting](#trouble-shooting)
8. [ERD](#erd)
9. [API 명세](#api-명세)

---

## 프로젝트 소개

고객, 관리자, 상품, 주문, 리뷰를 통합 관리하는 이커머스 백오피스 시스템
JWT 기반 인증/인가를 적용하여 관리자 권한에 따라 기능 접근을 제어

팀 프로젝트
(팀장) 권영현, (팀원) 김현승, 배지현, 황순남, 황정후

---

## 프로젝트 계기

- 단순 CRUD를 넘어 실무형 백오피스 구조 경험
- 인증/인가(JWT + Spring Security) 구조 이해
- 공통 응답/예외 처리, Soft Delete 등 실무 규칙 적용 경험
- 도메인 간 관계 설계 (User-Order-Product-Review)

---

## 주요기능

1. **관리자 관리** - 회원가입 / 로그인 / 승인 / 거절 / 상태 변경, 역할 기반 접근 제어 (SUPER / OPERATION / CS)
2. **고객 관리** - 고객 조회 / 상세 조회 / 수정 / 상태 변경 / 삭제, 주문 수 및 총 구매 금액 집계 제공
3. **상품 관리** - 상품 등록 / 수정 / 삭제 (Soft Delete), 재고 변경 시 상태 자동 변경 (판매중 ↔ 품절), 페이징 + 검색 + 정렬
4. **주문 관리** - 주문 생성 / 조회 / 상태 변경 / 취소, 주문 상태 흐름 관리 (READY → SHIPPING → DELIVERED)
5. **리뷰 관리** - 리뷰 조회 / 상세 조회 / 삭제, 상품 상세 조회 시 리뷰 포함
6. **대시보드** - 통계 (회원, 상품, 주문, 리뷰), 매출 / 주문 상태 / 재고 현황, 차트 데이터 및 최근 주문 조회
7. **공통 시스템** - JWT 인증 + Spring Security, 공통 응답 포맷 (ApiResponse), GlobalExceptionHandler, Soft Delete, Enum 기반 상태 관리

---

## 개발기간

2026.04.23 ~ 2026.04.30

---

## 기술스택

| 분류 | 사용 기술 |
| --- | --- |
| Language | Java 17 corretto |
| Framework | Spring Boot 4.0.5, Spring MVC, Spring Data JPA, Spring Security |
| Auth | JWT |
| Database | MySQL 8.4.8 |
| Build | Gradle |
| IDE | IntelliJ IDEA Ultimate |
| 협업 | GitHub, Postman |

---

## 개발 컨벤션

### Git Flow

```
main
└── develop
    └── feature/이름-기능명
```

| 브랜치 | 역할 |
| --- | --- |
| `main` | 최종 배포용. 항상 안정된 상태 유지 |
| `develop` | 개발 통합 브랜치. 모든 기능은 여기로 병합 |
| `feature/*` | 기능 개발 브랜치 |

브랜치명 예시: `feature/영현-login`, `feature/정후-user-profile`

---

### 커밋 컨벤션

```
<타입>: <제목>
```

| 타입 | 설명 |
| --- | --- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 포맷팅 등 기능 변경 없는 수정 |
| `refactor` | 코드 리팩토링 |
| `test` | 테스트 코드 |
| `chore` | 빌드 설정, 패키지 관리 등 |
| `remove` | 파일 또는 코드 삭제 |

규칙: 50자 이내, 마침표 없음, 명령문 현재형으로 작성 (`추가한다` X → `추가` O)

---

### 네이밍 규칙

| 대상 | 규칙 | 예시 |
| --- | --- | --- |
| 변수 | camelCase | `userName`, `isLoggedIn` |
| 메서드 | camelCase, 동사로 시작 | `getUserById()` |
| 클래스 / 인터페이스 | PascalCase | `UserService` |
| 상수 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| 패키지 | 소문자 | `com.example.user` |
| Enum | PascalCase (값은 UPPER_SNAKE_CASE) | `UserRole.ADMIN` |

---

### 파일 네이밍 규칙

| 파일 종류 | 규칙 | 예시 |
| --- | --- | --- |
| 컨트롤러 | PascalCase + Controller | `UserController.java` |
| 서비스 | PascalCase + Service | `UserService.java` |
| 레포지토리 | PascalCase + Repository | `UserRepository.java` |
| 엔티티 | PascalCase (명사) | `User.java` |
| 요청 DTO | PascalCase + Request | `CreateUserRequest.java` |
| 응답 DTO | PascalCase + Response | `UserResponse.java` |
| 예외 클래스 | PascalCase + Exception | `UserNotFoundException.java` |
| 설정 클래스 | PascalCase + Config | `SecurityConfig.java` |
| 테스트 | 대상클래스명 + Test | `UserServiceTest.java` |

---

### 코드 작성 규칙

- 필드 주입(`@Autowired`) 금지 → 생성자 주입(`@RequiredArgsConstructor`) 사용
- `Optional.get()` 직접 호출 금지 → `orElseThrow()` 사용
- null 반환 지양 → `Optional` 활용
- 상태 변경 시 setter 직접 사용 금지 → 도메인 메서드 사용
- `repository.delete()` 사용 금지 → `softDelete()` 메서드 사용
- 컨트롤러에서 Repository 직접 접근 금지
- 중괄호는 항상 사용 (단일 라인 if 문도 포함)
- 클래스 멤버 선언 순서: 상수 → 필드 → 생성자 → 메서드
- 기능 단위로 줄바꿈 구분
- 주석은 왜(Why) 작성했는지 설명할 때만 사용, 코드를 그대로 설명하는 주석 금지

---

### PR 규칙

- PR 제목 형식: `[타입] 작업 내용 요약` (예: `[feat] 로그인 기능 구현`)
- 2명 이상의 팀원 승인 후 merge
- 본인이 직접 merge 금지
- PR 하나는 하나의 기능 또는 하나의 버그 수정에 집중

---

### Trouble Shooting

#### 1. 시큐리티 예외가 GlobalExceptionHandler에서 처리되지 않음

- **원인**: Security 예외는 필터 단계에서 발생하기 때문에 DispatcherServlet을 거치지 않아 GlobalExceptionHandler가 동작하지 않는다.
- **해결**: AuthenticationEntryPoint와 AccessDeniedHandler를 별도로 구현하여 Security 필터 단계에서 예외를 직접 처리한다.

#### 2. 시큐리티에서 JSON 응답이 자동 변환되지 않음

- **원인**: Security 필터 단계에서는 HttpMessageConverter를 거치지 않아 응답 객체가 자동으로 JSON으로 변환되지 않는다.
- **해결**: ObjectMapper를 사용하여 직접 JSON 직렬화 후 응답에 작성한다.

#### 3. JWT 적용 후 로그인 예외 발생

- **원인**: 기존 세션 기반 코드가 남아 있어 JWT 방식과 충돌이 발생한다.
- **해결**: 세션 관련 코드를 제거하고, 인증 정보를 SecurityContext에서 조회하도록 변경한다.

#### 4. BCrypt 적용 후 로그인 실패

- **원인**: BCrypt를 적용했지만 DB에는 기존 평문 비밀번호가 그대로 저장되어 있어 비교에 실패한다.
- **해결**: data.sql에서 비밀번호를 BCrypt로 해시된 값으로 변경한다.

#### 5. ddl-auto=create 설정인데 DB 초기화 실패

- **원인**: 외래키 제약 조건으로 인해 테이블 DROP이 실패한다.
- **해결**: DB를 직접 삭제한 후 재생성한다.

#### 6. 세션 vs JWT 프로필 수정 처리 혼란

- **원인**: 세션은 서버에 상태를 저장하는 방식이고, JWT는 무상태(stateless) 구조이기 때문에 프로필 수정 후 처리 방식이 다르다.
- **해결**: 세션 방식은 setAttribute로 세션 값을 갱신하고, JWT 방식은 DB 값만 수정한다.


#### 7. 블랙리스트 토큰 요청 시 메시지가 출력되지 않음

- **원인**: 필터에서 response.setStatus만 설정하고 흐름이 종료되어 응답 메시지가 전달되지 않는다.
- **해결**: AuthenticationEntryPoint를 직접 호출하여 응답 메시지를 처리한다.

#### 8. 세션 vs JWT 로그아웃 처리 방식 차이

- **원인**: 세션은 서버 상태 기반이라 서버에서 세션을 제거하면 로그아웃이 되지만, JWT는 무상태 토큰 기반이라 서버에서 토큰을 직접 무효화할 수 없다.
- **해결**: 세션 방식은 invalidate()로 세션을 제거하고, JWT 방식은 블랙리스트에 토큰을 등록하고 Access Token 유효시간을 짧게 유지하는 전략을 적용한다.

#### 9. 시큐리티 적용 후 `/error`가 401로 응답되는 문제

- **원인**: JSON 파싱 오류 발생 시 스프링이 내부적으로 `/error` 엔드포인트로 포워딩하는데, Security 설정에서 `/error`가 차단되어 있어 401 응답이 반환된다. 원래 오류 내용 대신 인증 실패 응답만 전달된다.
- **해결**: Security 설정에서 `/error` 경로에 permitAll()을 추가한다.

#### 10. Enum 변환 실패로 엔티티 예외가 동작하지 않는 문제

- **원인**: Jackson이 요청 데이터를 Enum으로 변환하는 단계에서 실패하면 Controller 진입 전에 예외가 발생하여 비즈니스 로직이 실행되지 않는다.
- **해결**: GlobalExceptionHandler에서 JSON 파싱 예외를 처리하거나, DTO에서 Enum 대신 String으로 받아 내부에서 직접 변환 및 검증한다.

#### 11. 기존 비즈니스 로직을 인지하지 못하고 중복 로직 작성

- **원인**: 기존에 구현된 비즈니스 로직을 파악하지 못한 채 Repository를 직접 호출하는 중복 로직을 작성하여 코드가 과도하게 파편화되었다.
- **해결**: 기존 비즈니스 로직을 재사용할 수 있도록 코드를 리팩토링하고, 팀원들의 로직 패턴을 학습한다.

#### 12. 초기 JWT 적용 시 기존 로직과 충돌로 보안 기능이 정상 작동하지 않음

- **원인**: 보안을 고려하지 않은 초기 설계로 인해 JWT를 적용했을 때 기존 로직과 부분적으로 불일치가 발생한다.
- **해결**: JWT 적용 방법을 학습한 후 기존 로직을 수정하여 재적용한다.

#### 13. 리뷰가 없는 상품의 평균 평점 계산 중 예외 발생

- **원인**: `DoubleStream.average()`는 `double`이 아닌 `OptionalDouble`을 반환한다. 스트림 요소가 0개이면 비어있는 `OptionalDouble`이 반환되는데, 이를 처리하지 않고 바로 사용하면 `NoSuchElementException`이 발생한다.
- **해결**: `orElse(0.0)`을 사용하여 리뷰가 0개인 경우 평균을 0.0으로 처리한다.

```java
// 변경 전
double avg = reviews.stream()
    .mapToDouble(Review::getRating)
    .average();

// 변경 후
double avg = reviews.stream()
    .mapToDouble(Review::getRating)
    .average()
    .orElse(0.0);
```

#### 14. 정렬 방향에 소문자 입력 시 IllegalArgumentException 발생

- **원인**: `Sort.Direction.valueOf()`는 "ASC", "DESC"와 같이 대문자만 인식한다.
- **해결**: `toUpperCase()`를 추가하여 대소문자 구분 없이 처리한다.

```java
// 변경 전
PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder), sortBy);

// 변경 후
PageRequest.of(page - 1, size, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy);
```

#### 15. 조건별 조회 시 쿼리 수 증가로 인한 비효율

- **원인**: 조건별로 Repository를 반복 호출하거나 최근 주문을 스트림으로 처리하면 주문 목록이 많을 경우 비효율적이고, 여러 Repository를 반복 사용하면 쿼리 수가 증가하여 DB 부하가 늘어난다.
- **해결 방향**: 관련 조건을 하나의 쿼리로 작성하여 처리한다. (관련 개념 부족으로 해당 프로젝트 내 구현에는 어려움이 있었음)

#### 16. 본인 담당 도메인 외 코드 파악 어려움

- **원인**: 팀 프로젝트 특성상 본인이 담당하지 않은 도메인의 코드를 충분히 이해하지 못한 채 진행되었다.
- **해결**: 코드 발표 시간을 통해 팀원 간 서로의 코드를 공유하고 궁금증을 해결한다.

#### 17. Enum 변환 오류가 401로 응답되는 문제

- **문제 상황**: 주문 상태(OrderStatus)를 변경하는 API에서 잘못된 값을 요청했을 때, 400 Bad Request가 아닌 401 Unauthorized가 반환되었다. 비즈니스 예외가 아니라 인증 에러로 처리되는 문제였다.
- **원인**: 잘못된 Enum 값이 들어오면 Jackson이 JSON을 Java 객체로 역직렬화하는 과정에서 `HttpMessageNotReadableException`이 발생한다. 이 예외는 Spring 내부의 `DefaultHandlerExceptionResolver`가 먼저 처리하기 때문에 직접 구현한 `GlobalExceptionHandler`까지 도달하지 못한다. 이후 `/error` 경로로 포워딩되는데, Spring Security 필터가 `/error`를 차단하고 있어 최종적으로 401이 반환된다.

  예외 처리 흐름 요약:
    1. JSON 파싱 실패 (`HttpMessageNotReadableException` 발생)
    2. `DefaultHandlerExceptionResolver` 동작 → `GlobalExceptionHandler` 도달 불가
    3. `/error` 경로로 포워딩
    4. Security 필터가 `/error` 차단
    5. 401 Unauthorized 반환

- **해결**:

  **방법 1. `@JsonCreator`로 Enum 변환을 직접 제어**

  Enum 클래스 내부에 `@JsonCreator`를 사용하여 Jackson의 역직렬화 과정을 직접 제어한다. 잘못된 값이 들어오면 `HttpMessageNotReadableException` 대신 직접 정의한 `InvalidRequestException`을 발생시켜 `GlobalExceptionHandler`에서 정상 처리되도록 한다.

  ```java
  @JsonCreator
  public static OrderStatus from(String value) {
      return Arrays.stream(values())
          .filter(v -> v.name().equalsIgnoreCase(value))
          .findFirst()
          .orElseThrow(() -> new InvalidRequestException("잘못된 상태값입니다."));
  }
  ```

  **방법 2. Security에서 `/error` 경로 허용**

  `/error`로 포워딩되는 요청은 DispatcherType이 다르기 때문에 명시적으로 허용해야 한다.

  ```java
  .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
  ```

- **추가 검토 방안 - Converter 구현 방식**:

  Spring의 `Converter`를 사용하면 요청 값(String)을 원하는 타입(Enum 등)으로 직접 변환할 수 있다. `@RequestParam`, `@PathVariable`, `@ModelAttribute` 기반 요청에는 효과적이지만, `@RequestBody`(JSON) 요청의 역직렬화는 Jackson이 담당하기 때문에 이 방법은 적용되지 않는다. 이번 문제처럼 JSON 요청에서 발생한 Enum 변환 오류에는 직접적인 해결책이 아니다.

- **최종 결론**: `@JsonCreator`를 사용하여 Enum 변환 책임을 명확히 분리하고, Security 흐름에 불필요하게 개입되지 않도록 처리하는 것이 가장 적합한 방법이다.

  | 상황 | 변경 전 | 변경 후 |
    |---|---|---|
  | 잘못된 Enum 값 요청 | 401 Unauthorized | 400 Bad Request |
  | 예외 처리 주체 | Security 필터 | GlobalExceptionHandler |

---

## ERD
![Ecommerce ERD.png](docs/Ecommerce%20ERD.png)
---

## API 명세

### 공통 규칙

- 공통 응답 구조 (status, message, data, timestamp)
- 공통 예외 처리 (GlobalExceptionHandler)
- Soft Delete 적용

### 도메인별 API

1. 관리자 API - 회원가입 / 로그인 / 승인 / 상태관리 [AdminAPI.md](docs/AdminAPI.md)
2. 고객 API - 고객 조회 / 상세 / 수정 / 상태 변경 [UserAPI.md](docs/UserAPI.md)
3. 상품 API - 상품 등록 / 조회 / 수정 / 재고 관리 / 삭제 [ProductAPI.md](docs/ProductAPI.md)
4. 주문 API - 주문 생성 / 조회 / 상태 변경 / 취소 [OrderAPI.md](docs/OrderAPI.md)
5. 리뷰 API - 리뷰 조회 / 상세 / 삭제 [ReviewAPI.md](docs/ReviewAPI.md)
6. 대시보드 API - 통계 / 위젯 / 차트 / 최근 주문 [DashboardAPI.md](docs/DashboardAPI.md)
7. 헬스 체크 API - 서버 상태 확인 [HealthAPI.md](docs/HealthAPI.md)