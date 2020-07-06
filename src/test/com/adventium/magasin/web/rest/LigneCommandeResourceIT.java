package com.adventium.magasin.web.rest;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.LigneCommande;
import com.adventium.magasin.domain.Produit;
import com.adventium.magasin.domain.CommandeProduit;
import com.adventium.magasin.repository.LigneCommandeRepository;
import com.adventium.magasin.service.LigneCommandeService;

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

import com.adventium.magasin.domain.enumeration.StatutLigneCommande;
/**
 * Integration tests for the {@link LigneCommandeResource} REST controller.
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LigneCommandeResourceIT {

    private static final Integer DEFAULT_QUANTITE = 0;
    private static final Integer UPDATED_QUANTITE = 1;

    private static final BigDecimal DEFAULT_PRIX_TOTAL_HT = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRIX_TOTAL_HT = new BigDecimal(1);

    private static final StatutLigneCommande DEFAULT_STATUT = StatutLigneCommande.DISPONIBLE;
    private static final StatutLigneCommande UPDATED_STATUT = StatutLigneCommande.EN_RUPTURE;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Autowired
    private LigneCommandeService ligneCommandeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLigneCommandeMockMvc;

    private LigneCommande ligneCommande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCommande createEntity(EntityManager em) {
        LigneCommande ligneCommande = new LigneCommande()
            .quantite(DEFAULT_QUANTITE)
            .prixTotalHT(DEFAULT_PRIX_TOTAL_HT)
            .statut(DEFAULT_STATUT);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        ligneCommande.setProduit(produit);
        // Add required entity
        CommandeProduit commandeProduit;
        if (TestUtil.findAll(em, CommandeProduit.class).isEmpty()) {
            commandeProduit = CommandeProduitResourceIT.createEntity(em);
            em.persist(commandeProduit);
            em.flush();
        } else {
            commandeProduit = TestUtil.findAll(em, CommandeProduit.class).get(0);
        }
        ligneCommande.setCommande(commandeProduit);
        return ligneCommande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCommande createUpdatedEntity(EntityManager em) {
        LigneCommande ligneCommande = new LigneCommande()
            .quantite(UPDATED_QUANTITE)
            .prixTotalHT(UPDATED_PRIX_TOTAL_HT)
            .statut(UPDATED_STATUT);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createUpdatedEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        ligneCommande.setProduit(produit);
        // Add required entity
        CommandeProduit commandeProduit;
        if (TestUtil.findAll(em, CommandeProduit.class).isEmpty()) {
            commandeProduit = CommandeProduitResourceIT.createUpdatedEntity(em);
            em.persist(commandeProduit);
            em.flush();
        } else {
            commandeProduit = TestUtil.findAll(em, CommandeProduit.class).get(0);
        }
        ligneCommande.setCommande(commandeProduit);
        return ligneCommande;
    }

    @BeforeEach
    public void initTest() {
        ligneCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneCommande() throws Exception {
        int databaseSizeBeforeCreate = ligneCommandeRepository.findAll().size();
        // Create the LigneCommande
        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isCreated());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        LigneCommande testLigneCommande = ligneCommandeList.get(ligneCommandeList.size() - 1);
        assertThat(testLigneCommande.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testLigneCommande.getPrixTotalHT()).isEqualTo(DEFAULT_PRIX_TOTAL_HT);
        assertThat(testLigneCommande.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createLigneCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneCommandeRepository.findAll().size();

        // Create the LigneCommande with an existing ID
        ligneCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneCommandeRepository.findAll().size();
        // set the field null
        ligneCommande.setQuantite(null);

        // Create the LigneCommande, which fails.


        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixTotalHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneCommandeRepository.findAll().size();
        // set the field null
        ligneCommande.setPrixTotalHT(null);

        // Create the LigneCommande, which fails.


        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneCommandeRepository.findAll().size();
        // set the field null
        ligneCommande.setStatut(null);

        // Create the LigneCommande, which fails.


        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLigneCommandes() throws Exception {
        // Initialize the database
        ligneCommandeRepository.saveAndFlush(ligneCommande);

        // Get all the ligneCommandeList
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].prixTotalHT").value(hasItem(DEFAULT_PRIX_TOTAL_HT.intValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }
    
    @Test
    @Transactional
    public void getLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeRepository.saveAndFlush(ligneCommande);

        // Get the ligneCommande
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes/{id}", ligneCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneCommande.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.prixTotalHT").value(DEFAULT_PRIX_TOTAL_HT.intValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLigneCommande() throws Exception {
        // Get the ligneCommande
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeService.save(ligneCommande);

        int databaseSizeBeforeUpdate = ligneCommandeRepository.findAll().size();

        // Update the ligneCommande
        LigneCommande updatedLigneCommande = ligneCommandeRepository.findById(ligneCommande.getId()).get();
        // Disconnect from session so that the updates on updatedLigneCommande are not directly saved in db
        em.detach(updatedLigneCommande);
        updatedLigneCommande
            .quantite(UPDATED_QUANTITE)
            .prixTotalHT(UPDATED_PRIX_TOTAL_HT)
            .statut(UPDATED_STATUT);

        restLigneCommandeMockMvc.perform(put("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneCommande)))
            .andExpect(status().isOk());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeUpdate);
        LigneCommande testLigneCommande = ligneCommandeList.get(ligneCommandeList.size() - 1);
        assertThat(testLigneCommande.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testLigneCommande.getPrixTotalHT()).isEqualTo(UPDATED_PRIX_TOTAL_HT);
        assertThat(testLigneCommande.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneCommande() throws Exception {
        int databaseSizeBeforeUpdate = ligneCommandeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCommandeMockMvc.perform(put("/api/ligne-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeService.save(ligneCommande);

        int databaseSizeBeforeDelete = ligneCommandeRepository.findAll().size();

        // Delete the ligneCommande
        restLigneCommandeMockMvc.perform(delete("/api/ligne-commandes/{id}", ligneCommande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
