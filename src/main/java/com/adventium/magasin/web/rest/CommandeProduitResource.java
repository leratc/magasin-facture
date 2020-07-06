package com.adventium.magasin.web.rest;

import com.adventium.magasin.domain.CommandeProduit;
import com.adventium.magasin.service.CommandeProduitService;
import com.adventium.magasin.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adventium.magasin.domain.CommandeProduit}.
 */
@RestController
@RequestMapping("/api")
public class CommandeProduitResource {

    private final Logger log = LoggerFactory.getLogger(CommandeProduitResource.class);

    private static final String ENTITY_NAME = "commandeProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandeProduitService commandeProduitService;

    public CommandeProduitResource(CommandeProduitService commandeProduitService) {
        this.commandeProduitService = commandeProduitService;
    }

    /**
     * {@code POST  /commande-produits} : Create a new commandeProduit.
     *
     * @param commandeProduit the commandeProduit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandeProduit, or with status {@code 400 (Bad Request)} if the commandeProduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commande-produits")
    public ResponseEntity<CommandeProduit> createCommandeProduit(@Valid @RequestBody CommandeProduit commandeProduit) throws URISyntaxException {
        log.debug("REST request to save CommandeProduit : {}", commandeProduit);
        if (commandeProduit.getId() != null) {
            throw new BadRequestAlertException("A new commandeProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandeProduit result = commandeProduitService.save(commandeProduit);
        return ResponseEntity.created(new URI("/api/commande-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commande-produits} : Updates an existing commandeProduit.
     *
     * @param commandeProduit the commandeProduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandeProduit,
     * or with status {@code 400 (Bad Request)} if the commandeProduit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandeProduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commande-produits")
    public ResponseEntity<CommandeProduit> updateCommandeProduit(@Valid @RequestBody CommandeProduit commandeProduit) throws URISyntaxException {
        log.debug("REST request to update CommandeProduit : {}", commandeProduit);
        if (commandeProduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandeProduit result = commandeProduitService.save(commandeProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandeProduit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commande-produits} : get all the commandeProduits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandeProduits in body.
     */
    @GetMapping("/commande-produits")
    public ResponseEntity<List<CommandeProduit>> getAllCommandeProduits(Pageable pageable) {
        log.debug("REST request to get a page of CommandeProduits");
        Page<CommandeProduit> page = commandeProduitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commande-produits/:id} : get the "id" commandeProduit.
     *
     * @param id the id of the commandeProduit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandeProduit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commande-produits/{id}")
    public ResponseEntity<CommandeProduit> getCommandeProduit(@PathVariable Long id) {
        log.debug("REST request to get CommandeProduit : {}", id);
        Optional<CommandeProduit> commandeProduit = commandeProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandeProduit);
    }

    /**
     * {@code DELETE  /commande-produits/:id} : delete the "id" commandeProduit.
     *
     * @param id the id of the commandeProduit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commande-produits/{id}")
    public ResponseEntity<Void> deleteCommandeProduit(@PathVariable Long id) {
        log.debug("REST request to delete CommandeProduit : {}", id);
        commandeProduitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
