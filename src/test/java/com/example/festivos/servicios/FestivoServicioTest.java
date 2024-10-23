package com.example.festivos.servicios;

import com.example.festivos.aplicacion.FestivoServicio;
import com.example.festivos.core.dominio.Festivo;
import com.example.festivos.core.dominio.Tipo;
import com.example.festivos.core.interfaces.repositorios.IFestivoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FestivoServicioTest {

    @Mock
    private IFestivoRepositorio festivoRepositorio;

    private FestivoServicio festivoServicio;

    @BeforeEach
    void setUp() {
        festivoServicio = new FestivoServicio(festivoRepositorio);
    }

    @Test
    void listar_DeberiaRetornarListaDeFestivos() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        List<Festivo> festivos = Arrays.asList(
                new Festivo(1L, "Año Nuevo", 1, 1, 0, tipo),
                new Festivo(2L, "Reyes Magos", 6, 1, 0, tipo)
        );
        when(festivoRepositorio.findAll()).thenReturn(festivos);

        // Act
        List<Festivo> resultado = festivoServicio.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(festivoRepositorio).findAll();
    }

    @Test
    void obtener_CuandoExisteId_DeberiaRetornarFestivo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        Festivo festivo = new Festivo(1L, "Año Nuevo", 1, 1, 0, tipo);
        when(festivoRepositorio.findById(1L)).thenReturn(Optional.of(festivo));

        // Act
        Festivo resultado = festivoServicio.obtener(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Año Nuevo", resultado.getNombre());
        verify(festivoRepositorio).findById(1L);
    }

    @Test
    void obtener_CuandoNoExisteId_DeberiaRetornarNull() {
        // Arrange
        when(festivoRepositorio.findById(99L)).thenReturn(Optional.empty());

        // Act
        Festivo resultado = festivoServicio.obtener(99L);

        // Assert
        assertNull(resultado);
        verify(festivoRepositorio).findById(99L);
    }

    @Test
    void agregar_DeberiaGuardarFestivo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        Festivo festivo = new Festivo(1L, "Nuevo Festivo", 1, 1, 0, tipo);
        when(festivoRepositorio.save(any(Festivo.class))).thenReturn(festivo);

        // Act
        Festivo resultado = festivoServicio.agregar(festivo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Festivo", resultado.getNombre());
        verify(festivoRepositorio).save(festivo);
    }

    @Test
    void modificar_CuandoExisteFestivo_DeberiaActualizarlo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        Festivo festivo = new Festivo(1L, "Festivo Modificado", 1, 1, 0, tipo);
        when(festivoRepositorio.findById(1L)).thenReturn(Optional.of(festivo));
        when(festivoRepositorio.save(any(Festivo.class))).thenReturn(festivo);

        // Act
        Festivo resultado = festivoServicio.modificar(festivo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Festivo Modificado", resultado.getNombre());
        verify(festivoRepositorio).save(festivo);
    }

    @Test
    void eliminar_CuandoExisteFestivo_DeberiaRetornarTrue() {
        // Arrange
        doNothing().when(festivoRepositorio).deleteById(1L);

        // Act
        boolean resultado = festivoServicio.eliminar(1L);

        // Assert
        assertTrue(resultado);
        verify(festivoRepositorio).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExisteFestivo_DeberiaRetornarFalse() {
        // Arrange
        doThrow(EmptyResultDataAccessException.class).when(festivoRepositorio).deleteById(99L);

        // Act
        boolean resultado = festivoServicio.eliminar(99L);

        // Assert
        assertFalse(resultado);
        verify(festivoRepositorio).deleteById(99L);
    }

    @Test
    void diasFestivos_CuandoEsFestivo_DeberiaRetornarEsFestivo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        List<Festivo> festivos = Arrays.asList(
                new Festivo(1L, "Año Nuevo", 1, 1, 0, tipo)
        );
        when(festivoRepositorio.findAll()).thenReturn(festivos);

        // Act
        String resultado = festivoServicio.diasFestivos(2024, 1, 1);

        // Assert
        assertEquals("Es Festivo", resultado);
    }

    @Test
    void diasFestivos_CuandoNoEsFestivo_DeberiaRetornarNoEsFestivo() {
        // Arrange
        when(festivoRepositorio.findAll()).thenReturn(Arrays.asList());

        // Act
        String resultado = festivoServicio.diasFestivos(2024, 1, 2);

        // Assert
        assertEquals("No es festivo", resultado);
    }

    @Test
    void diasFestivos_CuandoFechaNoValida_DeberiaRetornarFechaNoValida() {
        // Act
        String resultado = festivoServicio.diasFestivos(2024, 13, 1);

        // Assert
        assertEquals("Fecha No valida", resultado);
    }
}
