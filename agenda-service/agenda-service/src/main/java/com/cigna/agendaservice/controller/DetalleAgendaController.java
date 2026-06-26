package com.cigna.agendaservice.controller;

import com.cigna.agendaservice.dto.DetalleAgendaDTO;
import com.cigna.agendaservice.service.DetalleAgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas/detalles")
public class DetalleAgendaController {

    private final DetalleAgendaService detalleService;

    public DetalleAgendaController(DetalleAgendaService detalleService) {
        this.detalleService = detalleService;
    }

    @GetMapping("/agenda/{idAgenda}")
    public ResponseEntity<List<DetalleAgendaDTO>> listarPorAgenda(@PathVariable Long idAgenda) {
        return ResponseEntity.ok(detalleService.listarPorAgenda(idAgenda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleAgendaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DetalleAgendaDTO> crear(@RequestBody DetalleAgendaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleService.crear(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
