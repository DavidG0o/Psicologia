package com.Psicologia.app.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Citas")
public class Cita {

    @Id
    private String id;
    private String clienteId;
    private String psicologoId;
    private String horarioPsicologoId;
    private String estado;
    private String fecha;

    public Cita() {}

    public Cita(String clienteId, String psicologoId, String horarioPsicologoId, String estado, String fecha) {
        this.clienteId = clienteId;
        this.psicologoId = psicologoId;
        this.horarioPsicologoId = horarioPsicologoId;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getPsicologoId() {
        return psicologoId;
    }

    public void setPsicologoId(String psicologoId) {
        this.psicologoId = psicologoId;
    }

    public String getHorarioPsicologoId() {
        return horarioPsicologoId;
    }

    public void setHorarioPsicologoId(String horarioPsicologoId) {
        this.horarioPsicologoId = horarioPsicologoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
