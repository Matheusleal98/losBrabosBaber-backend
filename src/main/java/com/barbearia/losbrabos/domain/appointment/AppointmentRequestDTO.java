package com.barbearia.losbrabos.domain.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppointmentRequestDTO {

    private Long providerId;
    private Long userId;
    private LocalDateTime data;
}
