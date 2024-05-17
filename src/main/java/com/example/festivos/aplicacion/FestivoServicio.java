package com.example.festivos.aplicacion;

import com.example.festivos.core.dominio.Festivo;
import com.example.festivos.core.interfaces.repositorios.IFestivoRepositorio;
import com.example.festivos.core.interfaces.servicios.IFestivoServicio;
import com.example.festivos.utils.Fecha;
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
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public String diasFestivos(int anio, int mes, int dia){

        String msg = "No es festivo";

        if( ! Fecha.validarFecha(anio, mes, dia)){
            return msg = "Fecha No valida";
        }

        var bd = repositorio.findAll();
        var diaPascua = Fecha.diaPascua(anio);

        for (Festivo festivo : bd){

            if(festivo.getTipo().getId() == 4L || festivo.getTipo().getId() == 3L){

                var x = Fecha.sumarDias(anio,diaPascua[1],diaPascua[0],festivo.getDiaPascua());
                festivo.setDia(x[0]);
                festivo.setMes(x[1]);
            }

            if (festivo.getTipo().getId() == 2L || festivo.getTipo().getId() == 4L){

                var x = Fecha.puenteFestivo(anio,festivo.getMes(),festivo.getDia());
                festivo.setDia(x[0]);
                festivo.setMes(x[1]);
            }

            if (festivo.getDia() == dia && festivo.getMes() == mes){
                return "Es Festivo";
            }
        }

        return msg;
    }

}
