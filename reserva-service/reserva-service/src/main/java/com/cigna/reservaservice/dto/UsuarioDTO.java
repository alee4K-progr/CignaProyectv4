package com.cigna.reservaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
    private Boolean activo;
}
