# **Intro**

최근 CORS에 대하여 공부를 해보고, Spring에서 CORS 설정은 어떻게 하는지에 대하여 공부에 본 뒤, 직접 적용하는 코드를 작성하고 간단하게 정리하고자 합니다.  
모든 코드는 [https://github.com/DevCHW/spring-cors-example](https://github.com/DevCHW/spring-cors-example) 에서 확인하실 수 있습니다.

혼자 공부하여 정리한 내용이라 틀린 부분이 있을 수 있습니다. 이 점 유의하여 읽어주시고 틀린 부분이 있다면 고쳐주시면 감사하겠습니다!

# **Spring CORS 설정 방법 3가지**

1.  어노테이션 활용
2.  WebConfig에서 글로벌하게 설정
3.  Spring Security 설정

## **어노테이션 활용**

Controller 클래스에서 @CrossOrigin 어노테이션을 이용하여 CORS를 적용할 수 있습니다.  
클래스레벨에 어노테이션을 달아 컨트롤러의 모든 메소드에 설정할 수도 있고, 메소드에 따로따로 설정할 수도 있습니다.

### **클래스 레벨에 적용하기**

```
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CorsController {

    @GetMapping("/cors/v1")
    public String corsV1() {
        return "ok";
    }
}
```

### **메소드 레벨에 적용하기**

```
@RestController
public class CorsController {

    @CrossOrigin(origins = "http//localhost:8080", allowedHeaders = "*")
    @GetMapping("/cors/v2")
    public String corsV2() {
        return "ok";
    }
}
```

## **WebConfig에서 글로벌하게 설정**

`WebMvcConfigurer`를 구현한 클래스에서 `addCorsMappings` 메소드를 오버라이드 하여 CORS 설정을 할 수 있습니다.

`addCorsMappings()` 메소드의 매개변수로 들어온 `CorsRegistry registry`의 `addMapping()`의 기본 값은 다음과 같습니다.

```
Allow all origins
Allow "simple" methods GET, HEAD and POST
Allow all headers
Set max age to 1800 seconds (30 minutes)
```

다음과 같이 기본값 대신 커스텀으로 설정할 수도 있습니다.

```
registry.addMapping("/**")
        .allowedOrigins("http://localhost:8080")
        .allowedMethods("GET", "POST")
        .maxAge(3000);
```

위 설정을 설명하자면  
Origin은 [http://localhost:8080](http://localhost:8080)  
허용할 메소드는 GET, POST  
최대 캐싱 시간은 3000초  
로 설정한 것입니다.

위와 같은 코드들을 @Configuration으로 스프링에 등록하면  
어플리케이션의 전체 글로벌 설정으로 CORS를 적용한 것입니다.

## **Spring Security 설정**

Spring security에서 CORS 설정하는 법은 Spring MVC에서 설정하는것보다 간단한데요,

Spring security를 적용하셨다면 보통 다음의 SecurityConfig 클래스를 설정할 것입니다.

``` java
package com.example.springcors.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilerChain(HttpSecurity http) throws Exception {
        return http.build();
    }
}
```

위의 SecurityConfig 클래스에 `SecurityFilterChain`을 반환하는 메소드를 빈으로 등록하여 다음의 cors()메소드를 추가해주시면 됩니다.

``` java
@Bean
public SecurityFilterChain securityFilerChain(HttpSecurity http) throws Exception {
    http.cors();    //CORS 설정
    return http.build();
}
```

Spring MVC에서의 CORS 설정과 Spring Security에서의 설정 둘 중 어느곳에서 해야하는지 헷갈릴 수 있는데요, 결론은 둘다 설정할 수 있습니다.

하지만 Spring Security가 사용자의 요청을 가장 앞단에서 처리하므로, Spring Security에서 설정하는것이 좋다고 볼 수 있습니다.

만약 둘 다 설정하게 된다면 Spring Security가 Spring MVC에서 제공되어지는 CORS 설정을 탐지하여 사용하게 됩니다.