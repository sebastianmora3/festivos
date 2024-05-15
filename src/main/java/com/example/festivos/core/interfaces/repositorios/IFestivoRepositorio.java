package com.example.festivos.core.interfaces.repositorios;

import com.example.festivos.core.dominio.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFestivoRepositorio extends JpaRepository<Festivo, Long> {

}
