package com.cigna.agendaservice.controller;

import com.cigna.agendaservice.dto.AgendaDTO;
import com.cigna.agendaservice.model.Agenda.EstadoAgenda;
import com.cigna.agendaservice.service.IAgendaService;
import com.cigna.agendaservice.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final IAgendaService agendaService;
    private final AgendaService agendaServiceImpl;

    public AgendaController(IAgendaService agendaService, AgendaService agendaServiceImpl) {
        this.agendaService = agendaService;
        this.agendaServiceImpl = agendaServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> listar() {
        return ResponseEntity.ok(agendaService.listar());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<AgendaDTO>> listarDisponibles() {
        return ResponseEntity.ok(agendaService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AgendaDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(agendaService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<AgendaDTO>> listarPorServicio(@PathVariable Long idServicio) {
        return ResponseEntity.ok(agendaService.listarPorServicio(idServicio));
    }

    // REQ 7: búsqueda por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<List<AgendaDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(agendaServiceImpl.listarPorRangoFechas(desde, hasta));
    }

    // REQ 7: total de agendas por servicio
    @GetMapping("/servicio/{idServicio}/total")
    public ResponseEntity<Map<String, Object>> totalPorServicio(@PathVariable Long idServicio) {
        long total = agendaServiceImpl.contarPorServicio(idServicio);
        return ResponseEntity.ok(Map.of("idServicio", idServicio, "total", total));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<AgendaDTO>> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) EstadoAgenda estado) {
        return ResponseEntity.ok(agendaService.listarPorFecha(fecha, estado));
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> crear(@Valid @RequestBody AgendaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AgendaDTO dto) {
        return ResponseEntity.ok(agendaService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AgendaDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoAgenda estado) {
        return ResponseEntity.ok(agendaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        agendaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
