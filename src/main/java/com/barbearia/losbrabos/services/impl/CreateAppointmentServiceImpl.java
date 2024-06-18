package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.appointment.AppointmentRequestDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.AppointmentBookedException;
import com.barbearia.losbrabos.exceptions.DateNotFoundException;
import com.barbearia.losbrabos.exceptions.InvalidDateException;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class CreateAppointmentServiceImpl {

    private final UserServiceImpl userService;
    private final AppointmentServiceImpl appointmentService;
    private final NotificationServiceImpl notificationService;

    public ResponseEntity create(AppointmentRequestDTO data) {
        LocalDateTime appointmentDate = data.getData();
        validateAppointmentDate(appointmentDate);
        validateAppointmentUserAndProvider(data.getUserId(), data.getProviderId());
        validateAppointmentHour(data.getData().getHour());

        Appointment findAppointmentInSameDate = appointmentService.findByDataAndProviderId(data.getData(), data.getProviderId());
        validateExistingAppointment(findAppointmentInSameDate);

        User userAppointment = userService.findById(data.getUserId())
                .orElseThrow(UserNotFoundException::new);
        User providerAppointment = userService.findById(data.getProviderId())
                .orElseThrow(UserNotFoundException::new);

        appointmentService.insert(data.getData(), userAppointment, providerAppointment);

        String formattedDateTime = data.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
        notificationService.insert(providerAppointment, "Novo agendamento para " + formattedDateTime);
        return ResponseEntity.ok().build();
    }

    private void validateAppointmentDate(LocalDateTime appointmentDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (appointmentDate.isBefore(currentDate))
            throw new InvalidDateException();
    }

    private void validateAppointmentUserAndProvider(Long userId, Long providerId) {
        if (userId.equals(providerId))
            throw new RuntimeException("Você não pode criar um agendamento para você mesmo.");
    }

    private void validateAppointmentHour(Integer appointmentHour) {
        if (appointmentHour < 8 || appointmentHour > 17)
            throw new DateNotFoundException();
    }

    private void validateExistingAppointment(Appointment appointment) {
        if (appointment != null)
            throw new AppointmentBookedException();
    }
}
