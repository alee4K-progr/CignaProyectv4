package com.cigna.servicioservice.repository;

import com.cigna.servicioservice.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByEstadoTrue();

    List<Servicio> findByNombreContainingIgnoreCase(String nombre);

}
