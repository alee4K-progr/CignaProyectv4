package com.cigna.usuarioservice.service;

import com.cigna.usuarioservice.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDTO> listar();

    UsuarioDTO guardar(UsuarioDTO dto);

    UsuarioDTO buscarPorId(Long id);

    UsuarioDTO buscarPorRun(String run);

    UsuarioDTO actualizar(Long id, UsuarioDTO dto);

    void eliminar(Long id);
}
