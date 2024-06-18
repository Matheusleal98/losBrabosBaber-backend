package com.barbearia.losbrabos.services.interfaces;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.appointment.DayAvailabilityResponseDTO;
import com.barbearia.losbrabos.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment findByDataAndProviderId(LocalDateTime data, Long providerId);
    List<Appointment> findAllByProviderIdAndDateBetween(User providerId, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
    void insert(LocalDateTime date, User user, User provider);
    void save(Appointment appointment);
}
