package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.services.impl.ListProviderAppointmentsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProviderAppointments {

    private final ListProviderAppointmentsServiceImpl listProviderAppointments;

    @GetMapping("/appointments/me")
    public ResponseEntity<?> listProviderAppointments(@RequestParam("user") Long providerId,
                                                      @RequestParam("day") int day,
                                                      @RequestParam("month") int month,
                                                      @RequestParam("year") int year) {
        List<Appointment> appointments = listProviderAppointments.listProviderAppointments(providerId, day, month, year);
        return ResponseEntity.ok(appointments);
    }
}
