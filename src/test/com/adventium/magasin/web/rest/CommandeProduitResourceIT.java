package com.adventium.magasin.web.rest;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.CommandeProduit;
import com.adventium.magasin.domain.Client;
import com.adventium.magasin.repository.CommandeProduitRepository;
import com.adventium.magasin.service.CommandeProduitService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adventium.magasin.domain.enumeration.StatutCommande;
/**
 * Integration tests for the {@link CommandeProduitResource} REST controller.
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeProduitResourceIT {

    private static final Instant DEFAULT_DATE_COMMANDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_COMMANDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatutCommande DEFAULT_STATUT = StatutCommande.TERMINEE;
    private static final StatutCommande UPDATED_STATUT = StatutCommande.EN_COURS;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_FACTURE_ID = 1L;
    private static final Long UPDATED_FACTURE_ID = 2L;

    @Autowired
    private CommandeProduitRepository commandeProduitRepository;

    @Autowired
    private CommandeProduitService commandeProduitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeProduitMockMvc;

    private CommandeProduit commandeProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeProduit createEntity(EntityManager em) {
        CommandeProduit commandeProduit = new CommandeProduit()
            .dateCommande(DEFAULT_DATE_COMMANDE)
            .statut(DEFAULT_STATUT)
            .code(DEFAULT_CODE)
            .factureId(DEFAULT_FACTURE_ID);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        commandeProduit.setClient(client);
        return commandeProduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeProduit createUpdatedEntity(EntityManager em) {
        CommandeProduit commandeProduit = new CommandeProduit()
            .dateCommande(UPDATED_DATE_COMMANDE)
            .statut(UPDATED_STATUT)
            .code(UPDATED_CODE)
            .factureId(UPDATED_FACTURE_ID);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        commandeProduit.setClient(client);
        return commandeProduit;
    }

    @BeforeEach
    public void initTest() {
        commandeProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeProduit() throws Exception {
        int databaseSizeBeforeCreate = commandeProduitRepository.findAll().size();
        // Create the CommandeProduit
        restCommandeProduitMockMvc.perform(post("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isCreated());

        // Validate the CommandeProduit in the database
        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeProduit testCommandeProduit = commandeProduitList.get(commandeProduitList.size() - 1);
        assertThat(testCommandeProduit.getDateCommande()).isEqualTo(DEFAULT_DATE_COMMANDE);
        assertThat(testCommandeProduit.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testCommandeProduit.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCommandeProduit.getFactureId()).isEqualTo(DEFAULT_FACTURE_ID);
    }

    @Test
    @Transactional
    public void createCommandeProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeProduitRepository.findAll().size();

        // Create the CommandeProduit with an existing ID
        commandeProduit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeProduitMockMvc.perform(post("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeProduit in the database
        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateCommandeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeProduitRepository.findAll().size();
        // set the field null
        commandeProduit.setDateCommande(null);

        // Create the CommandeProduit, which fails.


        restCommandeProduitMockMvc.perform(post("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isBadRequest());

        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeProduitRepository.findAll().size();
        // set the field null
        commandeProduit.setStatut(null);

        // Create the CommandeProduit, which fails.


        restCommandeProduitMockMvc.perform(post("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isBadRequest());

        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandeProduitRepository.findAll().size();
        // set the field null
        commandeProduit.setCode(null);

        // Create the CommandeProduit, which fails.


        restCommandeProduitMockMvc.perform(post("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isBadRequest());

        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandeProduits() throws Exception {
        // Initialize the database
        commandeProduitRepository.saveAndFlush(commandeProduit);

        // Get all the commandeProduitList
        restCommandeProduitMockMvc.perform(get("/api/commande-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCommande").value(hasItem(DEFAULT_DATE_COMMANDE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].factureId").value(hasItem(DEFAULT_FACTURE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getCommandeProduit() throws Exception {
        // Initialize the database
        commandeProduitRepository.saveAndFlush(commandeProduit);

        // Get the commandeProduit
        restCommandeProduitMockMvc.perform(get("/api/commande-produits/{id}", commandeProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeProduit.getId().intValue()))
            .andExpect(jsonPath("$.dateCommande").value(DEFAULT_DATE_COMMANDE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.factureId").value(DEFAULT_FACTURE_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCommandeProduit() throws Exception {
        // Get the commandeProduit
        restCommandeProduitMockMvc.perform(get("/api/commande-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeProduit() throws Exception {
        // Initialize the database
        commandeProduitService.save(commandeProduit);

        int databaseSizeBeforeUpdate = commandeProduitRepository.findAll().size();

        // Update the commandeProduit
        CommandeProduit updatedCommandeProduit = commandeProduitRepository.findById(commandeProduit.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeProduit are not directly saved in db
        em.detach(updatedCommandeProduit);
        updatedCommandeProduit
            .dateCommande(UPDATED_DATE_COMMANDE)
            .statut(UPDATED_STATUT)
            .code(UPDATED_CODE)
            .factureId(UPDATED_FACTURE_ID);

        restCommandeProduitMockMvc.perform(put("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandeProduit)))
            .andExpect(status().isOk());

        // Validate the CommandeProduit in the database
        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeUpdate);
        CommandeProduit testCommandeProduit = commandeProduitList.get(commandeProduitList.size() - 1);
        assertThat(testCommandeProduit.getDateCommande()).isEqualTo(UPDATED_DATE_COMMANDE);
        assertThat(testCommandeProduit.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testCommandeProduit.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCommandeProduit.getFactureId()).isEqualTo(UPDATED_FACTURE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeProduit() throws Exception {
        int databaseSizeBeforeUpdate = commandeProduitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeProduitMockMvc.perform(put("/api/commande-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeProduit)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeProduit in the database
        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandeProduit() throws Exception {
        // Initialize the database
        commandeProduitService.save(commandeProduit);

        int databaseSizeBeforeDelete = commandeProduitRepository.findAll().size();

        // Delete the commandeProduit
        restCommandeProduitMockMvc.perform(delete("/api/commande-produits/{id}", commandeProduit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeProduit> commandeProduitList = commandeProduitRepository.findAll();
        assertThat(commandeProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
