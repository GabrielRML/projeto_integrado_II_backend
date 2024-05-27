package com.clinicallink.app.repository;

import com.clinicallink.app.domain.EspecialidadeEspecialista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EspecialidadeEspecialista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadeEspecialistaRepository
    extends JpaRepository<EspecialidadeEspecialista, Long>, JpaSpecificationExecutor<EspecialidadeEspecialista> {}
