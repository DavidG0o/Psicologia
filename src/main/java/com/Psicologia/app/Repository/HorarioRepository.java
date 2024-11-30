package com.Psicologia.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Psicologia.app.Entity.Horario;

public interface HorarioRepository extends MongoRepository<Horario, String> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
}
