package com.Psicologia.app.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Psicologia.app.Entity.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    
    // Puedes definir métodos de consulta personalizados aquí si es necesario
    Cliente findByCedula(String cedula);
    Cliente findByCorreo(String correo);
    List<Cliente> findByEstado(String estado);
}