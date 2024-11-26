package com.Psicologia.app.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Psicologia.app.Entity.Psicologo;

public interface PsicologoRepository extends MongoRepository<Psicologo, String> {
    Psicologo findByCorreo(String correo);
    List<Psicologo> findByEstado(String estado);
}
