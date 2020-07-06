package com.adventium.magasin.repository;

import com.adventium.magasin.domain.CommandeProduit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommandeProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> {
}
