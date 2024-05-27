package com.clinicallink.app.repository;

import com.clinicallink.app.domain.Especialidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Especialidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>, JpaSpecificationExecutor<Especialidade> {}
