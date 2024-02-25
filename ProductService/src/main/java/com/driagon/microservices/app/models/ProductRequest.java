package com.driagon.microservices.app.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequest {

    private String name;
    private long price;
    private Long quantity;
}