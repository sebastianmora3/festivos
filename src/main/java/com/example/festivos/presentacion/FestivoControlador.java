package com.example.festivos.presentacion;

import com.example.festivos.core.dominio.Festivo;
import com.example.festivos.core.interfaces.servicios.IFestivoServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {

    private IFestivoServicio servicio;

    public FestivoControlador (IFestivoServicio servicio){
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivo> listar(){
        return servicio.listar();
    }

    @RequestMapping(value="/obtener/{id}", method = RequestMethod.GET)
    public Festivo obtener(@PathVariable long id) {
        return servicio.obtener(id);
    }

    @RequestMapping(value="/agregar", method = RequestMethod.POST)
    public Festivo agregar(@RequestBody Festivo festivo) {
        return servicio.agregar(festivo);
    }

    @RequestMapping(value="/modificar", method = RequestMethod.PUT)
    public Festivo modificar(@RequestBody Festivo festivo) {
        return servicio.modificar(festivo);
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.GET)
    public boolean eliminar(@PathVariable long id) {
        return servicio.eliminar(id);
    }
}
