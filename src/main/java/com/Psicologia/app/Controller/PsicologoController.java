package com.Psicologia.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Psicologia.app.Entity.Cita;
import com.Psicologia.app.Entity.Cliente;
import com.Psicologia.app.Entity.Psicologo;
import com.Psicologia.app.Repository.CitaRepository;
import com.Psicologia.app.Repository.ClienteRepository;
import com.Psicologia.app.Repository.PsicologoRepository;

@RestController
@RequestMapping("/psicologo")
public class PsicologoController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/perfil")
    public ModelAndView perfilPsicologo(@RequestParam String psicologoId) {
        System.out.println("Psicólogo ID recibido: " + psicologoId);

        Psicologo psicologo = psicologoRepository.findById(psicologoId).orElse(null);
        System.out.println("Psicólogo encontrado: " + psicologo);

        List<Cita> citas = citaRepository.findByPsicologoId(psicologoId);
        System.out.println("Citas encontradas para el psicólogo: " + citas);

        List<Map<String, String>> citasConDetalles = new ArrayList<>();
        for (Cita cita : citas) {
            Map<String, String> citaDetalle = new HashMap<>();
            Cliente cliente = clienteRepository.findById(cita.getClienteId()).orElse(null);

            System.out.println("Cliente encontrado: " + cliente);

            if (cliente != null) {
                citaDetalle.put("clienteNombre", cliente.getNombres() + " " + cliente.getApellidos());
                citaDetalle.put("estado", cita.getEstado());
                citaDetalle.put("fecha", cita.getFecha());
                citaDetalle.put("citaId", cita.getId());
                citasConDetalles.add(citaDetalle);
            }
        }

        System.out.println("Citas con detalles: " + citasConDetalles);

        ModelAndView modelAndView = new ModelAndView("perfilPsicologo");
        modelAndView.addObject("psicologo", psicologo);
        modelAndView.addObject("citas", citasConDetalles);
        return modelAndView;
    }


    @PostMapping("/cambiarEstadoCita")
    public ResponseEntity<String> cambiarEstadoCita(@RequestParam String citaId) {
        Cita cita = citaRepository.findById(citaId).orElse(null);
        if (cita == null) {
            return ResponseEntity.badRequest().body("Cita no encontrada");
        }

        // Cambiar el estado a "Realizada"
        cita.setEstado("Realizada");
        citaRepository.save(cita);

        return ResponseEntity.ok("Estado de la cita actualizado a 'Realizada'");
    }
}
