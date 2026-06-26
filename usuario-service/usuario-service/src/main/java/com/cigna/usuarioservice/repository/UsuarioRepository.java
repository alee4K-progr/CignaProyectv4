package com.cigna.usuarioservice.repository;

import com.cigna.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByRun(String run);

    Optional<Usuario> findByCorreo(String correo);

    Boolean existsByRun(String run);

    Boolean existsByCorreo(String correo);

    List<Usuario> findByActivoTrue();
}
