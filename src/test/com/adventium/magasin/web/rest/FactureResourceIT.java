package com.adventium.magasin.web.rest;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.Facture;
import com.adventium.magasin.repository.FactureRepository;
import com.adventium.magasin.service.FactureService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adventium.magasin.domain.enumeration.StatutFacture;
import com.adventium.magasin.domain.enumeration.MethodePaiement;
/**
 * Integration tests for the {@link FactureResource} REST controller.
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FactureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final StatutFacture DEFAULT_STATUT = StatutFacture.PAYE;
    private static final StatutFacture UPDATED_STATUT = StatutFacture.EMISE;

    private static final MethodePaiement DEFAULT_METHODE_PAIEMENT = MethodePaiement.CARTE_CREDIT;
    private static final MethodePaiement UPDATED_METHODE_PAIEMENT = MethodePaiement.COMPTANT;

    private static final Instant DEFAULT_DATE_PAIEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PAIEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_MONTANT_PAIEMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PAIEMENT = new BigDecimal(2);

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureService factureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureMockMvc;

    private Facture facture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createEntity(EntityManager em) {
        Facture facture = new Facture()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE)
            .details(DEFAULT_DETAILS)
            .statut(DEFAULT_STATUT)
            .methodePaiement(DEFAULT_METHODE_PAIEMENT)
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .montantPaiement(DEFAULT_MONTANT_PAIEMENT);
        return facture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createUpdatedEntity(EntityManager em) {
        Facture facture = new Facture()
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS)
            .statut(UPDATED_STATUT)
            .methodePaiement(UPDATED_METHODE_PAIEMENT)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montantPaiement(UPDATED_MONTANT_PAIEMENT);
        return facture;
    }

    @BeforeEach
    public void initTest() {
        facture = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacture() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();
        // Create the Facture
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isCreated());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate + 1);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFacture.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFacture.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testFacture.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testFacture.getMethodePaiement()).isEqualTo(DEFAULT_METHODE_PAIEMENT);
        assertThat(testFacture.getDatePaiement()).isEqualTo(DEFAULT_DATE_PAIEMENT);
        assertThat(testFacture.getMontantPaiement()).isEqualTo(DEFAULT_MONTANT_PAIEMENT);
    }

    @Test
    @Transactional
    public void createFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // Create the Facture with an existing ID
        facture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setCode(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setDate(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setStatut(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodePaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setMethodePaiement(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setDatePaiement(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantPaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setMontantPaiement(null);

        // Create the Facture, which fails.


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFactures() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].methodePaiement").value(hasItem(DEFAULT_METHODE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(DEFAULT_DATE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].montantPaiement").value(hasItem(DEFAULT_MONTANT_PAIEMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", facture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facture.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.methodePaiement").value(DEFAULT_METHODE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.datePaiement").value(DEFAULT_DATE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.montantPaiement").value(DEFAULT_MONTANT_PAIEMENT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFacture() throws Exception {
        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacture() throws Exception {
        // Initialize the database
        factureService.save(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture
        Facture updatedFacture = factureRepository.findById(facture.getId()).get();
        // Disconnect from session so that the updates on updatedFacture are not directly saved in db
        em.detach(updatedFacture);
        updatedFacture
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS)
            .statut(UPDATED_STATUT)
            .methodePaiement(UPDATED_METHODE_PAIEMENT)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montantPaiement(UPDATED_MONTANT_PAIEMENT);

        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacture)))
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFacture.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFacture.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testFacture.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFacture.getMethodePaiement()).isEqualTo(UPDATED_METHODE_PAIEMENT);
        assertThat(testFacture.getDatePaiement()).isEqualTo(UPDATED_DATE_PAIEMENT);
        assertThat(testFacture.getMontantPaiement()).isEqualTo(UPDATED_MONTANT_PAIEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacture() throws Exception {
        // Initialize the database
        factureService.save(facture);

        int databaseSizeBeforeDelete = factureRepository.findAll().size();

        // Delete the facture
        restFactureMockMvc.perform(delete("/api/factures/{id}", facture.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
