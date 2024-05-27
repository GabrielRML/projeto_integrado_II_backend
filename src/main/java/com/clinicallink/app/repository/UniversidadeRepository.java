package com.clinicallink.app.repository;

import com.clinicallink.app.domain.Universidade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Universidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversidadeRepository extends JpaRepository<Universidade, Long>, JpaSpecificationExecutor<Universidade> {}
