package com.cigna.agendaservice.service;

import com.cigna.agendaservice.client.ServicioClient;
import com.cigna.agendaservice.dto.AgendaDTO;
import com.cigna.agendaservice.model.Agenda;
import com.cigna.agendaservice.model.Agenda.EstadoAgenda;
import com.cigna.agendaservice.repository.AgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService implements IAgendaService {

    private static final Logger log = LoggerFactory.getLogger(AgendaService.class);

    private final AgendaRepository agendaRepository;
    private final ServicioClient servicioClient;

    public AgendaService(AgendaRepository agendaRepository, ServicioClient servicioClient) {
        this.agendaRepository = agendaRepository;
        this.servicioClient = servicioClient;
    }

    public List<AgendaDTO> listar() {
        log.info("Listando todas las agendas");
        return agendaRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<AgendaDTO> listarDisponibles() {
        return agendaRepository.findByEstado(EstadoAgenda.DISPONIBLE).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public List<AgendaDTO> listarPorUsuario(Long idUsuario) {
        return agendaRepository.findByIdUsuario(idUsuario).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public List<AgendaDTO> listarPorServicio(Long idServicio) {
        log.info("Buscando agendas para servicio id={}", idServicio);
        return agendaRepository.findByIdServicio(idServicio).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public List<AgendaDTO> listarPorFecha(LocalDate fecha, EstadoAgenda estado) {
        if (estado != null) {
            return agendaRepository.findByFechaAndEstado(fecha, estado).stream()
                    .map(this::toDto).collect(Collectors.toList());
        }
        return agendaRepository.findByFechaBetween(fecha, fecha).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    // REQ 7: búsqueda por rango de fechas
    public List<AgendaDTO> listarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        log.info("Buscando agendas entre {} y {}", desde, hasta);
        return agendaRepository.findByFechaBetween(desde, hasta).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    // REQ 7: total de agendas por servicio
    public long contarPorServicio(Long idServicio) {
        return agendaRepository.findByIdServicio(idServicio).size();
    }

    public AgendaDTO buscarPorId(Long id) {
        return agendaRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda no encontrada con id: " + id));
    }

    public AgendaDTO crear(AgendaDTO dto) {
        // REQ 6: comunica con servicio-service para validar que el servicio existe
        var servicio = servicioClient.obtenerServicio(dto.getIdServicio());
        if (servicio == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El servicio con id " + dto.getIdServicio() + " no existe");
        }
        log.info("Creando agenda para servicio: {}", servicio.getNombre());
        Agenda agenda = toEntity(dto);
        agenda.setEstado(EstadoAgenda.DISPONIBLE);
        return toDto(agendaRepository.save(agenda));
    }

    public AgendaDTO actualizar(Long id, AgendaDTO dto) {
        Agenda existente = agendaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda no encontrada con id: " + id));
        existente.setIdUsuario(dto.getIdUsuario());
        existente.setIdServicio(dto.getIdServicio());
        existente.setFecha(dto.getFecha());
        existente.setHoraInicio(dto.getHoraInicio());
        existente.setHoraFin(dto.getHoraFin());
        return toDto(agendaRepository.save(existente));
    }

    public AgendaDTO cambiarEstado(Long id, EstadoAgenda nuevoEstado) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda no encontrada con id: " + id));
        agenda.setEstado(nuevoEstado);
        return toDto(agendaRepository.save(agenda));
    }

    public void eliminar(Long id) {
        if (!agendaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda no encontrada con id: " + id);
        }
        agendaRepository.deleteById(id);
    }

    private AgendaDTO toDto(Agenda a) {
        return new AgendaDTO(a.getIdAgenda(), a.getIdUsuario(), a.getIdServicio(),
                a.getFecha(), a.getHoraInicio(), a.getHoraFin(), a.getEstado());
    }

    private Agenda toEntity(AgendaDTO dto) {
        return new Agenda(dto.getIdAgenda(), dto.getIdUsuario(), dto.getIdServicio(),
                dto.getFecha(), dto.getHoraInicio(), dto.getHoraFin(),
                dto.getEstado() != null ? dto.getEstado() : EstadoAgenda.DISPONIBLE);
    }
}
