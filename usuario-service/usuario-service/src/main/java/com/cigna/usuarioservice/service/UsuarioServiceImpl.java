package com.cigna.usuarioservice.service;

import com.cigna.usuarioservice.dto.UsuarioDTO;
import com.cigna.usuarioservice.model.Usuario;
import com.cigna.usuarioservice.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO guardar(UsuarioDTO dto) {
        if (Boolean.TRUE.equals(usuarioRepository.existsByRun(dto.getRun()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con el RUN: " + dto.getRun());
        }
        if (Boolean.TRUE.equals(usuarioRepository.existsByCorreo(dto.getCorreo()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo: " + dto.getCorreo());
        }
        Usuario usuario = toEntity(dto);
        usuario.setActivo(true);
        return toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id));
    }

    @Override
    public UsuarioDTO buscarPorRun(String run) {
        return usuarioRepository.findByRun(run)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con RUN: " + run));
    }

    @Override
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setCorreo(dto.getCorreo());
        existente.setRol(dto.getRol());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existente.setPassword(dto.getPassword());
        }

        return toDto(usuarioRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO toDto(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getRun(), u.getNombre(), u.getApellido(),
                u.getCorreo(), u.getPassword(), u.getRol(), u.getActivo());
    }

    private Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(dto.getId(), dto.getRun(), dto.getNombre(), dto.getApellido(),
                dto.getCorreo(), dto.getPassword(), dto.getRol(), dto.getActivo());
    }
}
