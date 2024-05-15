package com.example.festivos.aplicacion;

import com.example.festivos.core.dominio.Festivo;
import com.example.festivos.core.interfaces.repositorios.IFestivoRepositorio;
import com.example.festivos.core.interfaces.servicios.IFestivoServicio;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FestivoServicio implements IFestivoServicio {

    private IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio){
        this.repositorio = repositorio;
    }

    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }

    @Override
    public Festivo obtener(long id) {
        var festivo = repositorio.findById(id);
        return festivo.isEmpty() ? null : festivo.get();
    }

    @Override
    public Festivo agregar(Festivo Festivo) {
        Festivo.setId(0);
        return repositorio.save(Festivo);
    }

    @Override
    public Festivo modificar(Festivo Festivo) {
        var festivoExistente = repositorio.findById(Festivo.getId());
        if(!festivoExistente.isEmpty()){
            return repositorio.save(Festivo);
        }
        return null;
    }

    @Override
    public boolean eliminar(long id) {
        try {
            repositorio.deleteById(id);
            return true; // Se eliminó correctamente
        } catch (EmptyResultDataAccessException e) {
            return false; // No se encontró el festivo con el ID especificado
        }
    }
}
