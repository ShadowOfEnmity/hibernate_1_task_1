package com.kostrikov.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"flightId", "seatNo"})
@Builder
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(nullable = false)
    String passportNo;

    @NotNull
    @Column(nullable = false)
    String passengerName;

    @NotNull
    @Column(nullable = false)
    Long flightId;

    @NotNull
    @Column(nullable = false)
    String seatNo;

    @NotNull
    @Column(nullable = false)
    Double cost;
}
