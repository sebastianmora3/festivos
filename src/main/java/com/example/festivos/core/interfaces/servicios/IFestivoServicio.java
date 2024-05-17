package com.example.festivos.core.interfaces.servicios;

import com.example.festivos.core.dominio.Festivo;

import java.util.List;

public interface IFestivoServicio {

    public List<Festivo> listar();

    public Festivo obtener(long id);

    public Festivo agregar(Festivo Festivo);

    public Festivo modificar(Festivo Festivo);

    public boolean eliminar(long id);

    public String diasFestivos(int anio, int mes, int dia);
}
