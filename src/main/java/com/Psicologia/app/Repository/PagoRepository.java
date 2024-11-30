package com.Psicologia.app.Repository;

import com.Psicologia.app.Entity.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PagoRepository extends MongoRepository<Pago, String> {
    List<Pago> findByClienteId(String clienteId);  // Buscar pagos por cliente
}
