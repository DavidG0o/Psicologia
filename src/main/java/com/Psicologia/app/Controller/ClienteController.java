package com.Psicologia.app.Controller;


import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.Psicologia.app.Entity.Cita;
import com.Psicologia.app.Entity.Cliente;
import com.Psicologia.app.Entity.Horario;
import com.Psicologia.app.Entity.HorarioPsicologo;
import com.Psicologia.app.Entity.Psicologo;
import com.Psicologia.app.Repository.CitaRepository;
import com.Psicologia.app.Repository.ClienteRepository;
import com.Psicologia.app.Repository.HorarioPsicologoRepository;
import com.Psicologia.app.Repository.HorarioRepository;
import com.Psicologia.app.Repository.PsicologoRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private HorarioPsicologoRepository horarioPsicologoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/bienvenida")
    public ModelAndView mostrarBienvenida(@RequestParam String clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        List<Psicologo> psicologos = psicologoRepository.findByEstado("Activo");

        ModelAndView modelAndView = new ModelAndView("bienvenida");
        modelAndView.addObject("psicologos", psicologos);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @GetMapping("/agendarCitaForm")
    public ModelAndView mostrarFormularioAgendarCita(@RequestParam String clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        List<Psicologo> psicologos = psicologoRepository.findByEstado("Activo");

        ModelAndView modelAndView = new ModelAndView("agendarCitaForm");
        modelAndView.addObject("psicologos", psicologos);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @GetMapping("/horariosPorPsicologo")
    public List<Map<String, String>> obtenerHorariosPorPsicologo(@RequestParam String psicologoId) {
        List<HorarioPsicologo> horarios = horarioPsicologoRepository.findByPsicologoId(psicologoId);

        List<Map<String, String>> horariosFormateados = new ArrayList<>();
        for (HorarioPsicologo horario : horarios) {
            // Suponiendo que tienes una colección adicional para obtener detalles del horario
            Optional<Horario> horarioDetalle = horarioRepository.findById(horario.getHorarioId());
            if (horarioDetalle.isPresent()) {
                Map<String, String> detalle = new HashMap<>();
                detalle.put("id", horario.getId());
                detalle.put("dia", horarioDetalle.get().getDia());
                detalle.put("horaInicio", horarioDetalle.get().getHoraApertura());
                detalle.put("horaFin", horarioDetalle.get().getHoraCierre());
                horariosFormateados.add(detalle);
            }
        }
        return horariosFormateados;
    }

    @GetMapping("/verCitas")
    public ModelAndView verCitasProgramadas(@RequestParam String clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        List<Cita> citas = citaRepository.findByClienteId(clienteId);

        List<Map<String, String>> citasConDetalles = new ArrayList<>();
        for (Cita cita : citas) {
            Map<String, String> citaDetalle = new HashMap<>();
            Psicologo psicologo = psicologoRepository.findById(cita.getPsicologoId()).orElse(null);
            HorarioPsicologo horarioPsicologo = horarioPsicologoRepository.findById(cita.getHorarioPsicologoId()).orElse(null);

            if (psicologo != null && horarioPsicologo != null) {
                // Obtenemos los detalles del horario desde la entidad Horario
                Optional<Horario> horario = horarioRepository.findById(horarioPsicologo.getHorarioId());
                if (horario.isPresent()) {
                    citaDetalle.put("psicologoNombre", psicologo.getNombres() + " " + psicologo.getApellidos());
                    citaDetalle.put("horarioDetalle", horario.get().getDia() + " " + horario.get().getHoraApertura() + " - " + horario.get().getHoraCierre());
                    citaDetalle.put("fecha", cita.getFecha());
                    citaDetalle.put("estado", cita.getEstado());
                } else {
                    citaDetalle.put("horarioDetalle", "Horario no encontrado");
                }
            }
            citasConDetalles.add(citaDetalle);
        }

        ModelAndView modelAndView = new ModelAndView("verCitas");
        modelAndView.addObject("citas", citasConDetalles);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }


    @GetMapping("/calendarioCitas")
    public ModelAndView mostrarCalendario(@RequestParam String clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        ModelAndView modelAndView = new ModelAndView("calendarioCitas");
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }


    @GetMapping("/citasCalendario")
    public List<Map<String, String>> obtenerCitasCalendario(
            @RequestParam String clienteId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {

        // Filtra las citas por cliente ID
        List<Cita> citas = citaRepository.findByClienteId(clienteId);

        List<Map<String, String>> eventos = new ArrayList<>();
        for (Cita cita : citas) {
            Map<String, String> evento = new HashMap<>();
            Psicologo psicologo = psicologoRepository.findById(cita.getPsicologoId()).orElse(null);
            HorarioPsicologo horarioPsicologo = horarioPsicologoRepository.findById(cita.getHorarioPsicologoId()).orElse(null);

            if (psicologo != null && horarioPsicologo != null) {
                Optional<Horario> horario = horarioRepository.findById(horarioPsicologo.getHorarioId());
                horario.ifPresent(h -> {
                    evento.put("title", psicologo.getNombres() + " " + psicologo.getApellidos() + " (" + cita.getEstado() + ")");
                    evento.put("start", cita.getFecha() + "T" + h.getHoraApertura());
                    evento.put("end", cita.getFecha() + "T" + h.getHoraCierre());
                    evento.put("descripcion", "Horario: " + h.getDia() + " " + h.getHoraApertura() + " - " + h.getHoraCierre());
                });
            }
            eventos.add(evento);
        }
        return eventos;
    }



    
    @PostMapping("/agendarCita")
    public ModelAndView agendarCita(@RequestParam String clienteId,
                                    @RequestParam String psicologoId,
                                    @RequestParam String horarioPsicologoId,
                                    @RequestParam String fecha) {
        if (clienteId.isEmpty() || psicologoId.isEmpty() || horarioPsicologoId.isEmpty() || fecha.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        Cita cita = new Cita(clienteId, psicologoId, horarioPsicologoId, "Pendiente", fecha);
        citaRepository.save(cita);

        return new ModelAndView("redirect:/cliente/bienvenida?clienteId=" + clienteId);
    }
    
   


}

