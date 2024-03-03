package com.driagon.microservices.app.externals.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductResponse {

    private long id;
    private String productName;
    private long quantity;
    private long price;
}