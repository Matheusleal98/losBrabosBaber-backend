package com.barbearia.losbrabos.controllers;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.appointment.AppointmentRequestDTO;
import com.barbearia.losbrabos.domain.appointment.DayAvailabilityResponseDTO;
import com.barbearia.losbrabos.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        appointmentService.create(appointmentRequestDTO);
        return ResponseEntity.ok().body("Agendamento criado com sucesso!");
    }

    @GetMapping("/appointments/me")
    public ResponseEntity<?> listProviderAppointments(@RequestParam("user") Long providerId,
                                                                      @RequestParam("day") int day,
                                                                      @RequestParam("month") int month,
                                                                      @RequestParam("year") int year) {
        List<Appointment> appointments = appointmentService.listProviderAppointments(providerId, day, month, year);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum agendamento para este período");
        }
        return ResponseEntity.ok(appointments);
    }

//    @GetMapping("/providers/{providerId}/availability")
//    public ResponseEntity<List<DayAvailabilityResponseDTO>> listProviderDayAvailability(@PathVariable("provider_id") Long providerId,
//                                                                                        @RequestParam int year,
//                                                                                        @RequestParam int month,
//                                                                                        @RequestParam int day) {
//        List<DayAvailabilityResponseDTO> availability = appointmentService.listProviderDayAvailability(providerId, year, month, day);
//        return ResponseEntity.ok(availability);
//    }

    @GetMapping("/providers/{providerId}/month-availability")
    public ResponseEntity<?> listProviderMonthAvailability(@PathVariable Long providerId,
                                                                                       @RequestParam int year,
                                                                                       @RequestParam int month) {
        List<DayAvailabilityResponseDTO> monthAvailability = appointmentService.listProviderMonthAvailability(providerId, year, month);

        if (monthAvailability.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum agendamento encontrado.");
        }

        return ResponseEntity.ok(monthAvailability);
    }
}
