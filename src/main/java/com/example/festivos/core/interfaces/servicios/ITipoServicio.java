package com.example.festivos.core.interfaces.servicios;

import com.example.festivos.core.dominio.Tipo;

import java.util.List;

public interface ITipoServicio {

    public List<Tipo> listar();

    public Tipo obtener(long id);

    public Tipo agregar(Tipo Tipo);

    public Tipo modificar(Tipo Tipo);

    public boolean eliminar(long id);

}
