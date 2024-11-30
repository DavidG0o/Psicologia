package com.Psicologia.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Psicologia.app.Entity.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String> {
    Administrador findByCorreo(String correo);
}
