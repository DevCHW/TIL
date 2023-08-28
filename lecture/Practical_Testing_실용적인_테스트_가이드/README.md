# **Intro**
본 포스팅은 박우빈님의 **[Practical Testing: 실용적인 테스트 가이드](https://www.inflearn.com/course/practical-testing-%EC%8B%A4%EC%9A%A9%EC%A0%81%EC%9D%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EA%B0%80%EC%9D%B4%EB%93%9C/dashboard)** 을 수강하면서 개인적으로 정리한 내용입니다. 참고용도로만 봐주시길 바라며, 더 자세한 내용이 궁금하신분들은 직접 강의를 수강하시길 권장드립니다.

<br>

# **Section 1. 테스트는 왜 필요할까?**

## 테스트는 귀찮다.
당연하다. 그렇다면 테스트코드는 왜 작성해야할까? 먼저, 올바른 테스트코드에 대하여 알아보자.

<br>

## **올바른 테스트코드란?**

- 자동화 테스트로 비교적 빠른 시간 안에 버그를 발견할 수 있고, 수동 테스트에 드는 비용을 크게 절약할 수 있다.

- 소프트웨어의 빠른 변화를 지원한다.

- 팀원들의 집단 지성을 팀 차원의 이익으로 승격시킨다.

- 가까이 보면 느리지만, 멀리 보면 가장 빠르다.

<br>

# Section 2. **단위테스트(Unit test)**
단위테스트란?
- **작은** 코드 단위를 **독립적**으로 검증하는 테스트(여기서 작은 이라는 뜻은 클래스 or 메소드)

- 검증 속도가 빠르고, 안정적이다.

JUnit 5
- 단위 테스트를 위한 테스트 프레임워크
- XUnit - Kent Beck
    - SUnit(Smalltalk), JUnit(Java), NUnit(.NET), ...

AssertJ
- 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
- 풍부한 API, 메서드 체이닝 지원

<br>

## **테스트 케이스 세분화 하기**

질문하기 : 암묵적이거나 아직 드러나지 않은 요구사항이 있는가?

1. 해피 케이스
2. 예외 케이스
3. 경계값 테스트
    - 범위(이상, 이하, 초과, 미만), 구간, 날짜 등



## **테스트하기 어려운 영역을 분리하기**
만약 가게 운영시간(10:00 ~ 20:00) 외에는 주문을 생성할 수 없다. 라는 요구사항이 있다면?

테스트 하기 어려운 영역
- 관측할 때마다 다른 값에 의존하는 코드
    - 현재 날짜/시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등

- 외부 세계에 영향을 주는 코드
    - 표준 출력, 메세지 발송, 데이터베이스에 기록하기 등

테스트하기 쉬운 함수
순수함수(pure functions)
- 같은 입력에는 항상 같은 결과
- 외부 세상과 단절된 형태
- 테스트하기 쉬운 코드


## **키워드 정리**

- 단위 테스트
- 수동 테스트, 자동화 테스트
- Junit5, AssertJ
- 해피케이스, 예외 케이스
- 경계값 테스트
- 테스트하기 쉬운/어려운 영역(순수 함수)

Lombok(사용 가이드)
@Data, @Setter, @AllArgsConstructor 지양
양방향 연관관계 시 @ToString 순환 참조 문제


# **Section 3. TDD**

TDD란 ?
- 프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론

**TDD 핵심 과정**

TDD는 다음의 레드, 그린, 리팩토링 이라는 3단계의 과정을 거친다.

**레드** : 실패하는 테스트를 작성함.(구현 부가 없기 때문에 무조건 실패)  
**그린** : 테스트를 통과하도록 구현부를 작성함. 최소한의 코딩. 막 코딩  
**리팩토링** : 구현 코드 개선, 테스트 통과 유지


**TDD를 적용하지 않고, 선 기능 구현 후 테스트 작성의 문제점은?**
- 테스트 자체의 누락 가능성
- 특정 테스트 케이스(예를들어 해피케이스)만 검증할 가능성
- 잘못된 구현을 다소 늦게 발견할 가능성

**그렇다면 TDD를 적용하여 선 테스트 작성 후 기능 구현을 하였을 때 얻을 수 있는 장점은?**
- 복잡도가 낮은(유연하며 유지보수가 쉬운), 테스트 가능한 코드로 구현할 수 있게 한다.
- 쉽게 발견하기 어려운 엣지(Edge) 케이스를 놓치지 않게 해준다.
- 구현에 대한 빠른 피드백을 받을 수 있다.
- 과감한 리팩토링이 가능해진다.

기존 우리는 테스트는 구현부의 검증을 위한 보조 수단으로 생각을 해왔다면, TDD에서는 테스트와 상호 작용하며 발전하는 구현부 관점으로 바라본다.

## **Section 3 키워드 정리**

- TDD
- 레드-그린-리팩토링
- 애자일(Agile) 방법론 VS .폭포수 방법론
- 익스트림 프로그래밍
- 스크럼(Scrum), 칸반(kanban)


## **애자일 소프트웨어 개발 선언**
[애자일 선언문 링크](https://agilemanifesto.org/iso/ko/manifesto.html)

우리는 소프트웨어를 개발하고, 또 다른 사람의 개발을 도와주면서 소프트웨어 개발의 더 나은 방법들을 찾아가고 있다.
이 작업을 통해 우리는 다음을 가치 있게 여기게 되었다.

공정과 도구보다 개인과 상호작용을
포괄적인 문서보다 작동하는 소프트웨어를
계약 협상보다 고객과의 협력을
계획을 따르기보다 변화에 대응하기를

가치있게 여긴다. 이 말은, 왼쪽에 있는 것들도 가치가 있지만,
우리는 오른쪽에 있는 것들에 더 높은 가치를 둔다는 것이다.


# **Section 4. 테스트는 [   ]다.**
=> 테스트는 문서다.

왜 테스트는 문서인가??
- 프로덕션 기능을 설명하는 테스트 코드
- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
- 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.

<br>

## **DisplayName을 섬세하게**
예를들어 "음료 1개 추가 테스트" 라는 DisplayName보다는,
"음료를 1개 추가할 수 있다." 라는 DisplayName이 더욱 직관적이고 이해하기 쉽다.

즉, 명사의 나열보다 **문장**으로 하는 것이 좋다.
- A이면 B이다.
- A이면 B가 아니고 C다.

더욱 발전시켜서
"음료를 1개 추가하면 주문 목록에 담긴다." 라고 작성하는것이 좋다.
테스트 행위에 대한 결과까지 기술하는것이 가장 좋음.

"특정 시간 이전에 주문을 생성하면 실패한다."
-> "영업 시작 시간 이전에는 주문을 생성할 수 없다."

**도메인 용어**를 사용하여 한층 추상화된 내용을 담기.
메서드 자체의 관점보다 도메인 정책 관점으로.

테스트의 현상을 중점으로 기술하지 말자. 위에서 보이는 "실패한다."와 같은 워딩은 피하자.

<br>

## **BDD 스타일로 작성하기**

**BDD란?**
- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장.

**Given / When / Then**
- **Given** : 시나리오 진행에 필요한 모든 준비 과정(객체, 값, 상태 등)
- **When** : 시나리오 행동 진행
- **Then** : 시나리오 진행에 대한 결과 명시, 검증

한 문장으로 풀면,  
어떤 환경에서(Given), 어떤 행동을 했을 때(When), 어떤 상태 변화가 일어난다.(Then)
-> DisplayName에 명확하게 작성할 수 있다.

<br>

## **키워드 정리**

- @DisplayName - 도메인 정책, 용어를 사용한 명확한 문장
- Given / When / Then - 주어진 환경, 행동, 상태 변화
- TDD vs. BDD

- JUnit VS. Spock

"언어가 사고를 제한한다."


# **Section 4. Spring & JPA 기반 테스트**

레이어드 아키텍처와 테스트

스프링 MVC 프레임워크는 일반적으로 다음의 3 계층으로 나누어 개발한다.
Presentation Layer -> Business Layer -> Persistence Layer

왜 이렇게 분리??
=> 관심사를 분리하기 위하여!

통합테스트
- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없다.
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트


## **Spring / JPA 훑어보기 & 기본 엔티티 설계**

Library VS. Framework
- 주체가 무엇이냐에 따라 분류할 수 있음
- 라이브러리 같은 경우 내 코드가 주체가 되고, 필요에 따라 라이브러리를 불러와서 내 코드에 적용시킬 수 있다.
- 프레임워크는 이미 구성된 환경에 맞추어 내 코드를 작성하면, 동작하게되는 방식이다.

## **Spring의 기본적인 특징**
- IoC (Inversion of Control)
- DI (Dependency Injection)
- AOP (Aspect Oriented Programming)


## **ORM(Object-Relational Mapping)**
- 객체 지향 패러다임과 관계형 DB 패러다임의 불일치를 해결하기 위해 나온 기술
- 이전에는 개발자가 객체의 데이터를 한땀한땀 매핑하여 DB에 저장 및 조회(CRUD)
- ORM을 사용함으로써 개발자는 단순 작업을 줄이고, 비즈니스 로직에 집중할 수 있다.

## **JPA(Java Persistence API)**
- Java 진영의 ORM 기술 표준
- 인터페이스이고, 여러 구현체가 있지만 보통 Hibernate를 많이 사용한다.
- 반복적인 CRUD SQL을 생성 및 실행해주고, 여러 부가 기능들을 제공한다.
- 편리하지만 쿼리를 직접 작성하지 않기 때문에, 어떤 식으로 쿼리가 만들어지고 실행되는지 명확하게 이해하고 있어야 한다.

- Spring 진영에서는 JPA를 한번 더 추상화한 Spring Data JPA 제공
- QueryDSL과 조합하여 많이 사용한다. (타입체크, 동적쿼리)

## **Persistence Layer 테스트**

**Question.**  
JpaRepository같은 걸 사용한다면 메소드명만 잘 지으면 쿼리가 예상한대로 잘 날아갈텐데,
굳이 왜 테스트코드같은 걸 작성해야 하나요?

**Answer**
1. 어떤 쿼리가 날라갈지 모른다. 
2. 미래에 어떤 형태로 변형될 지 모른다.

Repository 테스트는 단위테스트에 가깝다.
왜냐하면 DB접근로직만 갖고있기 때문.

Persistence Layer 테스트 방법
`@DataJpaTest` 어노테이션 또는 `@SpringBootTest`로 테스트 할 수 있다.

`@DataJpaTest`와 `@SpringBootTest`의 차이점?

- `@DataJpaTest` 내부 어노테이션을 확인하면 `@Transactional`과 `@AutoConfigurationTestDataBase` 어노테이션이 포함되어있다. 따라서 자동으로 설정해놓은 DB가 아닌 In-memory DB로 테스트가 실행되며, 테스트가 끝나면 롤백처리된다.

- `@SpringBootTest` 내부 어노테이션에는 `@Transactional`이 걸려져있지 않다. 그래서 테스트가 종료되어도 자동 롤백처리가 되지않으며, 따로 테스트 클래스 위에 `@Transactional`을 걸어주거나 `tearDown()` 메소드를 통하여 DB 클렌징을 따로 해주어야 한다.

<br>

## **각 레이어 별 테스트코드 설명**

**Persistence Layer**
- Data Access의 역할
- **비즈니스 가공 로직이 포함되어서는 안됨**

**Business Layer**
- 비즈니스 로직을 구현하는 역할
- Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)를 통해 비즈니스 로직을 전개
- **트랜잭션 보장**

<br>


## **테스트 코드 작성 팁 메모사항**

1. 리스트 테스트 할 때:
예문
``` java
assertThat(orderResponse.getProducts()).hasSize(3)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                    tuple("001", 1000),
                    tuple("002", 3000)
                );
```
**extracting** : 리스트에서 필요한 필드를 추출할 수 있음
**containsExactlyInAnyOrder** : 리스트에서 추출한 필드값들이 다음과 같은 값들을 포함하고있는지에 대하여 검증.

<br>

## **deleteAll()과 deleteAllInBatch() 차이점 ?**
직접 `tearDown()` 메소드를 작성하여 DB 클렌징을 할 때 발생하는 쿼리 로그를 살펴보면 쉽게 차이점을 발견할 수 있다.

`deleteAll()`로 DB 클렌징을 한다면, 단건으로 delete 쿼리를 날려서 DB를 클렌징한다.
데이터가 많으면 많을수록 클렌징을 하는데에는 속도가 느려진다.

`deleteAllInBatch()`를 사용하여 DB 클렌징을 한다면, 벌크성으로 delete 쿼리를 날려서 데이터의 row가 많아도 한번에 클렌징이 가능하다. 단, 연관관계를 고려하여 순서대로 클렌징 하는것이 중요하다!


<br>

## **@DataJpaTest와 @SpringBootTest의 차이점?**
-> @DataJpaTest는 @Test가 끝나면 롤백시켜준다.
    반면에, @SpringBootTest는 @Test어노테이션이 달린 메소드가 끝나도 자동 롤백이 되지않는다.
    따라서 @AfterEach로 tearDown() 메소드를 작성해주어야 한다.

-> 여기서 의문점,
    그러면 @SpringBootTest가 달린 테스트 클래스에 @Transactional을 달아주면 자동 롤백이 되지 않을까?
    -> 이렇게 했을 때 문제점.


<br>


## **@Valid에 대한 책임**

보통 개발을 할 때 컨트롤러에서 넘어온 `RequestDto`값을 검증하기 위해 `@Valid`를 많이 쓴다.
`@Valid`에서 지원하는 메소드들로 Dto에 어노테이션으로 필드값 검증을 달아놓는데,  

**예를들어 닉네임은 20자 이내로 작성하여야 한다**는 비즈니스가 있다면 이 검증을 과연 DTO에서 해주어야 할까?

이러한 비즈니스는 **엔티티 내부 생성자**에서 해주거나, **서비스 레이어에서 검증**을 해주는 것이 맞다고 보는 부분!

<br>

## **Dto 만들 때 꿀팁**

예를들어 Controller에서 쓰는 RequestDto를 Service 메소드에서 파라미터로 그대로 넘긴다면,
Service는 Controller에서 쓰는 Dto를 공유하기에 하위 레이어가 상위 레이어를 알고있다는 문제점이 생긴다.

따라서 Dto는 Service 레이어에서 쓰는 ServiceDto와 Controller 레이어에서 쓰는 ControllerDto를 따로 분리해서 사용하도록 하는것이 좋다.

이렇게 하였을 때 필드값 검증에 대한 부분은 Controller Dto에서만 책임지고, 서비스에서 사용하는 Dto는 POJO 형태의 Dto만 사용할 수 있다는 장점과,
하위 레이어에서 상위 레이어를 전혀 몰라도 된다는 장점이 생겨 모듈분리에도 큰 이점이 있다.

요약 : 컨트롤러에서 하위 DTO를 알고있게 해도 괜찮지만, 추후 모듈분리를 대비하여 귀찮더라도 Service 레이어에서 쓰는 DTO를 따로 만들도록 하자.


## **키워드 정리**

- Layered Architecture
     - 레이어드 아키텍처의 단점
            - JPA로 개발하는 과정을 떠올렸을 때  엔티티 객체는 너무 JPA라는 기술과 강결합이다..!
	- 이러한 기술에 종속적이다! 라는 단점을 보완하기 위하여 헥사고날 아키텍처가 나오게 됨.
- Hexagonal Architecture
- 단위 테스트 vs. 통합 테스트
- IoC, DI, AOP
- ORM, 패러다임의 불일치, Hibernate
- Spring Data JPA

- @SpringBootTest vs. @DataJpaTest
- @SpringBootTest vs. @WebMvcTest
- @Transactional(readOnly = true)
- Optimistic Lock, Pessimistic Lock
- CQRS
- @RestControllerAdvice, @ExceptionHandler
- Spring bean validation
     - @NotNull, @NotEmpty, @NotBlank, ...
- @WebMvcTest
- ObjectMapper
- Mock, Mockito, @MockBean

<br>

# **Section 5. Mockito로 Stubbing 하기**

메일 전송이라는 기능을 넣고 테스트를 하려고 한다.   
메일 전송 기능은 외부 네트웍을 사용하기 때문에 기능이 정상적으로 동작하는지 테스트하려고 한다면 메일을 실제로 보내보고 잘 전송되는지 매번 테스트하여야 할까?
매번 이렇게 메일을 실제로 보내고 확인하는 과정을 거쳐서 기능을 검증한다면 굉장히 번거로울 것이다.
또한 중간에 기능이 정상적으로 동작하지 않아도 빠르게 대응하기가 힘들다.

그렇다면 이럴때 어떻게 테스트하여야 할까?  
이럴 때는 이메일 전송하는 클래스를 Mock으로 가져와서 Stubbing 처리를 하면 된다!

**※ 참고 !!**  
이메일 전송하는 로직이 포함된 메소드에는 @Transactional 처리를 하지 않는것이 좋다.
왜냐하면 메일전송은 되게 긴 네트웍을 타는 로직인데 이 때 @Transactional 커넥션을 계속 들고있는 것이 비효율적이기 때문이다..

<br>

# **Section 6. Mock을 마주하는 자세**

## **Mocking이란?**
Controller Test를 위해선 Mocking에 대해 알고있어야 한는데, Mock은 테스트를 위해 실제 객체를 사용하는 것처럼 테스트를 위해 만든 모형으로 **가짜 객체**를 의미한다.
Mock을 이용해서 테스트하는 과정을 Mocking이라고 부르며, 웹 애플리케이션 환경에서는 Servlet Container와 Dispatcher Servlet이 메모리에 로딩되지만, Mocking을 하면 실제 테스트 컨테이너를 사용하기 때문에 Mocking을 통해 의존성을 단절시킨 상태로 테스트할 수 있다.
SpringBoot환경에서 테스트 코드를 작성할 때 @SpringBootTest + @AutoConfigureMockMvc 또는 @WebMvcTest를 사용하는데, 차이점을 알아보자


**@WebMvcTest vs. @AutoConfigureMockMvc**  
**공통점** : 컨트롤러를 테스트할 때 사용하는 어노테이션  
**차이점** : `@WebMvcTest`는 `@Controller`, `@ControllerAdvice` 등 사용이 가능하지만, `@Service`, `@Repository`에는 사용 불가하고, `@MockBean`을 사용하여 컨트롤러의 협력체들을 생성한다.
**주로 간단한 테스트에서 사용한다.**
`@AutoConfigureMockMvc`는 컨트롤러 뿐 아니라 `@Service`, `@Repository`



## **Test Double**

마틴 파울러가 정의

- **Dummy**
     - 아무 것도 하지 않는 깡통 객체

- **Fake**
     - 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체 ex) FakeRepository

- **Stub**
     - 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체
       그 외에는 응답하지 않는다.
 
- **Spy**
     - Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체. 일부는 실제 객체처럼 동작시키고 일부만 Stubbing 할 수 있다.
 
- **Mock**
     - 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

**Stub과 Mock은 다르다.**
 - Stub은 상태 검증(State Verification), Mock은 행위 검증(Behabior Verification)

<br>

## **BDDMockito**

**BDD(Behavior-Driven Development)**
- 행위 주도 개발을 말한다. 테스트 대상의 상태의 변화를 테스트하는 것이고, 시나리오를 기반으로 테스트 하는 패턴을 권장한다.
- 권장하는 기본 패턴은 Given, When, Then 구조를 가진다.

BDDMockito를 들어가면 Mockito를 상속받고 있는걸 확인할 수 있다.  
Mockito 라이브러리를 사용하면, 일반적인 Given, When, Then 시나리오로 테스트코드를 작성할 때 
Given 절 아래에 Mockito의 when() 메서드를 사용할 때가 있기에 코드를 읽을 때 부자연스럽다.
하지만 BDDMockito를 사용하면 Given 절 아래에 given() 메소드로 원하는 행동을 정의할 수 있기에 코드가독성 면에서 이점을 가져갈 수 있다.

<br>

## **Classicist VS. Mockist**

**Classicist** : Mocking을 하는 것을 최대한 줄이고 꼭 필요한 부분에만 Mocking을 하자 !라는 입장.

**Mockist** : 이미 단위테스트로 전부 기능이 정상 동작하는 것을 검증하기 때문에, 다른 클래스는 전부 Mocking 처리하자!는 입장.

정답은 없다. 테스트코드를 쭉 작성해보면서 자신이 어느 스타일에 맞는지 서서히 파악해볼 것.

<br>

## **키워드 정리**

- Test Double, Stubbing
     - dummy, fake, stub, spy, mock

- @Mock, @MockBean, @Spy, @SpyBean, @InjectMocks

- BDDMockito

- Classist VS. Mockist

<br>

# **Section 7. 더 나은 테스트를 작성하기 위한 구체적 조언**

1. **한 문단에 한 주제**
    - 테스트 메서드 내부에서 if문으로 케이스를 나누어 테스트하는 것은 바람직하지 않다. 무엇을 테스트하는것인지?에 대한 **불명확성.**    
      케이스가 여러가지 있다면 여러가지의 케이스를 나눈 테스트 메서드를 작성하는것이 바람직하다.
      한 문장의 DisplayName으로 작성할 수 있는지에 대한 체크 필요!

<br>

2. **완벽하게 제어하기**
     - 시간에 따라서 결과값이 바뀌는 메소드가 있다면 프로덕션 코드 자체내부에 LocalDateTime 변수를 두는 것이 아니라, 파라미터로 받게끔 해두고,
       테스트코드를 작성하는 것이 좋다.
     - 시간같은 경우 현재시간과 같은건 쓰지 않는것을 권장!  

<br>

3. **테스트 환경의 독립성을 보장하자.**

<br>

4. **테스트 간 독립성을 보장하자.**  
만약 테스트 A와 테스트 B가 있다고 하였을 때, A테스트 수행 다음 B 테스트 수행 시에는 성공하지만,
B 테스트를 수행한 다음 A테스트를 수행하였을 때 테스트가 깨진다면 좋지 못한 테스트 코드이다.  
테스트 코드에는 순서가 없어야 하며, A 테스트와 B 테스트가 자원을 공유하는 일은 없어야 한다.
각각 독립적으로 자원을 생성(given)해주어야 하며, A->B, B->A 모두 통과하는 테스트코드를 작성하도록 하자.

5. **한 눈에 들어오는 Test Fixture 구성하기**
     - Fixture : 고정물, 고정되어 있는 물체
     - 테스트를 위해 원하는 상태로 고정시킨 일련의 객체

6. **Test Fixture 클렌징**


7. **@ParameterizedTest**

- **CsvSource 사용**
``` java
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE, false","BOTTLE, true", "BAKERY, true"})
    @ParameterizedTest
    @Test
    void containsStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }
```

<br>

- **MethodSource 사용**
``` java
    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BAKERY, true),
                Arguments.of(ProductType.BOTTLE, true)
        );
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    @Test
    void containsStockType5(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }
```

<br>


8. **@DynamicTest 사용한 테스트**

``` java
    @DisplayName("재고 차감 시나리오")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynaicTest() {
        // given
        Stock stock = Stock.create("001", 1);

        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {
                    // given
                    int quantity = 1;

                    // when
                    stock.deductQuantity(quantity);

                    // then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감을 시도하는 경우 예외가 발생한다.", () -> {
                    // given
                    int quantity = 1;

                    // when // then
                    assertThatThrownBy(() -> stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 없습니다.");
                })
        );
    }
```

<br>

9. **테스트 수행도 비용이다. 환경 통합하기**

- 공통 환경설정을 모아놓은 상위 추상클래스를 만들고, 공통 환경을 가지고 있는 테스트 클래스들을 상속받게 만들면 된다.
    - 다음 영상 참고
    - [손너잘의 테스트코드 여행기](https://www.youtube.com/watch?v=N06UeRrWFdY)

<br>

10. **private 메서드의 테스트는 어떻게 하나요?**

- private 메서드를 검증할 필요는 없다가 결론. 만약 private 메서드를 테스트하고싶다는 욕망이 강하게 든다면
 그것은 객체 분리의 신호로 봐도 무방. 하나의 클래스를 더 많들어서 책임을 분산시키고 public하게 메서드를 만든 뒤 주입받아 사용하게 만든다!

<br>

11. **테스트에서만 필요한 메서드가 생겼는데 프로덕션 코드에서는 필요없다면?**
- 만들어도 된다. 하지만 보수적으로 접근하기!

<br>

## 키워드 정리

- 테스트 하나 당 목적은 하나!
- 완벽한 제어
- 테스트 환경의 독립성, 테스트 간 독립성
- Test Fixture
- deleteAll(), deleteAllInBatch()
- @ParameterizedTest, @DynamicTest
- 수행 환경 통합하기
- private method test
- 테스트에서만 필요한 코드


<br>

# Section 8. Appendix

## 학습 테스트
- 잘 모르는 기능, 라이브러리, 프레임워크를 학습하기 위해 작성하는 테스트
- 여러 테스트 케이스를 스스로 정의하고 검증하는 과정을 통해 보다 구체적인 동작과 기능을 학습할 수 있다.
- 관련 문서만 읽는 것보다 훨씬 재미있게 학습할 수 있다.

