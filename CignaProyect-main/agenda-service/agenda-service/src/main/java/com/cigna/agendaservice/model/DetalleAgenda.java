package com.cigna.agendaservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_agenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleAgenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agenda", nullable = false)
    private Agenda agenda;

    @Column(name = "id_tratamiento", nullable = false)
    private Long idTratamiento;

    @Column(length = 500)
    private String observaciones;

    private Boolean activo = true;
}
