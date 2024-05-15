package com.example.festivos.core.dominio;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "festivo")

public class Festivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_festivo")
    @GenericGenerator(name = "secuencia_feestivo", strategy = "increment")
    @Column(name = "id")
    private long id;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "dia")
    private int dia;

    @Column(name = "mes")
    private int mes;

    @Column(name = "diaspascua")
    private int diaPascua;

    @ManyToOne
    @JoinColumn(name = "idtipo", referencedColumnName = "id")
    private Tipo tipo;

    public Festivo() {

    }

    public Festivo(long id, String nombre, int dia, int mes, int diaPascua, Tipo tipo) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diaPascua = diaPascua;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDiaPascua() {
        return diaPascua;
    }

    public void setDiaPascua(int diaPascua) {
        this.diaPascua = diaPascua;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
