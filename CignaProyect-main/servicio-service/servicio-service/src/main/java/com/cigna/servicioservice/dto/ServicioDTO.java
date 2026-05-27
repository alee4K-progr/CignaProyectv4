package com.cigna.servicioservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {

	private Long idServicio;
	private String nombre;
	private String descripcion;
	private Boolean estado;

}
