package com.adventium.magasin.service;

import com.adventium.magasin.domain.CategorieProduit;
import com.adventium.magasin.repository.CategorieProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CategorieProduit}.
 */
@Service
@Transactional
public class CategorieProduitService {

    private final Logger log = LoggerFactory.getLogger(CategorieProduitService.class);

    private final CategorieProduitRepository categorieProduitRepository;

    public CategorieProduitService(CategorieProduitRepository categorieProduitRepository) {
        this.categorieProduitRepository = categorieProduitRepository;
    }

    /**
     * Save a categorieProduit.
     *
     * @param categorieProduit the entity to save.
     * @return the persisted entity.
     */
    public CategorieProduit save(CategorieProduit categorieProduit) {
        log.debug("Request to save CategorieProduit : {}", categorieProduit);
        return categorieProduitRepository.save(categorieProduit);
    }

    /**
     * Get all the categorieProduits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieProduit> findAll() {
        log.debug("Request to get all CategorieProduits");
        return categorieProduitRepository.findAll();
    }


    /**
     * Get one categorieProduit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategorieProduit> findOne(Long id) {
        log.debug("Request to get CategorieProduit : {}", id);
        return categorieProduitRepository.findById(id);
    }

    /**
     * Delete the categorieProduit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategorieProduit : {}", id);
        categorieProduitRepository.deleteById(id);
    }
}
