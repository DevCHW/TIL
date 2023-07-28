package com.heapdump;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private String name;
    private int weight;
    private int height;
}
