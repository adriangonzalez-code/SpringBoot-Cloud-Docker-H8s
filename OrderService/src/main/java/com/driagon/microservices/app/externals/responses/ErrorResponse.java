package com.driagon.microservices.app.externals.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String errorMessage;
    private String errorCode;
}