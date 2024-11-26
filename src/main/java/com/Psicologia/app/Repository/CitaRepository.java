package com.Psicologia.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Psicologia.app.Entity.Cita;

import java.util.List;

public interface CitaRepository extends MongoRepository<Cita, String> {
    List<Cita> findByClienteId(String clienteId);
    List<Cita> findByPsicologoId(String psicologoId);
    List<Cita> findByHorarioPsicologoId(String horarioPsicologoId);
    
  
}
