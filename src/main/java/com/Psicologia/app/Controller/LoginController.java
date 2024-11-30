package com.Psicologia.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.Psicologia.app.Entity.Administrador;
import com.Psicologia.app.Entity.Cliente;
import com.Psicologia.app.Entity.Psicologo;
import com.Psicologia.app.Repository.AdministradorRepository;
import com.Psicologia.app.Repository.ClienteRepository;
import com.Psicologia.app.Repository.PsicologoRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    // Mostrar la página de login
    @GetMapping("/")
    public ModelAndView mostrarLogin() {
        return new ModelAndView("login");
    }

    // Autenticación de usuarios (administradores, clientes o psicólogos)
    @PostMapping("/authenticate")
    public ModelAndView autenticarUsuario(@ModelAttribute("correo") String correo,
                                          @ModelAttribute("cedula") String cedula) {
        ModelAndView modelAndView = new ModelAndView();

        // Verificar si es un administrador
        Administrador admin = administradorRepository.findByCorreo(correo);
        if (admin != null && admin.getPassword().equals(cedula)) {
            // Si es administrador, redirigir al menú de opciones
            modelAndView.setViewName("menuAdministrador"); // Redirige a una vista menuAdministrador.html
            modelAndView.addObject("mensaje", "Bienvenido, Administrador.");
            return modelAndView;
        }

     // Verificar si es un psicólogo
        Psicologo psicologo = psicologoRepository.findByCorreo(correo);
        if (psicologo != null && psicologo.getPassword().equals(cedula)) {
            // Redirigir al controlador del perfil del psicólogo
            return new ModelAndView("redirect:/psicologo/perfil?psicologoId=" + psicologo.getId());
        }

        // Si no es administrador ni psicólogo, verificar si es cliente
        Cliente cliente = clienteRepository.findByCorreo(correo);
        if (cliente != null && cliente.getCedula().equals(cedula)) {
            modelAndView.setViewName("bienvenida");
            modelAndView.addObject("mensaje", "Inicio de sesión exitoso.");
            modelAndView.addObject("cliente", cliente);
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("mensaje", "Correo o cédula incorrectos. Inténtalo de nuevo.");
        }

        return modelAndView;
    }
}
