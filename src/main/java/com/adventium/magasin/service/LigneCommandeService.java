package com.adventium.magasin.service;

import com.adventium.magasin.domain.LigneCommande;
import com.adventium.magasin.repository.LigneCommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LigneCommande}.
 */
@Service
@Transactional
public class LigneCommandeService {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeService.class);

    private final LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommande the entity to save.
     * @return the persisted entity.
     */
    public LigneCommande save(LigneCommande ligneCommande) {
        log.debug("Request to save LigneCommande : {}", ligneCommande);
        return ligneCommandeRepository.save(ligneCommande);
    }
    /**
     * Get all the ligneCommandes from commandeProduit
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
/*    @Transactional(readOnly = true)
    public LigneCommande findByCommandeProduit(Long commandeId) {
        log.debug("Request to get all LigneCommandes");
        return ligneCommandeRepository.findAll(commandeId);
    }*/

    /**
     * Get all the ligneCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LigneCommande> findAll(Pageable pageable) {
        log.debug("Request to get all LigneCommandes");
        return ligneCommandeRepository.findAll(pageable);
    }


    /**
     * Get one ligneCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LigneCommande> findOne(Long id) {
        log.debug("Request to get LigneCommande : {}", id);
        return ligneCommandeRepository.findById(id);
    }

    /**
     * Delete the ligneCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LigneCommande : {}", id);
        ligneCommandeRepository.deleteById(id);
    }
}
