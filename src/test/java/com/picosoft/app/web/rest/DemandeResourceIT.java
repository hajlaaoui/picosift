package com.picosoft.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.picosoft.app.IntegrationTest;
import com.picosoft.app.domain.Demande;
import com.picosoft.app.domain.enumeration.Statut;
import com.picosoft.app.repository.DemandeRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DemandeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeResourceIT {

    private static final String DEFAULT_REF_SITE_WEB = "AAAAAAAAAA";
    private static final String UPDATED_REF_SITE_WEB = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEDESICION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDESICION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IMPORTATEUR = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_REF_BF = "AAAAAAAAAA";
    private static final String UPDATED_REF_BF = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_IMEI_1 = "AAAAAAAAAA";
    private static final String UPDATED_IMEI_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IMEI_2 = "AAAAAAAAAA";
    private static final String UPDATED_IMEI_2 = "BBBBBBBBBB";

    private static final String DEFAULT_IMEI_3 = "AAAAAAAAAA";
    private static final String UPDATED_IMEI_3 = "BBBBBBBBBB";

    private static final Statut DEFAULT_STATUT = Statut.EN_COURS;
    private static final Statut UPDATED_STATUT = Statut.ACCEPTER;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEPOT_SITE_WEB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPOT_SITE_WEB = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EXPORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPORT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demandes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeMockMvc;

    private Demande demande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demande createEntity(EntityManager em) {
        Demande demande = new Demande()
            .refSiteWeb(DEFAULT_REF_SITE_WEB)
            .datedesicion(DEFAULT_DATEDESICION)
            .importateur(DEFAULT_IMPORTATEUR)
            .refBF(DEFAULT_REF_BF)
            .marque(DEFAULT_MARQUE)
            .modele(DEFAULT_MODELE)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .imei1(DEFAULT_IMEI_1)
            .imei2(DEFAULT_IMEI_2)
            .imei3(DEFAULT_IMEI_3)
            .statut(DEFAULT_STATUT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateDepotSiteWeb(DEFAULT_DATE_DEPOT_SITE_WEB)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .dateExport(DEFAULT_DATE_EXPORT)
            .commentaire(DEFAULT_COMMENTAIRE);
        return demande;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demande createUpdatedEntity(EntityManager em) {
        Demande demande = new Demande()
            .refSiteWeb(UPDATED_REF_SITE_WEB)
            .datedesicion(UPDATED_DATEDESICION)
            .importateur(UPDATED_IMPORTATEUR)
            .refBF(UPDATED_REF_BF)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .imei1(UPDATED_IMEI_1)
            .imei2(UPDATED_IMEI_2)
            .imei3(UPDATED_IMEI_3)
            .statut(UPDATED_STATUT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDepotSiteWeb(UPDATED_DATE_DEPOT_SITE_WEB)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .dateExport(UPDATED_DATE_EXPORT)
            .commentaire(UPDATED_COMMENTAIRE);
        return demande;
    }

    @BeforeEach
    public void initTest() {
        demande = createEntity(em);
    }

    @Test
    @Transactional
    void createDemande() throws Exception {
        int databaseSizeBeforeCreate = demandeRepository.findAll().size();
        // Create the Demande
        restDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isCreated());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeCreate + 1);
        Demande testDemande = demandeList.get(demandeList.size() - 1);
        assertThat(testDemande.getRefSiteWeb()).isEqualTo(DEFAULT_REF_SITE_WEB);
        assertThat(testDemande.getDatedesicion()).isEqualTo(DEFAULT_DATEDESICION);
        assertThat(testDemande.getImportateur()).isEqualTo(DEFAULT_IMPORTATEUR);
        assertThat(testDemande.getRefBF()).isEqualTo(DEFAULT_REF_BF);
        assertThat(testDemande.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testDemande.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testDemande.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testDemande.getImei1()).isEqualTo(DEFAULT_IMEI_1);
        assertThat(testDemande.getImei2()).isEqualTo(DEFAULT_IMEI_2);
        assertThat(testDemande.getImei3()).isEqualTo(DEFAULT_IMEI_3);
        assertThat(testDemande.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemande.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDemande.getDateDepotSiteWeb()).isEqualTo(DEFAULT_DATE_DEPOT_SITE_WEB);
        assertThat(testDemande.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testDemande.getDateExport()).isEqualTo(DEFAULT_DATE_EXPORT);
        assertThat(testDemande.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    void createDemandeWithExistingId() throws Exception {
        // Create the Demande with an existing ID
        demande.setId(1L);

        int databaseSizeBeforeCreate = demandeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isBadRequest());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRefSiteWebIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeRepository.findAll().size();
        // set the field null
        demande.setRefSiteWeb(null);

        // Create the Demande, which fails.

        restDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isBadRequest());

        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommentaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeRepository.findAll().size();
        // set the field null
        demande.setCommentaire(null);

        // Create the Demande, which fails.

        restDemandeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isBadRequest());

        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandes() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        // Get all the demandeList
        restDemandeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demande.getId().intValue())))
            .andExpect(jsonPath("$.[*].refSiteWeb").value(hasItem(DEFAULT_REF_SITE_WEB)))
            .andExpect(jsonPath("$.[*].datedesicion").value(hasItem(DEFAULT_DATEDESICION.toString())))
            .andExpect(jsonPath("$.[*].importateur").value(hasItem(DEFAULT_IMPORTATEUR)))
            .andExpect(jsonPath("$.[*].refBF").value(hasItem(DEFAULT_REF_BF)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].modele").value(hasItem(DEFAULT_MODELE)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].imei1").value(hasItem(DEFAULT_IMEI_1)))
            .andExpect(jsonPath("$.[*].imei2").value(hasItem(DEFAULT_IMEI_2)))
            .andExpect(jsonPath("$.[*].imei3").value(hasItem(DEFAULT_IMEI_3)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateDepotSiteWeb").value(hasItem(DEFAULT_DATE_DEPOT_SITE_WEB.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(DEFAULT_DATE_VALIDATION.toString())))
            .andExpect(jsonPath("$.[*].dateExport").value(hasItem(DEFAULT_DATE_EXPORT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }

    @Test
    @Transactional
    void getDemande() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        // Get the demande
        restDemandeMockMvc
            .perform(get(ENTITY_API_URL_ID, demande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demande.getId().intValue()))
            .andExpect(jsonPath("$.refSiteWeb").value(DEFAULT_REF_SITE_WEB))
            .andExpect(jsonPath("$.datedesicion").value(DEFAULT_DATEDESICION.toString()))
            .andExpect(jsonPath("$.importateur").value(DEFAULT_IMPORTATEUR))
            .andExpect(jsonPath("$.refBF").value(DEFAULT_REF_BF))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.modele").value(DEFAULT_MODELE))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.imei1").value(DEFAULT_IMEI_1))
            .andExpect(jsonPath("$.imei2").value(DEFAULT_IMEI_2))
            .andExpect(jsonPath("$.imei3").value(DEFAULT_IMEI_3))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateDepotSiteWeb").value(DEFAULT_DATE_DEPOT_SITE_WEB.toString()))
            .andExpect(jsonPath("$.dateValidation").value(DEFAULT_DATE_VALIDATION.toString()))
            .andExpect(jsonPath("$.dateExport").value(DEFAULT_DATE_EXPORT.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    @Transactional
    void getNonExistingDemande() throws Exception {
        // Get the demande
        restDemandeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemande() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();

        // Update the demande
        Demande updatedDemande = demandeRepository.findById(demande.getId()).get();
        // Disconnect from session so that the updates on updatedDemande are not directly saved in db
        em.detach(updatedDemande);
        updatedDemande
            .refSiteWeb(UPDATED_REF_SITE_WEB)
            .datedesicion(UPDATED_DATEDESICION)
            .importateur(UPDATED_IMPORTATEUR)
            .refBF(UPDATED_REF_BF)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .imei1(UPDATED_IMEI_1)
            .imei2(UPDATED_IMEI_2)
            .imei3(UPDATED_IMEI_3)
            .statut(UPDATED_STATUT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDepotSiteWeb(UPDATED_DATE_DEPOT_SITE_WEB)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .dateExport(UPDATED_DATE_EXPORT)
            .commentaire(UPDATED_COMMENTAIRE);

        restDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemande.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemande))
            )
            .andExpect(status().isOk());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
        Demande testDemande = demandeList.get(demandeList.size() - 1);
        assertThat(testDemande.getRefSiteWeb()).isEqualTo(UPDATED_REF_SITE_WEB);
        assertThat(testDemande.getDatedesicion()).isEqualTo(UPDATED_DATEDESICION);
        assertThat(testDemande.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDemande.getRefBF()).isEqualTo(UPDATED_REF_BF);
        assertThat(testDemande.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testDemande.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testDemande.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testDemande.getImei1()).isEqualTo(UPDATED_IMEI_1);
        assertThat(testDemande.getImei2()).isEqualTo(UPDATED_IMEI_2);
        assertThat(testDemande.getImei3()).isEqualTo(UPDATED_IMEI_3);
        assertThat(testDemande.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemande.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemande.getDateDepotSiteWeb()).isEqualTo(UPDATED_DATE_DEPOT_SITE_WEB);
        assertThat(testDemande.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testDemande.getDateExport()).isEqualTo(UPDATED_DATE_EXPORT);
        assertThat(testDemande.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void putNonExistingDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demande.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demande))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demande))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeWithPatch() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();

        // Update the demande using partial update
        Demande partialUpdatedDemande = new Demande();
        partialUpdatedDemande.setId(demande.getId());

        partialUpdatedDemande
            .refSiteWeb(UPDATED_REF_SITE_WEB)
            .datedesicion(UPDATED_DATEDESICION)
            .importateur(UPDATED_IMPORTATEUR)
            .refBF(UPDATED_REF_BF)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .imei3(UPDATED_IMEI_3)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDepotSiteWeb(UPDATED_DATE_DEPOT_SITE_WEB)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaire(UPDATED_COMMENTAIRE);

        restDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemande))
            )
            .andExpect(status().isOk());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
        Demande testDemande = demandeList.get(demandeList.size() - 1);
        assertThat(testDemande.getRefSiteWeb()).isEqualTo(UPDATED_REF_SITE_WEB);
        assertThat(testDemande.getDatedesicion()).isEqualTo(UPDATED_DATEDESICION);
        assertThat(testDemande.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDemande.getRefBF()).isEqualTo(UPDATED_REF_BF);
        assertThat(testDemande.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testDemande.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testDemande.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testDemande.getImei1()).isEqualTo(DEFAULT_IMEI_1);
        assertThat(testDemande.getImei2()).isEqualTo(DEFAULT_IMEI_2);
        assertThat(testDemande.getImei3()).isEqualTo(UPDATED_IMEI_3);
        assertThat(testDemande.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemande.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemande.getDateDepotSiteWeb()).isEqualTo(UPDATED_DATE_DEPOT_SITE_WEB);
        assertThat(testDemande.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testDemande.getDateExport()).isEqualTo(DEFAULT_DATE_EXPORT);
        assertThat(testDemande.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void fullUpdateDemandeWithPatch() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();

        // Update the demande using partial update
        Demande partialUpdatedDemande = new Demande();
        partialUpdatedDemande.setId(demande.getId());

        partialUpdatedDemande
            .refSiteWeb(UPDATED_REF_SITE_WEB)
            .datedesicion(UPDATED_DATEDESICION)
            .importateur(UPDATED_IMPORTATEUR)
            .refBF(UPDATED_REF_BF)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .imei1(UPDATED_IMEI_1)
            .imei2(UPDATED_IMEI_2)
            .imei3(UPDATED_IMEI_3)
            .statut(UPDATED_STATUT)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateDepotSiteWeb(UPDATED_DATE_DEPOT_SITE_WEB)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .dateExport(UPDATED_DATE_EXPORT)
            .commentaire(UPDATED_COMMENTAIRE);

        restDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemande))
            )
            .andExpect(status().isOk());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
        Demande testDemande = demandeList.get(demandeList.size() - 1);
        assertThat(testDemande.getRefSiteWeb()).isEqualTo(UPDATED_REF_SITE_WEB);
        assertThat(testDemande.getDatedesicion()).isEqualTo(UPDATED_DATEDESICION);
        assertThat(testDemande.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDemande.getRefBF()).isEqualTo(UPDATED_REF_BF);
        assertThat(testDemande.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testDemande.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testDemande.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testDemande.getImei1()).isEqualTo(UPDATED_IMEI_1);
        assertThat(testDemande.getImei2()).isEqualTo(UPDATED_IMEI_2);
        assertThat(testDemande.getImei3()).isEqualTo(UPDATED_IMEI_3);
        assertThat(testDemande.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemande.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemande.getDateDepotSiteWeb()).isEqualTo(UPDATED_DATE_DEPOT_SITE_WEB);
        assertThat(testDemande.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testDemande.getDateExport()).isEqualTo(UPDATED_DATE_EXPORT);
        assertThat(testDemande.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demande.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demande))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demande))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemande() throws Exception {
        int databaseSizeBeforeUpdate = demandeRepository.findAll().size();
        demande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demande)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demande in the database
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemande() throws Exception {
        // Initialize the database
        demandeRepository.saveAndFlush(demande);

        int databaseSizeBeforeDelete = demandeRepository.findAll().size();

        // Delete the demande
        restDemandeMockMvc
            .perform(delete(ENTITY_API_URL_ID, demande.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demande> demandeList = demandeRepository.findAll();
        assertThat(demandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
