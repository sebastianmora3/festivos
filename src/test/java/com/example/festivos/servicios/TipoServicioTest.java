package com.example.festivos.servicios;

import com.example.festivos.aplicacion.TipoServicio;
import com.example.festivos.core.dominio.Tipo;
import com.example.festivos.core.interfaces.repositorios.ITipoRepositorio;
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
class TipoServicioTest {

    @Mock
    private ITipoRepositorio tipoRepositorio;

    private TipoServicio tipoServicio;

    @BeforeEach
    void setUp() {
        tipoServicio = new TipoServicio(tipoRepositorio);
    }

    @Test
    void listar_DeberiaRetornarListaDeTipos() {
        // Arrange
        List<Tipo> tipos = Arrays.asList(
                new Tipo(1L, "Festivo Fijo"),
                new Tipo(2L, "Festivo Trasladable")
        );
        when(tipoRepositorio.findAll()).thenReturn(tipos);

        // Act
        List<Tipo> resultado = tipoServicio.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(tipoRepositorio).findAll();
    }

    @Test
    void obtener_CuandoExisteId_DeberiaRetornarTipo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Festivo Fijo");
        when(tipoRepositorio.findById(1L)).thenReturn(Optional.of(tipo));

        // Act
        Tipo resultado = tipoServicio.obtener(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Festivo Fijo", resultado.getTipo());
        verify(tipoRepositorio).findById(1L);
    }

    @Test
    void obtener_CuandoNoExisteId_DeberiaRetornarNull() {
        // Arrange
        when(tipoRepositorio.findById(99L)).thenReturn(Optional.empty());

        // Act
        Tipo resultado = tipoServicio.obtener(99L);

        // Assert
        assertNull(resultado);
        verify(tipoRepositorio).findById(99L);
    }

    @Test
    void agregar_DeberiaGuardarTipo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Nuevo Tipo");
        when(tipoRepositorio.save(any(Tipo.class))).thenReturn(tipo);

        // Act
        Tipo resultado = tipoServicio.agregar(tipo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Tipo", resultado.getTipo());
        verify(tipoRepositorio).save(tipo);
    }

    @Test
    void modificar_CuandoExisteTipo_DeberiaActualizarlo() {
        // Arrange
        Tipo tipo = new Tipo(1L, "Tipo Modificado");
        when(tipoRepositorio.findById(1L)).thenReturn(Optional.of(tipo));
        when(tipoRepositorio.save(any(Tipo.class))).thenReturn(tipo);

        // Act
        Tipo resultado = tipoServicio.modificar(tipo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Tipo Modificado", resultado.getTipo());
        verify(tipoRepositorio).save(tipo);
    }

    @Test
    void modificar_CuandoNoExisteTipo_DeberiaRetornarNull() {
        // Arrange
        Tipo tipo = new Tipo(99L, "Tipo Inexistente");
        when(tipoRepositorio.findById(99L)).thenReturn(Optional.empty());

        // Act
        Tipo resultado = tipoServicio.modificar(tipo);

        // Assert
        assertNull(resultado);
        verify(tipoRepositorio, never()).save(any(Tipo.class));
    }

    @Test
    void eliminar_CuandoExisteTipo_DeberiaRetornarTrue() {
        // Arrange
        doNothing().when(tipoRepositorio).deleteById(1L);

        // Act
        boolean resultado = tipoServicio.eliminar(1L);

        // Assert
        assertTrue(resultado);
        verify(tipoRepositorio).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExisteTipo_DeberiaRetornarFalse() {
        // Arrange
        doThrow(EmptyResultDataAccessException.class).when(tipoRepositorio).deleteById(99L);

        // Act
        boolean resultado = tipoServicio.eliminar(99L);

        // Assert
        assertFalse(resultado);
        verify(tipoRepositorio).deleteById(99L);
    }
}