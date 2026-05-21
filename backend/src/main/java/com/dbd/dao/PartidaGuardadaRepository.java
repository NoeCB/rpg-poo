package com.dbd.dao;

import com.dbd.model.PartidaGuardada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaGuardadaRepository extends JpaRepository<PartidaGuardada, Long> {
    List<PartidaGuardada> findByUsuarioId(Long usuarioId);
}
