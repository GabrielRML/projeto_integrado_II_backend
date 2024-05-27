package com.clinicallink.app.repository;

import com.clinicallink.app.domain.Especialista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Especialista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialistaRepository extends JpaRepository<Especialista, Long>, JpaSpecificationExecutor<Especialista> {}
