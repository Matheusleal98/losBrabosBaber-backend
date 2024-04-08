package com.barbearia.losbrabos.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage extends Throwable {
    private HttpStatus status;
    private String message;
}
