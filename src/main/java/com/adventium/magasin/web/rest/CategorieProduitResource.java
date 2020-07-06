package com.adventium.magasin.web.rest;

import com.adventium.magasin.domain.CategorieProduit;
import com.adventium.magasin.service.CategorieProduitService;
import com.adventium.magasin.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adventium.magasin.domain.CategorieProduit}.
 */
@RestController
@RequestMapping("/api")
public class CategorieProduitResource {

    private final Logger log = LoggerFactory.getLogger(CategorieProduitResource.class);

    private static final String ENTITY_NAME = "categorieProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieProduitService categorieProduitService;

    public CategorieProduitResource(CategorieProduitService categorieProduitService) {
        this.categorieProduitService = categorieProduitService;
    }

    /**
     * {@code POST  /categorie-produits} : Create a new categorieProduit.
     *
     * @param categorieProduit the categorieProduit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieProduit, or with status {@code 400 (Bad Request)} if the categorieProduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-produits")
    public ResponseEntity<CategorieProduit> createCategorieProduit(@Valid @RequestBody CategorieProduit categorieProduit) throws URISyntaxException {
        log.debug("REST request to save CategorieProduit : {}", categorieProduit);
        if (categorieProduit.getId() != null) {
            throw new BadRequestAlertException("A new categorieProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieProduit result = categorieProduitService.save(categorieProduit);
        return ResponseEntity.created(new URI("/api/categorie-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-produits} : Updates an existing categorieProduit.
     *
     * @param categorieProduit the categorieProduit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieProduit,
     * or with status {@code 400 (Bad Request)} if the categorieProduit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieProduit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-produits")
    public ResponseEntity<CategorieProduit> updateCategorieProduit(@Valid @RequestBody CategorieProduit categorieProduit) throws URISyntaxException {
        log.debug("REST request to update CategorieProduit : {}", categorieProduit);
        if (categorieProduit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieProduit result = categorieProduitService.save(categorieProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieProduit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorie-produits} : get all the categorieProduits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieProduits in body.
     */
    @GetMapping("/categorie-produits")
    public List<CategorieProduit> getAllCategorieProduits() {
        log.debug("REST request to get all CategorieProduits");
        return categorieProduitService.findAll();
    }

    /**
     * {@code GET  /categorie-produits/:id} : get the "id" categorieProduit.
     *
     * @param id the id of the categorieProduit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieProduit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-produits/{id}")
    public ResponseEntity<CategorieProduit> getCategorieProduit(@PathVariable Long id) {
        log.debug("REST request to get CategorieProduit : {}", id);
        Optional<CategorieProduit> categorieProduit = categorieProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieProduit);
    }

    /**
     * {@code DELETE  /categorie-produits/:id} : delete the "id" categorieProduit.
     *
     * @param id the id of the categorieProduit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-produits/{id}")
    public ResponseEntity<Void> deleteCategorieProduit(@PathVariable Long id) {
        log.debug("REST request to delete CategorieProduit : {}", id);
        categorieProduitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
