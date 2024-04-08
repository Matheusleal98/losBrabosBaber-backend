package com.barbearia.losbrabos.domain.appointment;

import com.barbearia.losbrabos.domain.user.User;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id, User provider, User user, LocalDateTime data, LocalDateTime create_at) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getProviderId(), appointment.getUserId(), appointment.getData(), appointment.getCreatedAt());
    }
}
