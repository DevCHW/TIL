# 테스트코드에서 Lombok 사용하기

보통 lombok을 사용하기위하여 `build.gradle`에 다음 코드를 추가한다.

**build.gradle**
```
// lombok
compileOnly 'org.projectlombok:lombok'

testCompileOnly 'org.projectlombok:lombok'
```

그러나 위 부분만을 추가한다면 테스트코드에서는 lombok을 사용할 수 없는데, 이럴 땐 **build.gradle**에 다음의 코드를 추가하면 된다.


**build.gradle**
```
complieOnly 'org.projectlombok:lombok'

testComplieOnly 'org.projectlombok:lombok'
```