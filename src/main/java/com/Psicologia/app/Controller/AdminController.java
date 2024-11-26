package com.Psicologia.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Psicologia.app.Entity.Cliente;
import com.Psicologia.app.Entity.Psicologo;
import com.Psicologia.app.Entity.Pago;
import com.Psicologia.app.Entity.Horario;
import com.Psicologia.app.Entity.HorarioPsicologo;
import com.Psicologia.app.Repository.ClienteRepository;
import com.Psicologia.app.Repository.PsicologoRepository;
import com.Psicologia.app.Repository.PagoRepository;
import com.Psicologia.app.Repository.HorarioRepository;
import com.Psicologia.app.Repository.HorarioPsicologoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private HorarioPsicologoRepository horarioPsicologoRepository;

    // Ver el menú de opciones del administrador
    @GetMapping("/menu")
    public ModelAndView verMenuAdministrador() {
        ModelAndView modelAndView = new ModelAndView("menuAdministrador");
        modelAndView.addObject("mensaje", "Bienvenido, Administrador.");
        return modelAndView;
    }

    // Mostrar el formulario para crear un nuevo psicólogo
    @GetMapping("/crearPsicologo")
    public ModelAndView mostrarFormularioCrearPsicologo() {
        ModelAndView modelAndView = new ModelAndView("crearPsicologo");
        modelAndView.addObject("psicologo", new Psicologo());
        return modelAndView;
    }

    // Guardar el nuevo psicólogo
    @PostMapping("/crearPsicologo")
    public ModelAndView crearPsicologo(@ModelAttribute Psicologo psicologo) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/menu");
        try {
            psicologoRepository.save(psicologo);
            modelAndView.addObject("mensaje", "Psicólogo creado exitosamente.");
        } catch (Exception e) {
            modelAndView.addObject("mensaje", "Error al crear el psicólogo: " + e.getMessage());
            modelAndView.setViewName("crearPsicologo");
        }
        return modelAndView;
    }

    // Ver la lista de psicólogos activos e inactivos
    @GetMapping("/verPsicologos")
    public ModelAndView verListaPsicologos() {
        List<Psicologo> psicologosActivos = psicologoRepository.findByEstado("Activo");
        List<Psicologo> psicologosInactivos = psicologoRepository.findByEstado("Inactivo");
        ModelAndView modelAndView = new ModelAndView("listaPsicologos");
        modelAndView.addObject("psicologosActivos", psicologosActivos);
        modelAndView.addObject("psicologosInactivos", psicologosInactivos);
        return modelAndView;
    }

    // Ver la lista de clientes activos e inactivos
    @GetMapping("/verClientes")
    public ModelAndView verListaClientes() {
        List<Cliente> clientesActivos = clienteRepository.findByEstado("Activo");
        List<Cliente> clientesInactivos = clienteRepository.findByEstado("Inactivo");
        ModelAndView modelAndView = new ModelAndView("listaClientes");
        modelAndView.addObject("clientesActivos", clientesActivos);
        modelAndView.addObject("clientesInactivos", clientesInactivos);
        return modelAndView;
    }

    // Desactivar un cliente (cambiar su estado a "Inactivo")
    @PostMapping("/desactivarCliente/{id}")
    public ModelAndView desactivarCliente(@PathVariable("id") String id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            cliente.setEstado("Inactivo");
            clienteRepository.save(cliente);
        }
        return new ModelAndView("redirect:/admin/verClientes");
    }

    // Desactivar un psicólogo (cambiar su estado a "Inactivo")
    @PostMapping("/desactivarPsicologo/{id}")
    public ModelAndView desactivarPsicologo(@PathVariable("id") String id) {
        Psicologo psicologo = psicologoRepository.findById(id).orElse(null);
        if (psicologo != null) {
            psicologo.setEstado("Inactivo");
            psicologoRepository.save(psicologo);
        }
        return new ModelAndView("redirect:/admin/verPsicologos");
    }

    // Activar un cliente (cambiar su estado a "Activo")
    @PostMapping("/activarCliente/{id}")
    public ModelAndView activarCliente(@PathVariable("id") String id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            cliente.setEstado("Activo");
            clienteRepository.save(cliente);
        }
        return new ModelAndView("redirect:/admin/verClientes");
    }

    // Activar un psicólogo (cambiar su estado a "Activo")
    @PostMapping("/activarPsicologo/{id}")
    public ModelAndView activarPsicologo(@PathVariable("id") String id) {
        Psicologo psicologo = psicologoRepository.findById(id).orElse(null);
        if (psicologo != null) {
            psicologo.setEstado("Activo");
            psicologoRepository.save(psicologo);
        }
        return new ModelAndView("redirect:/admin/verPsicologos");
    }

    // Mostrar el formulario de edición de psicólogo
    @GetMapping("/editarPsicologo/{id}")
    public ModelAndView mostrarFormularioEditarPsicologo(@PathVariable("id") String id) {
        Psicologo psicologo = psicologoRepository.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("editarPsicologo");
        modelAndView.addObject("psicologo", psicologo);
        return modelAndView;
    }

    // Actualizar el psicólogo editado
    @PostMapping("/editarPsicologo")
    public ModelAndView actualizarPsicologo(@ModelAttribute Psicologo psicologo) {
        psicologoRepository.save(psicologo);
        return new ModelAndView("redirect:/admin/verPsicologos");
    }

 // Mostrar el historial de pagos de un cliente
    @GetMapping("/verHistorialPagos/{clienteId}")
    public ModelAndView verHistorialPagos(@PathVariable("clienteId") String clienteId) {
        // Obtener los pagos del cliente usando su ID
    	List<Pago> historialPagos = pagoRepository.findByClienteId(clienteId);
        
        ModelAndView modelAndView = new ModelAndView("historialPagos");
        modelAndView.addObject("historialPagos", historialPagos);
        modelAndView.addObject("clienteId", clienteId);
        return modelAndView;
    }
    
 // Mostrar formulario para agregar pago
    @GetMapping("/agregarPago")
    public ModelAndView mostrarFormularioAgregarPago() {
        // Obtener todos los clientes activos
        List<Cliente> clientesActivos = clienteRepository.findByEstado("Activo");
        ModelAndView modelAndView = new ModelAndView("agregarPago");
        modelAndView.addObject("clientesActivos", clientesActivos);  // Pasamos la lista de clientes activos al modelo
        return modelAndView;
    }
    
    // Procesar el formulario de pago y guardar en la base de datos
    @PostMapping("/agregarPago")
    public ModelAndView agregarPago(@ModelAttribute Pago pago) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/menu");

        try {
            // Asignamos la fecha de pago si no fue proporcionada en el formulario (opcional)
            if (pago.getFecha() == null) {
                pago.setFecha(new Date().toString());  // Asignar la fecha actual si no se proporcionó
            }

            // Guardar el pago
            pagoRepository.save(pago);
            modelAndView.addObject("mensaje", "Pago agregado exitosamente.");
        } catch (Exception e) {
            modelAndView.addObject("mensaje", "Error al agregar el pago: " + e.getMessage());
            modelAndView.setViewName("agregarPago");
        }

        return modelAndView;
    }
    
    // Mostrar formulario para agregar horario
    @GetMapping("/agregarHorario")
    public ModelAndView mostrarFormularioAgregarHorario() {
        ModelAndView modelAndView = new ModelAndView("agregarHorario");
        return modelAndView;
    }

    // Procesar el formulario de horarios y guardar en la base de datos
    @PostMapping("/agregarHorario")
    public ModelAndView agregarHorario(@ModelAttribute Horario horario) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/menu");
        try {
            horarioRepository.save(horario);
            modelAndView.addObject("mensaje", "Horario agregado exitosamente.");
        } catch (Exception e) {
            modelAndView.addObject("mensaje", "Error al agregar el horario: " + e.getMessage());
            modelAndView.setViewName("agregarHorario");
        }
        return modelAndView;
    }

    // Ver los horarios del consultorio
    @GetMapping("/verHorarios")
    public ModelAndView verListaHorarios() {
        // Obtener todos los horarios
        List<Horario> horarios = horarioRepository.findAll();
        
        // Obtener los horarios y psicólogos asignados
        List<HorarioPsicologo> horariosPsicologo = horarioPsicologoRepository.findAll();
        
        // Crear un mapa que relacione el ID del horario con los psicólogos asignados
        Map<String, List<Psicologo>> psicologosPorHorario = new HashMap<>();
        
        // Relacionamos cada psicólogo con su horario correspondiente
        for (HorarioPsicologo horarioPsicologoItem : horariosPsicologo) {
            String horarioId = horarioPsicologoItem.getHorarioId();
            Psicologo psicologo = psicologoRepository.findById(horarioPsicologoItem.getPsicologoId()).orElse(null);
            if (psicologo != null) {
                psicologosPorHorario
                    .computeIfAbsent(horarioId, k -> new ArrayList<>())
                    .add(psicologo);
            }
        }

        // Preparar los datos para la vista
        ModelAndView modelAndView = new ModelAndView("listaHorarios");
        modelAndView.addObject("horarios", horarios);  // Pasar la lista de horarios
        modelAndView.addObject("psicologosPorHorario", psicologosPorHorario);  // Pasar los psicólogos asignados a cada horario
        return modelAndView;
    }

    // Editar horario de apertura y cierre
    @GetMapping("/editarHorario/{id}")
    public ModelAndView mostrarFormularioEditarHorario(@PathVariable("id") String id) {
        Horario horario = horarioRepository.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView("editarHorario");
        modelAndView.addObject("horario", horario);
        return modelAndView;
    }

    // Actualizar horario de apertura y cierre
    @PostMapping("/editarHorario")
    public ModelAndView actualizarHorario(@ModelAttribute Horario horario) {
        horarioRepository.save(horario);
        return new ModelAndView("redirect:/admin/verHorarios");
    }
    
    //mostrar el formulario de asignación de psicólogos a horarios.
    @GetMapping("/asignarPsicologoHorario")
    public ModelAndView mostrarFormularioAsignarPsicologoHorario() {
        List<Psicologo> psicologos = psicologoRepository.findByEstado("Activo"); // Obtener psicólogos activos
        List<Horario> horarios = horarioRepository.findAll(); // Obtener todos los horarios disponibles
        ModelAndView modelAndView = new ModelAndView("asignarPsicologoHorario");
        modelAndView.addObject("psicologos", psicologos); // Pasar psicólogos a la vista
        modelAndView.addObject("horarios", horarios); // Pasar horarios a la vista
        return modelAndView;
    }

    
    //Método para procesar la asignación.
    @PostMapping("/asignarPsicologoHorario")
    public ModelAndView asignarPsicologoAHorario(@ModelAttribute HorarioPsicologo horarioPsicologo) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/menu");
        try {
            // Asignar el psicólogo al horario específico
            horarioPsicologoRepository.save(horarioPsicologo);
            modelAndView.addObject("mensaje", "Psicólogo asignado al horario exitosamente.");
        } catch (Exception e) {
            modelAndView.addObject("mensaje", "Error al asignar el psicólogo: " + e.getMessage());
            modelAndView.setViewName("asignarPsicologoHorario");
        }
        return modelAndView;
    }
    
}
