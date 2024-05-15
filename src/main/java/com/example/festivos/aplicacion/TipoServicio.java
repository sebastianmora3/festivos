package com.example.festivos.aplicacion;

import com.example.festivos.core.dominio.Tipo;
import com.example.festivos.core.interfaces.repositorios.ITipoRepositorio;
import com.example.festivos.core.interfaces.servicios.ITipoServicio;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServicio implements ITipoServicio {

    private ITipoRepositorio repositorio;

    public TipoServicio(ITipoRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> listar() {
        return repositorio.findAll();
    }

    @Override
    public Tipo obtener(long id) {
        var tipo = repositorio.findById(id);
        return tipo.isEmpty() ? null : tipo.get();
    }

    @Override
    public Tipo agregar(Tipo Tipo) {
        Tipo.setId(0);
        return repositorio.save(Tipo);
    }

    @Override
    public Tipo modificar(Tipo Tipo) {
        var tipoExistente = repositorio.findById(Tipo.getId());
        if(!tipoExistente.isEmpty()){
            return repositorio.save(Tipo);
        }
        return null;
    }

    @Override
    public boolean eliminar(long id) {
        try {
            repositorio.deleteById(id);
            return true; // Se eliminó correctamente
        } catch (EmptyResultDataAccessException e) {
            return false; // No se encontró el tipo con el ID especificado
        }
    }
}
