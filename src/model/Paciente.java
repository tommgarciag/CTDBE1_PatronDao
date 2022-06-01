package model;

import java.time.LocalDate;

public class Paciente {
    private int id;
    private String nombre;
    private int dni;
    private LocalDate fecha;
    private Domicilio domicilio;

    public Paciente(int id, String nombre, int dni, LocalDate fecha, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.fecha = fecha;
        this.domicilio = domicilio;
    }

    public Paciente(String nombre, int dni, LocalDate fecha, Domicilio domicilio) {
        this.nombre = nombre;
        this.dni = dni;
        this.fecha = fecha;
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Paciente {" +
                "id:" + id + ", " +
                "nombre" + nombre + ", " +
                "dni: " + dni + ", " +
                "fecha: " + fecha + ", " +
                "domicilio: " + domicilio +
                "}";
    }
}
