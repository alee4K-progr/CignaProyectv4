package com.cigna.tratamientoservice.controller;

import com.cigna.tratamientoservice.dto.TratamientoDTO;
import com.cigna.tratamientoservice.service.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @GetMapping
    public ResponseEntity<List<TratamientoDTO>> listar() {
        return ResponseEntity.ok(tratamientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tratamientoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TratamientoDTO> crear(@Valid @RequestBody TratamientoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamientoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TratamientoDTO dto) {
        return ResponseEntity.ok(tratamientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tratamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
