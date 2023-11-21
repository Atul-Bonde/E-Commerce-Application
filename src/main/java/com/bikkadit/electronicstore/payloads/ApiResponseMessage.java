package com.bikkadit.electronicstore.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {

    private String message;

    private boolean status;

    private HttpStatus httpStatus;


}
