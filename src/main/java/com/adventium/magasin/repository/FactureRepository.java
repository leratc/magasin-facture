package com.adventium.magasin.repository;

import com.adventium.magasin.domain.Facture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Facture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
}
