# Service Layer에 대한 사실과 오해
이 글의 내용은 사이드 프로젝트를 개발할 때 평소에 많이 고민했던 문제가 있었으나, 명확한 해결책을 찾지 못하여 답답하던 와중에 재미니님의 [지속 성장 가능한 소프트웨어를 만들어가는 방법](https://geminikim.medium.com/%EC%A7%80%EC%86%8D-%EC%84%B1%EC%9E%A5-%EA%B0%80%EB%8A%A5%ED%95%9C-%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EA%B0%80%EB%8A%94-%EB%B0%A9%EB%B2%95-97844c5dab63) 를 읽고나서 감명을 받게 되어 작성하게 되었습니다.

<br>

# Intro
스프링 프레임워크로 `Controller` -> `Service` -> `Repository`의 3계층으로 애플리케이션을 개발하던 중, 애플리케이션이 점점 확장되면 `Service`계층에서 하는 일이 너무 많아질 때가 있습니다. 간단한 예시를 들면 다음과 같습니다.

회원 서비스를 개발하려고 합니다. 먼저 `MemberController`, `MemberService`, `MemberRepository`의 클래스를 만들고. 서비스의 초기상태이기 때문에 간단한 CRUD 기능을 수행하는 로직들을 넣어줍니다. 


**MemberController**
```java
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 간단한 로직들...
}
```

**MemberService**
```java
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 간단한 로직들...
}
```

**MemberRepository**
```java
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // 간단한 로직들...
}
```

초기에는 이렇게 간단한 로직들 밖에 없기에 `MemberController` -> `MemberService` -> `MemberRepository`의 형태를 어기는 일이 발생하지 않습니다.


# 요구사항 추가
서비스가 거대해지고, 점점 회원과 관련된 기능을 추가적으로 개발해야하는 상황이 생겼습니다. 가장 먼저 회원이 비밀번호를 까먹었을 경우에 회원의 이메일로 임시 비밀번호를 전송해주는 서비스를 개발하여야 한다고 가정해봅시다.

위의 기능을 개발하기 위하여 `MemberService`에 다음과 같은 로직을 넣어줍니다.

**MemberService**
```java
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 이메일로 임시비밀번호를 전송하기
     */
    public void sendTemporaryPassword(Long memberId) {
        // memberRepository를 사용하여 회원 조회
        // 임시비밀번호 생성
        // memberRepository를 사용하여 회원 비밀번호를 임시 비밀번호로 수정
        // 이메일 전송
    }

    // 이하 생략..
}
```
위와 같이 `sendTemporaryPassword()` 메소드를 작성하였는데, 이메일 전송 기능은 다른곳에서도 재사용될 가능성이 있어보입니다. 따라서 `EmailService`를 만들고 이메일을 전송하는 로직을 따로 빼주었습니다.

**EmailService**
```java
@Service
public class EmailService {

    public void send(String email, String message) {
        // 메일 전송 로직
    }
}
```

위와 같이 `EmailService`를 만들고 해당 클래스를 주입받아서 메일을 전송하는 기능을 사용하면 되는데, 과연 컨트롤러와 서비스 어느 레이어에서 호출해서 사용하는것이 바람직할까요?

<br>

## MemberService에서 EmailService 를 주입받아서 사용(같은 레이어끼리 호출)

첫번째로 `MemberService` -> `EmailService`의 방향으로 호출하는 방법으로 코드를 작성해보겠습니다.

**MemberService**
```java
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;

    /**
     * 이메일로 임시비밀번호를 전송해주는 메서드
     */
    @Transactional
    public void sendTemporaryPassword(Long memberId) {
        // memberRepository를 사용하여 회원 조회
        // 임시비밀번호 생성
        // memberRepository를 사용하여 회원 비밀번호를 임시 비밀번호로 수정
        // emailService를 사용하여 이메일 전송
    }

    // 이하 생략..
}
```

위와 같이 코드를 작성하여 비즈니스 요구사항게 맞게 개발을 완료하였습니다. 하지만 `Controller` -> `Service` -> `Repository` 로 가는 형태에서 `Controller` -> `Service` -> `Service` -> `Repository` 로 가는 형태에 의문점이 듭니다. 이것을 허용한다면 `MemberService` 에서는 여러가지의 `Repository`도 주입받을 수 있고, 서비스가 커짐에 따라서 여러가지의 `Service`도 주입받을 수 있게 되기 때문에 너무 역할이 많아지는듯한 느낌도 듭니다.

그래서 `Controller` -> `Service` -> `Repository`의 형태를 지키기 위하여 이번에는 `MemberController`에서 `EmailService`를 주입받아서 비즈니스 요구사항을 만족시키는 코드를 작성해보겠습니다.

<br>

## MemberController에서 EmailService를 주입받아서 사용(상위 레이어에서 호출)
**MemberController**
```java
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    public void sendTemporaryPassword(Long memberId) {
        // memberService 사용하여 회원 조회
        // 임시비밀번호 생성
        // memberService를 사용하여 회원 비밀번호를 임시 비밀번호로 수정
        // emailService를 사용하여 이메일 전송 
    }
}
```
위와 같이 `MemberController`를 작성해보니, 또 한가지 의문점이 생깁니다.

***만약 메일 전송 처리가 제대로 이뤄지지 않아서 오류가 발생하였다면?***

메일 전송 처리가 제대로 이뤄지지 않았다면 회원의 비밀번호는 임시 비밀번호로 수정하는 것이 아닌 다시 원래의 비밀번호로 롤백을 시켜주어야 할 것입니다.
하지만 컨트롤러에서 요구사항을 처리하였기 때문에 트랜잭션의 범위를 벗어나버려 원래의 비밀번호로 롤백시킬 수가 없게됩니다.

try-catch 문으로 예외가 발생하였을 경우 다시 롤백시켜줄 수는 있겠으나, 그렇게 처리한다면 `MemberController`의 코드도 길어질 뿐더러 어떤 예외가 발생할 지 모르기에 모든 예외상황에 대하여 처리를 해주어야 하고, 올바른 예외처리를 할 수 없게됩니다.

그렇다면 어떻게 해야 유지보수성과 확장성, 가독성까지 챙기며 개발하여야 할까요?

<br>

# 비즈니스와 구현을 분리하자
위의 방식대로 개발하는것이 **틀렸다** 라고는 볼 수 없지만, 저는 아래의 방법대로 개발을 진행하는것이 가장 바람직하다고 느꼈습니다.

먼저 요구사항에서 비즈니스 부분과 구체적인 구현 부분을 나누어봅시다.

```
비즈니스 : 회원의 임시 비밀번호를 전송한다
구체적인 구현 : 이메일을 전송한다, 임시 비밀번호를 생성한다, 회원의 정보를 조회하고 수정한다
```

그다음 `Service` 레이어의 역할은 무엇인가에 대하여 다시 생각해봅니다. `Service` 레이어는 어떤 역할을 수행하여야 할까요?

바로 우리가 개발하고자 하는 서비스의 핵심적인 비즈니스 로직들이 모여있는 계층입니다. 

그렇다면 비즈니스 로직이란 무엇일까요? 여러가지 해석이 있을 수 있겠지만 이 부분에 대하여 재미니님은 다음과 같이 정의하셨습니다.

**상세 구현 로직은 잘 모르더라도 비즈니스의 흐름은 이해 가능한 로직이다.**

이와 달리 위에서는 `Service` 레이어를 비즈니스와 구체적인 구현 부분을 합쳐서 생각하였기 때문에 이러한 고민이 생겨나게된 것입니다!

따라서 우리는  
`컨트롤러` -> `서비스` -> `레포지토리` 3계층으로 생각하는 것이 아닌,

`컨트롤러` -> `서비스` -> `구현` -> `레포지토리` 의 4계층 체제로 생각할 필요가 있습니다. 

이를 각각의 역할로 정의해보자면,

`Presentation Layer` -> `Business Layer` -> `Implementation Layer` -> `Data Access Layer`  
로 정의할 수 있겠습니다.

위와 같이 4계층으로 나눈다면, `EmailService`는 비즈니스 부분이 아닌 구현부분에 해당하기 때문에 이름을 `EmailManager`로 변경시켜주고 @Service 애노테이션 대신 @Component 애노테이션을 달아줍니다.

데이터를 조작하는 부분의 구현도 전부 따로 만들어주도록 합니다.  
회원 데이터 조회를 담당하는  `MemberReader`, 회원 데이터의 삽입, 수정, 삭제를
담당하는 `MemberManager`로 나누어준 뒤 `MemberService` 에서 이 구현 클래스들을 사용하여 요구사항에 맞는 비즈니스를 수행하도록 합니다.   
 
설명한대로 코드를 작성해보자면 다음과 같습니다.

**MemberService**
```java
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberReader memberReader;
    private final MemberManager memberManager;
    private final EmailManager emailManager;

    /**
     * 이메일로 임시비밀번호를 전송해주는 메서드
     */
    @Transactional
    public void sendTemporaryPassword(Long memberId) {
        // 1. memberReader를 사용하여 회원 정보 조회
        // 2. 임시 비밀번호 생성
        // 3. memberManager를 사용하여 회원의 비밀번호를 수정
        // 4. emailManager를 사용하여 이메일 전송

        // ※ 임시 비밀번호를 생성하는 기능은 해당 기능이 재사용 되는가? 되지않는가? 에 따라서 재사용될 가능성이 있다면 구현부분으로 나중에 빼면 된다. 
    } 
}
```

오로지 비즈니스 로직만을 수행하는 `MemberService`가 완성되었습니다. 로직을 코드가 아닌 글로 적어놓았기에 당장 와닿지 않을 수 있겠지만 조금만 들여다보아도 주입받은 구현 계층의 클래스들을 이용하여 서비스 레이어에서의 코드를 글처럼 쉽게 작성할 수 있다는 것을 눈치채셨을 겁니다.

이렇게 구현과 비즈니스를 나누어서 개발한다면 기능에 문제가 발생하였을때도 계층별 역할과 책임을 명확하게 나누어놓았기에 쉽게 발견할 수 있을 것이고, 새로운 비즈니스가 추가되어도 각각의 구현들을 조립하여 쉽게 비즈니스를 확장할 수 있게될 것입니다.

<br>

# 마치며..

저와 같이 스프링을 이용하여 개발중에 비슷한 고민을 하신 분들이 정말 많을것으로 예상됩니다. 당장 구글에 **Service에서 Service 호출** 이라는 키워드로 검색을 해보신다면 비슷한 질문들이 많은 것을 확인할 수 있기 때문입니다.  
하지만 저는 마음에 드는 답변은 찾지 못하였고, 우연히 재미니님의 글을 보고난 뒤에 어느정도의 궁금증을 해소할 수 있게 되었습니다.

제 포스팅도 이러한 고민을 하는 누군가에게 도움이 되길 바라며 틀린내용이 있다면 지적해주시면 감사하겠습니다! 읽어주셔서 감사합니다. 



