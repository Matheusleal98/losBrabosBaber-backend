package com.barbearia.losbrabos.services.impl;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.user.User;
import com.barbearia.losbrabos.repositories.AppointmentRepository;
import com.barbearia.losbrabos.services.interfaces.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;


    @Override
    public Appointment findByDataAndProviderId(LocalDateTime data, Long providerId) {
        return appointmentRepository.findByDataAndProviderId(data, providerId);
    }

    @Override
    public List<Appointment> findAllByProviderIdAndDateBetween(User providerId, LocalDateTime startOfMonth, LocalDateTime endOfMonth){
        return appointmentRepository.findAllByProviderIdAndDateBetween(providerId, startOfMonth, endOfMonth);
    }

    @Override
    public void insert(LocalDateTime date, User user, User provider) {
        Appointment appointment = new Appointment();
        appointment.setData(date);
        appointment.setUserId(user);
        appointment.setProviderId(provider);
        appointment.setCreatedAt(LocalDateTime.now());

        save(appointment);
    }

    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

}
