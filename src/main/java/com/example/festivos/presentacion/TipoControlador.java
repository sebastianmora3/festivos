package com.example.festivos.presentacion;

import com.example.festivos.core.dominio.Festivo;
import com.example.festivos.core.dominio.Tipo;
import com.example.festivos.core.interfaces.servicios.IFestivoServicio;
import com.example.festivos.core.interfaces.servicios.ITipoServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoControlador {

    private ITipoServicio servicio;

    public TipoControlador (ITipoServicio servicio){
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Tipo> listar(){
        return servicio.listar();
    }

    @RequestMapping(value="/obtener/{id}", method = RequestMethod.GET)
    public Tipo obtener(@PathVariable long id) {
        return servicio.obtener(id);
    }

    @RequestMapping(value="/agregar", method = RequestMethod.POST)
    public Tipo agregar(@RequestBody Tipo tipo) {
        return servicio.agregar(tipo);
    }

    @RequestMapping(value="/modificar", method = RequestMethod.PUT)
    public Tipo modificar(@RequestBody Tipo tipo) {
        return servicio.modificar(tipo);
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.GET)
    public boolean eliminar(@PathVariable long id) {
        return servicio.eliminar(id);
    }
}
