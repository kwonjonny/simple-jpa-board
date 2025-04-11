# 📘 simple-jpa-board

Spring Boot 기반의 간단한 게시판 프로젝트입니다.  
`Spring Data JPA`와 `QueryDSL`을 활용하여 게시글 CRUD 기능을 구현하였으며,  
단위/통합 테스트 코드도 포함된 실무형 예제입니다.

---

## 🛠 기술 스택

| 분류       | 기술 |
|------------|------|
| **Backend** | Spring Boot, Spring Data JPA, QueryDSL |
| **Database** | PostgreSQL  |
| **Build Tool** | Gradle |
| **Test** | JUnit 5, Mockito, MockMvc |
| **기타** | Lombok, Spring Validation |

---

## ✨ 주요 기능

- ✅ 게시글 등록 / 조회 / 수정 / 삭제 (CRUD)
- ✅ 게시글 목록 조회 (페이징, 검색 필터 포함)
- ✅ `JPA Auditing`으로 생성일/수정일 자동 관리
- ✅ 공통 응답 포맷(`APIResponseDTO`) 적용
- ✅ `WebMvcTest` 기반의 컨트롤러 테스트 포함
