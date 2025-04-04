# 로그인 및 회원가입 관련 문법 정리 (Kotlin + Spring Boot)


## 1️⃣ SHA-256 비밀번호 해싱

비밀번호는 평문으로 저장하면 보안상 매우 위험하므로, 해싱을 통해 복호화 불가능한 값으로 변환한다.

```kotlin
fun crypto(password: String): String {
    val sha = MessageDigest.getInstance("SHA-256")
    return sha.digest(password.toByteArray())
             .fold("") { acc, byte -> acc + "%02x".format(byte) }
}
```

---

- MessageDigest.getInstance("SHA-256") : 해시 알고리즘 선택
- fold("") { acc, byte -> ... } : 바이트 배열을 16진수 문자열로 변환
- 복호화가 불가능하며, 동일한 입력값에 대해 항상 동일한 출력이 나온다.

## 2️⃣ 세션 기반 로그인 처리

로그인 성공 시 사용자 정보를 HttpSession에 저장하면 이후 페이지에서도 로그인 상태를 유지할 수 있다.

```
session.setAttribute("userId", dbUser.userId)
```

- HttpSession은 서버에 사용자 정보를 저장하며, 브라우저마다 고유한 세션 ID로 구분한다.
- 이후 컨트롤러나 템플릿에서 session.getAttribute("userId")로 접근 가능하다.

---

## 3️⃣ JPA Entity 스캔 범위 설정

메인 클래스와 같은 패키지에 있을 경우 DB에 테이블이 생기지 않는 문제가 발생할 수 있다.  

이 에러는 @Entity 클래스가 JPA의 관리 범위 밖에 있을 때 발생합니다. 해결 방법은 다음과 같다.

- 메인 클래스의 위치를 com.hi 등 루트 패키지로 이동하여 하위 모든 패키지가 스캔되도록 함
- 또는 @EntityScan("com.hi.kotlin.entity")를 사용하여 명시적으로 엔티티 위치를 지정함

