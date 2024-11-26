package com.Psicologia.app.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Pagos")
public class Pago {

    @Id
    private String id;
    private String clienteId;  // Referencia al ID del cliente
    private double monto;
    private String fecha;      // Fecha del pago
    private String descripcion; // Descripción del pago

    public Pago() {}

    public Pago(String clienteId, double monto, String fecha, String descripcion) {
        this.clienteId = clienteId;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    // Getters and Setters
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
