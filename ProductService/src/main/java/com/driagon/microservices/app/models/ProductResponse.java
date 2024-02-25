package com.driagon.microservices.app.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductResponse {

    private long id;
    private String productName;
    private long quantity;
    private long price;
}