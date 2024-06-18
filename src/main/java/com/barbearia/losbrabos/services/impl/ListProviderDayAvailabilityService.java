package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.appointment.DayAvailabilityResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ListProviderDayAvailabilityService {
    private final UserServiceImpl userService;
    private final AppointmentServiceImpl appointmentService;

    public List<DayAvailabilityResponseDTO> listProviderMonthAvailability(Long providerId, int year, int month) {
        LocalDateTime startOfMonth = getStartOfMonth(year, month);
        LocalDateTime endOfMonth = getEndOfMonth(year, month);
        User provider = userService.findById(providerId)
                .orElseThrow(UserNotFoundException::new);

        List<Appointment> appointments = appointmentService.findAllByProviderIdAndDateBetween(provider, startOfMonth, endOfMonth);
        int numberOfDaysInMonth = YearMonth.of(year, month).lengthOfMonth();
        return IntStream.rangeClosed(1, numberOfDaysInMonth)
                .mapToObj(day -> {
                    LocalDate compareDate = LocalDate.of(year, month, day).atTime(23, 59, 59).toLocalDate();
                    long appointmentInDay = appointments.stream()
                            .filter(appointment -> appointment.getData().toLocalDate().getDayOfMonth() == day)
                            .count();
                    return new DayAvailabilityResponseDTO(day, compareDate.isAfter(LocalDate.now()) && appointmentInDay < 10);
                })
                .collect(Collectors.toList());
    }

    private LocalDateTime getStartOfMonth(int year, int month) {
        return LocalDateTime.of(year, month, 1, 0, 0);
    }

    private LocalDateTime getEndOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return LocalDateTime.of(year, month, yearMonth.lengthOfMonth(), 23, 59, 59);
    }
}
