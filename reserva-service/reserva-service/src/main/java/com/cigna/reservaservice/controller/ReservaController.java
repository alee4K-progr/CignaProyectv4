package com.cigna.reservaservice.controller;

import com.cigna.reservaservice.dto.ReservaDTO;
import com.cigna.reservaservice.model.Reserva.EstadoReserva;
import com.cigna.reservaservice.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(reservaService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/agenda/{idAgenda}")
    public ResponseEntity<List<ReservaDTO>> listarPorAgenda(@PathVariable Long idAgenda) {
        return ResponseEntity.ok(reservaService.listarPorAgenda(idAgenda));
    }

    // REQ 7: búsqueda por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<List<ReservaDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(reservaService.listarPorRangoFechas(desde, hasta));
    }

    // REQ 7: total de reservas por usuario
    @GetMapping("/usuario/{idUsuario}/total")
    public ResponseEntity<Map<String, Object>> totalPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(reservaService.totalPorUsuario(idUsuario));
    }

    // REQ 7: total por estado
    @GetMapping("/total/estado")
    public ResponseEntity<Map<String, Object>> totalPorEstado(@RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.totalPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> crear(@Valid @RequestBody ReservaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
