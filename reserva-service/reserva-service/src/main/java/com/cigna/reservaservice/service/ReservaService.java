package com.cigna.reservaservice.service;

import com.cigna.reservaservice.client.AgendaClient;
import com.cigna.reservaservice.client.UsuarioClient;
import com.cigna.reservaservice.dto.ReservaDTO;
import com.cigna.reservaservice.model.Reserva;
import com.cigna.reservaservice.model.Reserva.EstadoReserva;
import com.cigna.reservaservice.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    private final ReservaRepository reservaRepository;
    private final UsuarioClient usuarioClient;
    private final AgendaClient agendaClient;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioClient usuarioClient,
                          AgendaClient agendaClient) {
        this.reservaRepository = reservaRepository;
        this.usuarioClient = usuarioClient;
        this.agendaClient = agendaClient;
    }

    public List<ReservaDTO> listar() {
        log.info("Listando todas las reservas");
        return reservaRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDTO> listarPorUsuario(Long idUsuario) {
        log.info("Buscando reservas del usuario id={}", idUsuario);
        return reservaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public List<ReservaDTO> listarPorAgenda(Long idAgenda) {
        return reservaRepository.findByIdAgenda(idAgenda).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public ReservaDTO buscarPorId(Long id) {
        return reservaRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada con id: " + id));
    }

    // REQ 7: búsqueda por rango de fechas
    public List<ReservaDTO> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        log.info("Buscando reservas entre {} y {}", desde, hasta);
        return reservaRepository.findByFechaReservaBetween(desde, hasta).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    // REQ 7: total de reservas por usuario
    public Map<String, Object> totalPorUsuario(Long idUsuario) {
        long total = reservaRepository.contarPorUsuario(idUsuario);
        return Map.of("idUsuario", idUsuario, "totalReservas", total);
    }

    // REQ 7: total por estado
    public Map<String, Object> totalPorEstado(EstadoReserva estado) {
        long total = reservaRepository.countByEstado(estado);
        return Map.of("estado", estado, "total", total);
    }

    public ReservaDTO crear(ReservaDTO dto) {
        // REQ 5: valida usuario en usuario-service
        var usuario = usuarioClient.obtenerUsuario(dto.getIdUsuario());
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El usuario con id " + dto.getIdUsuario() + " no existe");
        }

        // REQ 5: valida agenda en agenda-service
        var agenda = agendaClient.obtenerAgenda(dto.getIdAgenda());
        if (agenda == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La agenda con id " + dto.getIdAgenda() + " no existe");
        }
        if (!"DISPONIBLE".equals(agenda.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La agenda no está disponible, estado actual: " + agenda.getEstado());
        }

        log.info("Creando reserva para usuario: {} {} en agenda id={}",
                usuario.getNombre(), usuario.getApellido(), dto.getIdAgenda());

        boolean agendaOcupada = reservaRepository.existsByIdAgendaAndEstadoNot(
                dto.getIdAgenda(), EstadoReserva.CANCELADA);
        if (agendaOcupada) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La agenda ya tiene una reserva activa");
        }

        Reserva reserva = toEntity(dto);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado(EstadoReserva.PENDIENTE);
        return toDto(reservaRepository.save(reserva));
    }

    public ReservaDTO cambiarEstado(Long id, EstadoReserva nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada con id: " + id));
        log.info("Cambiando estado de reserva id={} a {}", id, nuevoEstado);
        reserva.setEstado(nuevoEstado);
        return toDto(reservaRepository.save(reserva));
    }

    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada con id: " + id));
        if (reserva.getEstado() == EstadoReserva.COMPLETADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede cancelar una reserva ya completada");
        }
        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    private ReservaDTO toDto(Reserva r) {
        return new ReservaDTO(r.getIdReserva(), r.getIdAgenda(), r.getIdUsuario(),
                r.getFechaReserva(), r.getEstado(), r.getObservaciones());
    }

    private Reserva toEntity(ReservaDTO dto) {
        return new Reserva(dto.getIdReserva(), dto.getIdAgenda(), dto.getIdUsuario(),
                dto.getFechaReserva(), dto.getEstado(), dto.getObservaciones());
    }
}
