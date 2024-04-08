package com.barbearia.losbrabos.services;

import com.barbearia.losbrabos.domain.appointment.AppointmentRequestDTO;
import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.appointment.DayAvailabilityResponseDTO;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.exceptions.AppointmentBookedException;
import com.barbearia.losbrabos.exceptions.DateNotFoundException;
import com.barbearia.losbrabos.exceptions.InvalidDateException;
import com.barbearia.losbrabos.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    public ResponseEntity create(AppointmentRequestDTO appointmentRequestDTO) {
        LocalDateTime dataAtual = LocalDateTime.now();
        User user = userService.findById(appointmentRequestDTO.getUserId());
        User provider = userService.findById(appointmentRequestDTO.getProviderId());
        Appointment existingAppointment = appointmentRepository.findByDataAndProviderId(appointmentRequestDTO.getData(), provider);

        if (appointmentRequestDTO.getData().isBefore(dataAtual))
            throw new InvalidDateException();

        if (user.equals(provider))
            throw new RuntimeException("Você não pode criar um agendamento para você mesmo.");

        Integer appointmentHour = appointmentRequestDTO.getData().getHour();
        if (appointmentHour < 8 || appointmentHour > 17)
            throw new DateNotFoundException();

        if (existingAppointment != null)
            throw new AppointmentBookedException();

        insert(appointmentRequestDTO, user, provider);

        String formattedDateTime = appointmentRequestDTO.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
        notificationService.createNotification(provider, "Novo agendamento para " + formattedDateTime );
        //cacheProvider.invalidate("provider-appointments:" + providerId + ":" + appointmentDateTime.format(DateTimeFormatter.ofPattern("yyyy-M-d")));
        return ResponseEntity.ok().build();
    }

    public List<Appointment> listProviderAppointments(Long providerId, int day, int month, int year) {
        User provider = userService.findById(providerId);
        LocalDate date = LocalDate.of(year, month, day);
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX).truncatedTo(ChronoUnit.SECONDS);

        List<Appointment> appointments = appointmentRepository.findAllByProviderIdAndDateBetween(provider, startOfDay, endOfDay);
        return appointments;
    }


    public List<DayAvailabilityResponseDTO> listProviderMonthAvailability(Long providerId, int year, int month) {
        User user = userService.findById(providerId);

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1 , 0 ,0);
        LocalDateTime endOfMonth = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23,59,59);
        List<Appointment> appointments = appointmentRepository.findAllByProviderIdAndDateBetween(user, startOfMonth, endOfMonth);

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

    private void insert(AppointmentRequestDTO data, User user, User provider) {
        Appointment appointment = new Appointment();
        appointment.setUserId(user);
        appointment.setProviderId(provider);
        appointment.setData(data.getData());
        appointment.setCreatedAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }
}
