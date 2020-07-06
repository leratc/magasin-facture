package com.adventium.magasin.repository;

import com.adventium.magasin.domain.LigneCommande;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LigneCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
    @Query("select lc from LigneCommande lc join CommandeProduit cp on cp.id=lc.commande.id join fetch lc.produit where cp.id=:commandeProduitId")
    List<LigneCommande> findAllByCommandeId(@Param("commandeProduitId") Long commandeProduitId);
}
