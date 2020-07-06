package com.adventium.magasin.web.rest;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.Client;
import com.adventium.magasin.domain.User;
import com.adventium.magasin.repository.ClientRepository;
import com.adventium.magasin.service.ClientService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adventium.magasin.domain.enumeration.Civilite;
/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Civilite DEFAULT_CIVILITE = Civilite.HOMME;
    private static final Civilite UPDATED_CIVILITE = Civilite.FEMME;

    private static final String DEFAULT_EMAIL = "\"eV<@hjTzH.c";
    private static final String UPDATED_EMAIL = "o4H(b@7zIG>`.K#";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_LIGNE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_LIGNE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_LIGNE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_LIGNE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .civilite(DEFAULT_CIVILITE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .addresseLigne1(DEFAULT_ADDRESSE_LIGNE_1)
            .addresseLigne2(DEFAULT_ADDRESSE_LIGNE_2)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        client.setUser(user);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .civilite(UPDATED_CIVILITE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .addresseLigne1(UPDATED_ADDRESSE_LIGNE_1)
            .addresseLigne2(UPDATED_ADDRESSE_LIGNE_2)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        client.setUser(user);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testClient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClient.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClient.getAddresseLigne1()).isEqualTo(DEFAULT_ADDRESSE_LIGNE_1);
        assertThat(testClient.getAddresseLigne2()).isEqualTo(DEFAULT_ADDRESSE_LIGNE_2);
        assertThat(testClient.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testClient.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPrenom(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setNom(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCiviliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setCivilite(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setEmail(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTelephone(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddresseLigne1IsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setAddresseLigne1(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setVille(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPays(null);

        // Create the Client, which fails.


        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].addresseLigne1").value(hasItem(DEFAULT_ADDRESSE_LIGNE_1)))
            .andExpect(jsonPath("$.[*].addresseLigne2").value(hasItem(DEFAULT_ADDRESSE_LIGNE_2)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.addresseLigne1").value(DEFAULT_ADDRESSE_LIGNE_1))
            .andExpect(jsonPath("$.addresseLigne2").value(DEFAULT_ADDRESSE_LIGNE_2))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS));
    }
    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .civilite(UPDATED_CIVILITE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .addresseLigne1(UPDATED_ADDRESSE_LIGNE_1)
            .addresseLigne2(UPDATED_ADDRESSE_LIGNE_2)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClient.getAddresseLigne1()).isEqualTo(UPDATED_ADDRESSE_LIGNE_1);
        assertThat(testClient.getAddresseLigne2()).isEqualTo(UPDATED_ADDRESSE_LIGNE_2);
        assertThat(testClient.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testClient.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientService.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
