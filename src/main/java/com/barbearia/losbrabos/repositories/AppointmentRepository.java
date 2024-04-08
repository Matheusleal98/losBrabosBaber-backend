package com.barbearia.losbrabos.repositories;

import com.barbearia.losbrabos.domain.appointment.Appointment;
import com.barbearia.losbrabos.domain.user.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findByDataAndProviderId(LocalDateTime data, User provider);

    @Query(value = "SELECT a FROM Appointment a WHERE a.providerId = :providerId AND a.data >= :startOfMonth AND a.data < :endOfMonth")
    List<Appointment> findAllByProviderIdAndDateBetween(User providerId, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
