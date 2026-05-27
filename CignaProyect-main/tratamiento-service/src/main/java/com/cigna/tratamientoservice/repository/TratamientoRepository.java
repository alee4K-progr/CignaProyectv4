package com.cigna.tratamientoservice.repository;

import com.cigna.tratamientoservice.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    List<Tratamiento> findByActivoTrue();

    List<Tratamiento> findByNombreContainingIgnoreCase(String nombre);
}
