package com.Psicologia.app.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HorariosPsicologo")
public class HorarioPsicologo {

    @Id
    private String id;
    private String psicologoId;  // El ID del psicólogo asignado
    private String horarioId;    // El ID del horario (compatibilidad con código existente)
    private String dia;          // Día de la semana (ejemplo: "Lunes", "Martes", etc.)
    private String horaInicio;   // Hora de inicio del turno
    private String horaFin;      // Hora de cierre del turno

    public HorarioPsicologo() {}

    public HorarioPsicologo(String psicologoId, String horarioId, String dia, String horaInicio, String horaFin) {
        this.psicologoId = psicologoId;
        this.horarioId = horarioId;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsicologoId() {
        return psicologoId;
    }

    public void setPsicologoId(String psicologoId) {
        this.psicologoId = psicologoId;
    }

    public String getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(String horarioId) {
        this.horarioId = horarioId;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
