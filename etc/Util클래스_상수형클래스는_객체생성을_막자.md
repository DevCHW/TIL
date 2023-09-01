# Util클래스 또는 상수형 클래스는 객체생성을 막자

어플리케이션 개발을 진행하다보면 자주 쓰이는 성격이 비슷한 메소드들을 묶어 Util성 클래스로 만들거나, 상수값들을 모아놓은 클래스를 만들 일이 있습니다.

이러한 클래스들은 static 메소드와 static 변수들을 제공해주기 때문에, 객체생성은 의미가 없습니다.

따라서 기본생성자를 private로 지정하여 객체생성을 막는것이 바람직합니다.


```java

public final class StringUtils {

    public static String utilmethod(String str) {
        // util 로직...
        return result;
    }

    // 객체생성 금지!
    private StringUtils() {

    };
}

```

Lombok 라이브러리를 사용한다면 `@NoArgsConstructor` 어노테이션을 활용하여 기본생성자를 private로 지정할 수 있습니다.

```java

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    public static String utilmethod(String str) {
        // util 로직...
        return result;
    }

}

```
