package com.adventium.magasin.web.rest;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.CategorieProduit;
import com.adventium.magasin.repository.CategorieProduitRepository;
import com.adventium.magasin.service.CategorieProduitService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CategorieProduitResource} REST controller.
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategorieProduitResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAUX_TAXE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_TAXE = new BigDecimal(2);

    private static final Boolean DEFAULT_IMPORTE = false;
    private static final Boolean UPDATED_IMPORTE = true;

    @Autowired
    private CategorieProduitRepository categorieProduitRepository;

    @Autowired
    private CategorieProduitService categorieProduitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieProduitMockMvc;

    private CategorieProduit categorieProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieProduit createEntity(EntityManager em) {
        CategorieProduit categorieProduit = new CategorieProduit()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .tauxTaxe(DEFAULT_TAUX_TAXE)
            .importe(DEFAULT_IMPORTE);
        return categorieProduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieProduit createUpdatedEntity(EntityManager em) {
        CategorieProduit categorieProduit = new CategorieProduit()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .tauxTaxe(UPDATED_TAUX_TAXE)
            .importe(UPDATED_IMPORTE);
        return categorieProduit;
    }

    @BeforeEach
    public void initTest() {
        categorieProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieProduit() throws Exception {
        int databaseSizeBeforeCreate = categorieProduitRepository.findAll().size();
        // Create the CategorieProduit
        restCategorieProduitMockMvc.perform(post("/api/categorie-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieProduit)))
            .andExpect(status().isCreated());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCategorieProduit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategorieProduit.getTauxTaxe()).isEqualTo(DEFAULT_TAUX_TAXE);
        assertThat(testCategorieProduit.isImporte()).isEqualTo(DEFAULT_IMPORTE);
    }

    @Test
    @Transactional
    public void createCategorieProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieProduitRepository.findAll().size();

        // Create the CategorieProduit with an existing ID
        categorieProduit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieProduitMockMvc.perform(post("/api/categorie-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieProduit)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieProduitRepository.findAll().size();
        // set the field null
        categorieProduit.setNom(null);

        // Create the CategorieProduit, which fails.


        restCategorieProduitMockMvc.perform(post("/api/categorie-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieProduit)))
            .andExpect(status().isBadRequest());

        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieProduits() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        // Get all the categorieProduitList
        restCategorieProduitMockMvc.perform(get("/api/categorie-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tauxTaxe").value(hasItem(DEFAULT_TAUX_TAXE.intValue())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        // Get the categorieProduit
        restCategorieProduitMockMvc.perform(get("/api/categorie-produits/{id}", categorieProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieProduit.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.tauxTaxe").value(DEFAULT_TAUX_TAXE.intValue()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCategorieProduit() throws Exception {
        // Get the categorieProduit
        restCategorieProduitMockMvc.perform(get("/api/categorie-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitService.save(categorieProduit);

        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();

        // Update the categorieProduit
        CategorieProduit updatedCategorieProduit = categorieProduitRepository.findById(categorieProduit.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieProduit are not directly saved in db
        em.detach(updatedCategorieProduit);
        updatedCategorieProduit
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .tauxTaxe(UPDATED_TAUX_TAXE)
            .importe(UPDATED_IMPORTE);

        restCategorieProduitMockMvc.perform(put("/api/categorie-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorieProduit)))
            .andExpect(status().isOk());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCategorieProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategorieProduit.getTauxTaxe()).isEqualTo(UPDATED_TAUX_TAXE);
        assertThat(testCategorieProduit.isImporte()).isEqualTo(UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc.perform(put("/api/categorie-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorieProduit)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitService.save(categorieProduit);

        int databaseSizeBeforeDelete = categorieProduitRepository.findAll().size();

        // Delete the categorieProduit
        restCategorieProduitMockMvc.perform(delete("/api/categorie-produits/{id}", categorieProduit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
