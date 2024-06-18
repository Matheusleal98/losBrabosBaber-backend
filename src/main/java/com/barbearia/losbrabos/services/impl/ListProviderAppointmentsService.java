package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListProviderAppointmentsService {

    private final UserServiceImpl userService;
    private final AppointmentServiceImpl appointmentService;

    public List<Appointment> listProviderAppointments(Long providerId, int year, int month, int day) {
        LocalDateTime startOfDay = getStartOfDay(year, month, day);
        LocalDateTime endOfDay = getEndOfDay(year, month, day);
        User provider = userService.findById(providerId)
                .orElseThrow(UserNotFoundException::new);

        return appointmentService.findAllByProviderIdAndDateBetween(provider, startOfDay, endOfDay);
    }

    private LocalDateTime getStartOfDay(int year, int month, int day) {
        return LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MIN);
    }

    private LocalDateTime getEndOfDay(int year, int month, int day) {
        return LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.MAX);
    }
}
