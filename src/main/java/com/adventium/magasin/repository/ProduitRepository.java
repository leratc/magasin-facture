package com.adventium.magasin.repository;

import com.adventium.magasin.domain.LigneCommande;
import com.adventium.magasin.domain.Produit;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("select p from Produit p join fetch p.categorieProduit  where p.id in (:produitsId)")
    List<Produit> findAllByProduitsId(@Param("produitsId") List<Long> produitsId);
}
