package com.barbearia.losbrabos.domain.appointment;

import com.barbearia.losbrabos.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "appointment")
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private User providerId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime data;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

}
