package com.Psicologia.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Psicologia.app.Entity.Cliente;
import com.Psicologia.app.Repository.ClienteRepository;


@RestController
@RequestMapping("/registro")
public class RegistroController {

	
	
	@Autowired
    private ClienteRepository clienteRepository;

    // Controlador para mostrar la página de inicio (index.html)
    @GetMapping("/")
    public ModelAndView mostrarInicio() {
        return new ModelAndView("index");
    }

    // Controlador para mostrar el formulario de registro de clientes
    @GetMapping("/res")
    public ModelAndView mostrarFormularioRegistro() {
        ModelAndView modelAndView = new ModelAndView("RegistroCliente"); // Muestra RegistroCliente.html
        modelAndView.addObject("cliente", new Cliente()); // Añade un objeto Cliente vacío para el formulario
        return modelAndView;
    }

    // Controlador para registrar el cliente
    @PostMapping("/clientes/registrar")
    public ModelAndView registrarCliente(@ModelAttribute Cliente cliente) {
        ModelAndView modelAndView = new ModelAndView("index");

        // Verificar si el correo ya está registrado
        Cliente existente = clienteRepository.findByCorreo(cliente.getCorreo());
        
        if (existente != null) {
            modelAndView.setViewName("RegistroCliente"); // Redirige de nuevo al formulario de registro
            modelAndView.addObject("mensaje", "Error: el correo ya está registrado.");
        } else {
            try {
                clienteRepository.save(cliente);
                modelAndView.addObject("mensaje", "Cliente registrado exitosamente.");
            } catch (Exception e) {
                modelAndView.addObject("mensaje", "Error al registrar al cliente: " + e.getMessage());
            }
        }

        return modelAndView;
    }
}
