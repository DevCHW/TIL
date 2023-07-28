package com.heapdump;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class OutOfMemoryErrorController {

    @GetMapping("/outOfMemory")
    public int register() {
        List<Person> people = new ArrayList<>();

        // 1억개의 객체가 생성되어 힙 메모리에 올라갈 것이다.
        for(int i=0; i<100000000; i++) {
            Person person = Person.builder()
                    .name("Hyun Woo Choi"+i)
                    .weight(i%120)
                    .height(i%120)
                    .build();

            people.add(person);
        }

        log.info("Person count={}", people.size());
        return people.size();
    }
}
