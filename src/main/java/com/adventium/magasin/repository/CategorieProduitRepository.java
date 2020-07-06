package com.adventium.magasin.repository;

import com.adventium.magasin.domain.CategorieProduit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategorieProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long> {
}
