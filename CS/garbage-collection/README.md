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

## 힙 영역 구조
![image](./images/힙_영역_구조.png)

GC의 대상이 되는 힙 영역은 크게 Young Generation과 Old Generation으로 나뉩니다.

## GC 알고리즘


## GC 방식


## Java 버전별 GC 방식


## GC의 특징, 장단점

### 특징
1. 

### 장점
1. 개발자의 실수로 인한 코드 레벨에서의 메모리 누수가 없습니다.
2. 해제된 메모리에는 접근할 수 없습니다.
3. 해제된 메모리를 한번 더 해제하는 실수를 미연에 방지합니다.

### 단점
1. 개발자가 메모리가 언제 해제되는지 정확하게 알 수 없습니다.
2. 가비지 컬렉션(GC)가 동작하는 동안에는 다른 동작을 멈추기 때문에 오버헤드가 발생합니다.(Stop The World)

## GC 튜닝 방법


## 실제 GC관련 트러블슈팅 사례


## Reference
https://www.youtube.com/results?search_query=%EC%A1%B0%EC%97%98%EC%9D%98+gc  

https://www.youtube.com/watch?v=v1gb397uFC4  

https://mangkyu.tistory.com/118

https://coding-factory.tistory.com/828

