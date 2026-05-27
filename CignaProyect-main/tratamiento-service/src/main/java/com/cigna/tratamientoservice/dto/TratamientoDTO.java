package com.cigna.tratamientoservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoDTO {

    private Long idTratamiento;

    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    private String nombre;

    private String descripcion;

    private Integer duracionDias;

    private Boolean activo;
}
