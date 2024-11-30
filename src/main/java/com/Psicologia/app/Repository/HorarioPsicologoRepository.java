package com.Psicologia.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Psicologia.app.Entity.HorarioPsicologo;

import java.util.List;

public interface HorarioPsicologoRepository extends MongoRepository<HorarioPsicologo, String> {

    List<HorarioPsicologo> findByPsicologoId(String psicologoId);

    List<HorarioPsicologo> findByDia(String dia);
}
