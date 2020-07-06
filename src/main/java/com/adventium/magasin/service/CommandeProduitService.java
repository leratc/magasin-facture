package com.adventium.magasin.service;

import com.adventium.magasin.domain.CommandeProduit;
import com.adventium.magasin.repository.CommandeProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CommandeProduit}.
 */
@Service
@Transactional
public class CommandeProduitService {

    private final Logger log = LoggerFactory.getLogger(CommandeProduitService.class);

    private final CommandeProduitRepository commandeProduitRepository;

    public CommandeProduitService(CommandeProduitRepository commandeProduitRepository) {
        this.commandeProduitRepository = commandeProduitRepository;
    }

    /**
     * Save a commandeProduit.
     *
     * @param commandeProduit the entity to save.
     * @return the persisted entity.
     */
    public CommandeProduit save(CommandeProduit commandeProduit) {
        log.debug("Request to save CommandeProduit : {}", commandeProduit);
        return commandeProduitRepository.save(commandeProduit);
    }

    /**
     * Get all the commandeProduits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeProduit> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeProduits");
        return commandeProduitRepository.findAll(pageable);
    }


    /**
     * Get one commandeProduit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandeProduit> findOne(Long id) {
        log.debug("Request to get CommandeProduit : {}", id);
        return commandeProduitRepository.findById(id);
    }

    /**
     * Delete the commandeProduit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeProduit : {}", id);
        commandeProduitRepository.deleteById(id);
    }
}
