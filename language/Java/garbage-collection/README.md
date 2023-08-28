# GC(garbage collection) 총 정리

## Intro
평소 저는 단순하게 GC를 알아서 사용하지 않는 메모리를 없애주는 똑똑한 청소부의 역할을 하는 친구정도의 개념으로만 알고있었습니다. 물론 이것도 틀린말은 아니지만 이번에 Java관련 기술면접 질문답변을 기록하면서, GC의 개념을 확실하게 잡고 가야겠다는 생각이 들어서 여러 참고자료들을 보며 공부한 내용을 기록하고자 합니다.


## Garbage Collection(GC) 이란?
프로그램을 개발 하다 보면 더이상 쓰이지 않는(참조되지 않는) 메모리인 Garbage가 발생하게 됩니다.  
C언어에서는 `free()`라는 함수를 통하여 개발자가 직접 메모리를 해제해주어야 하지만, Java나 Kotlin 에서는 Garbage Collector가 알아서 불필요한 메모리를 정리해주기 때문에 개발자의 실수로 memory leak이 발생하는 일을 막아줍니다.  
그런데 GC는 어떻게 알아서 똑똑하게 불필요한 메모리를 판단하여 정리하고, 또 언제 정리를 할까요?  

이를 알기 위해선 먼저 Java의 컴파일 과정과, JVM 메모리구조, GC의 여러가지 방식들에 대하여 살펴볼 필요가 있습니다.

## Java 컴파일 과정
![image](./images/자바_컴파일_과정.png)

Java 컴파일 과정에 대하여 잘 모르시는 분이라면 위의 그림에는 낯선 친구들이 많이 나와서 당황하실 수도 있습니다. 하지만 다음과 같이 동작 과정을 하나하나 따져본 뒤, 
1. 자바 프로그램을 실행하면, JVM은 OS로부터 메모리를 할당합니다.이 메모리 영역을 Runtime Data Area라고 부릅니다.
2. 자바 컴파일러(javac)가 자바 소스코드(.java)를 자바 바이트코드(.class)로 컴파일합니다.
3. Class Loader를 통해 JVM Runtime Data Area로 로딩합니다.
4. Runtime Data Area에 로딩 된 .class들은 실행 엔진(Execution Engine)을 통해 해석합니다.
5. 해석된 바이트 코드는 Runtime Data Area의 각 영역에 배치되어 수행하며 이 과정에서 실행 엔진에 의해 GC의 작동과 스레드 동기화가 이루어집니다.

위의 컴파일 과정을 요약하자면,  

OS로부터 메모리 할당받기   
-> 자바 컴파일러가 .java를 .class로 변환   
-> 클래스로더가 .class들을 Runtime Data Area로 적재   
-> 로딩된 .class 파일들을 실행엔진이 해석   
-> 해석된 바이트 코드들을 Runtime Data Area의 각 영역에 배치되어 수행하며 GC의 작동과 스레드 동기화 수행

이라고 할 수 있겠습니다.

## JVM 메모리 구조
![image](./images/Runtime_Data_Area_구조.png)

JVM 메모리의 구조는 크게 Stack Area, Heap Area, Method Area로 나뉘고, 더 나아가서 PC Register, Native Method Stack이 있습니다.   
이 중 위 그림처럼 빨간색 테두리로 묶인 Stack Area, PC Register, Native Method Stack은 스레드별로 생성되고, Heap Area와 Method Area는 여러 스레드에서 공유되는 영역입니다.  
여기서 우리가 자세히 봐야 될 부분은 GC의 대상이 되는 힙 영역인데요,

Java로 프로그래밍을 해보신 분이라면 용어가 낯설더라도 하나하나 들여다보면서 역할을 따져보면 크게 어렵지 않게 이해하실 수 있을것입니다.

### Method Area(Static Area)
- 클래스 멤버 변수의 이름, 데이터 타입, 접근 제어자 정보와 같은 각종 필드 정보들과 메서드 정보, 데이터 타입 정보, static 변수, Constant Pool, final class 등이 생성되는 영역입니다.

### Heap Area
- new 키워드로 생성된 객체와 배열이 생성되는 영역입니다.
- 주기적으로 GC가 제거하는 영역입니다.
- Heap Area는 또 크게 Yonng Generation과 Old Generation으로 나뉘는데, 이는 아래서 자세히 다루겠습니다.

### Stack Area
- 지역변수, 매개변수, 리턴 값, 연산에 사용되는 임시 값 등이 생성되는 영역입니다.

### PC Register
- 스레드가 생성될 때마다 생성되는 영역으로 프로그램 카운터. 즉 현재 스레드가 실행되는 부분의 주소와 명령을 저장하고 있는 영역입니다.

### Native Method Stack
- C/C++과 같은 저수준 언어의 코드를 수행하기 위한 스택입니다.


## GC 알고리즘(Mark And Sweep)
Java에서의 GC는 기본적으로 `Mark And Sweep` 알고리즘으로 돌아갑니다.
객체가 참조하는 갯수를 세어 Reference Count가 0이된다면 메모리를 해제해주는 방식인 `Reference Counting` 방식도 있는데, 이는 효율적이지 못합니다. 왜냐하면 `Reference Counting` 방식은 순환 참조일 경우에 항상 참조 횟수가 1이기 때문에 영원히 `Garbage Collection`의 대상이 되지 않는 문제가 있기 때문입니다.

이를 해결하기 위해 `Mark And Sweep`이라는 알고리즘이 만들어졌고, Java의 GC는 이 알고리즘으로 동작합니다.

`Mark And Sweep` 알고리즘은 `Root Space`로부터 동적 메모리 영역의 객체 접근이 가능한지를 해제의 기준으로 둡니다.

Root Space부터 해당 객체로 접근이 가능하다면 ? => 메모리에 유지
Root Space부터 해당 객체로 접근이 불가능하다면 ? => 삭제!


그림으로 표현하면 다음과 같습니다.
![image](./images/Mark_And_Sweep.png)

위 그림에서 왼쪽은 Root Space부터 객체로 접근이 가능한지 판별하는 Mark 이고, 오른쪽은 접근이 불가능한 객체들을 메모리에서 지워버리는 Sweep 입니다.

오른쪽에서 너저분했던 객체들이 불필요한 것들은 삭제하고, 이쁘게 한쪽으로 정리되어진 모습을 볼 수 있습니다. 이를 `Compaction` 이라고 합니다.

Compaction은 꼭 필수로 동작하는것은 아니라고 합니다!

그렇다면 여기서 궁금한 점은 GC의 대상은 Heap 영역이라는 것은 알겠고, Root Space가 어디인지 궁금할 수가 있는데요, JVM의 Root Space는 다음의 3가지 영역입니다.

1. Stack의 로컬 변수
2. Method Area의 Static 변수
3. Native Method Stack의 JNI 참조

그림으로 표현하면 다음과 같습니다.
![image](./images/Root_Space.png)

노란색 박스로 표시되어진 `stack`, `native method stack`, `runtime constant pool` 이 Root Space입니다!

`Reference Counting` 방식의 치명적인 단점(순환참조 문제)을 보완하여 나온 `Mark And Sweep` ***가비지 컬렉션 수행중엔 프로그램의 실행이 잠시 중단된다.***라는 단점은 존재합니다.  

실시간성이 중요한 어플리케이션 같은 경우 GC에게 메모리 관리를 맡기는 것이 치명적인 장애로 이어질 가능성도 존재한다는 뜻입니다.

따라서 어플리케이션의 사용성을 유지하면서 효율적이게 GC를 실행하는 것이 꽤나 어려운 최적화 작업이라고 합니다.

이제 JVM에서는 어떻게 `Mark And Sweep` 방식의 특징을 녹여내었는지 알아보겠습니다.

## 힙 영역 구조
![image](./images/힙_영역_구조.png)

GC의 대상이 되는 힙 영역은 크게 `Young Generation`과 `Old Generation`으로 나뉘는데요,

Young Generation에서 발생하는 GC는 `Minor GC`, Old Generation에서 발생하는 GC는 `Major GC`라고 부릅니다.

Young Generation은 또 3가지 영역인 `Eden`, `Survival 0`, `Survival 1` 으로 나뉘는데요

Eden은 새롭게 생성된 객체들이 할당되는 영역,
Survival 0, Survival 1 영역은 Minor GC로부터 살아남은 객체들이 존재하는 영역입니다.

Minor GC의 실행 타이밍은 바로 Eden 영역이 꽉 차게 된다면 실행하게 되고,
Major GC의 실행 타이밍은 Old Generation 영역이 꽉 차게 된다면 실행하게 되는데요, 동작방식을 글로 정리하면 다음과 같이 동작하게 됩니다.

1. 객체들이 생성되어 Eden에 할당되어집니다.
2. 계속 새로운 객체들이 생성되어 어느시점에 Eden이 꽉 차게 됩니다 
3. Minor GC 실행!
    - Mark And Sweep 방식으로 불필요한 메모리는 지우고 Eden 영역의 메모리를 Survival 0 영역으로 옮깁니다. 이 때, 옮기면서 살아남은 객체들의 age bit(minor gc에서 살아남은 횟수)를 1 증가시켜줍니다.
5. 또다시 새로운 객체들이 생성되어 Eden 영역이 꽉 차게 됩니다.
6. Minor GC 실행 !
    - Mark And Sweep 방식으로 불필요한 메모리는 지우고 Eden 영역의 메모리를 Survival 1 영역으로 옮깁니다. 이 때, 옮기면서 살아남은 객체들의 age bit(minor gc에서 살아남은 횟수)를 1 증가시켜줍니다.
    - 3번에서 옮긴 Survival 0 의 객체들도 마찬가지 입니다. 불필요한 메모리는 삭제하고 Survival 0의 객체들도 Survival 1로 옮겨집니다. 이때도 age bit를 1 증가시켜줍니다.
7. 위의 과정을 반복하면서, age bit가 어느 일정수준을 넘어간다면 오래도록 참조될 객체구나 라고 판단하고, 해당 객체들 Old Generation에 넘겨줍니다. 이 과정을 ***Promotion*** 이라고 합니다.
8. 시간이 많이 지나게 된다면 언젠가 Old Generation도 다 채워지는 날이 오게 될겁니다. 이 때 Major GC가 발생하여 Mark And Sweep 방식을 통해 필요 없는 메모리를 지워줍니다.

※ Java 8에서는 Parallel GC 방식 사용 기준으로 age bit가 15가 되면 Promotion이 진행된다고 합니다.


글을 이미지와 함께 좀더 자세히 알아보고 싶은 분은 https://joel-dev.site/94 를 추천드립니다!


## GC 실행 방식
JVM에서는 어플리케이션과 GC를 병행하여 실행할 수 있는 여러 옵션들을 제공합니다. 각각의 옵션들은 GC를 실행하기 위하여 JVM이 어플리케이션의 실행을 멈추는 Stop The World의 시간이 다릅니다.


1. Serial GC
- 하나의 쓰레드로 GC를 실행하는 방식입니다.
    - Stop The World의 시간이 깁니다.
    - 싱글 쓰레드 환경 및 Heap 영역이 매우 작을 때 사용합니다.

2. Parallel GC(Java 8 기본 GC 방식)
- 여러개의 쓰레드로 가비지 컬렉션을 수행하는 방식입니다.
    - 멀티코어 환경에서 어플리케이션 처리 속도를 향상시키기 위해 사용됩니다.
    - Serial GC보다 Stop The World 시간이 짧습니다.

3. CMS GC(Councurrent-Mark-Sweep)
- 대부분의 가비지 수집 작업을 어플리케이션 쓰레드와 동시에 수행하는 방식입니다.
    - Stop The World 시간이 위의 두 방식보다 짧습니다.
    - 메모리와 CPU를 많이 사용하고, Mark-And-Sweep 과정 이후 메모리 파편화를 해결하는 Compaction이 기본적으로 제공되지 않습니다.

4. G1 GC(Java 9 이상 기본 GC 방식)
- Heap을 일정 크기의 Region으로 잘게 나누어 어떤 영역은 Young Gen, 어떤 영역은 Old Gen으로 쓰는 방식입니다.
    - 런타임에 G1 GC가 필요에 따라 영역별 Region 개수를 튜닝한다고 합니다.
    - Java9 이상부터 G1 GC를 기본 GC 실행방식으로 사용합니다.


## GC의 장단점
### 장점
1. 개발자의 실수로 인한 코드 레벨에서의 메모리 누수가 없습니다.
2. 해제된 메모리에는 접근할 수 없습니다.
3. 해제된 메모리를 한번 더 해제하는 실수를 미연에 방지합니다.

### 단점
1. 개발자가 메모리가 언제 해제되는지 정확하게 알 수 없습니다.
2. 가비지 컬렉션(GC)가 동작하는 동안에는 다른 동작을 멈추기 때문에 오버헤드가 발생합니다.(Stop The World)


이상으로 JVM의 GC에 대하여 알아보았습니다. 다음 기회가 된다면 GC의 튜닝방법과 실제 GC로 인한 장애를 해결하는 과정 예시를 포스팅하도록 하겠습니다.

공부하여 알게된 내용을 정리한 것이다보니 오류가 있을 수 있습니다. 이 점 유의하셔서 참고용으로만 읽어주셨으면 감사하겠습니다.

## Reference
https://www.youtube.com/results?search_query=%EC%A1%B0%EC%97%98%EC%9D%98+gc  

https://www.youtube.com/watch?v=v1gb397uFC4  

https://mangkyu.tistory.com/118

https://coding-factory.tistory.com/828

