package com.picosoft.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.picosoft.app.IntegrationTest;
import com.picosoft.app.domain.Dossier;
import com.picosoft.app.domain.enumeration.Statut;
import com.picosoft.app.repository.DossierRepository;
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
 * Integration tests for the {@link DossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DossierResourceIT {

    private static final String DEFAULT_REF_BF = "AAAAAAAAAA";
    private static final String UPDATED_REF_BF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEDESICION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDESICION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSEPORT = "AAAAAAAAAA";
    private static final String UPDATED_PASSEPORT = "BBBBBBBBBB";

    private static final String DEFAULT_IMPORTATEUR = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final Statut DEFAULT_STATUT = Statut.EN_COURS;
    private static final Statut UPDATED_STATUT = Statut.ACCEPTER;

    private static final String DEFAULT_NUM_AVIS_ARRIVE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_AVIS_ARRIVE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .refBF(DEFAULT_REF_BF)
            .datedesicion(DEFAULT_DATEDESICION)
            .cin(DEFAULT_CIN)
            .passeport(DEFAULT_PASSEPORT)
            .importateur(DEFAULT_IMPORTATEUR)
            .marque(DEFAULT_MARQUE)
            .statut(DEFAULT_STATUT)
            .numAvisArrive(DEFAULT_NUM_AVIS_ARRIVE)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .commentaire(DEFAULT_COMMENTAIRE);
        return dossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createUpdatedEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .refBF(UPDATED_REF_BF)
            .datedesicion(UPDATED_DATEDESICION)
            .cin(UPDATED_CIN)
            .passeport(UPDATED_PASSEPORT)
            .importateur(UPDATED_IMPORTATEUR)
            .marque(UPDATED_MARQUE)
            .statut(UPDATED_STATUT)
            .numAvisArrive(UPDATED_NUM_AVIS_ARRIVE)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaire(UPDATED_COMMENTAIRE);
        return dossier;
    }

    @BeforeEach
    public void initTest() {
        dossier = createEntity(em);
    }

    @Test
    @Transactional
    void createDossier() throws Exception {
        int databaseSizeBeforeCreate = dossierRepository.findAll().size();
        // Create the Dossier
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isCreated());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate + 1);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRefBF()).isEqualTo(DEFAULT_REF_BF);
        assertThat(testDossier.getDatedesicion()).isEqualTo(DEFAULT_DATEDESICION);
        assertThat(testDossier.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testDossier.getPasseport()).isEqualTo(DEFAULT_PASSEPORT);
        assertThat(testDossier.getImportateur()).isEqualTo(DEFAULT_IMPORTATEUR);
        assertThat(testDossier.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testDossier.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDossier.getNumAvisArrive()).isEqualTo(DEFAULT_NUM_AVIS_ARRIVE);
        assertThat(testDossier.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testDossier.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    void createDossierWithExistingId() throws Exception {
        // Create the Dossier with an existing ID
        dossier.setId(1L);

        int databaseSizeBeforeCreate = dossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRefBFIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossierRepository.findAll().size();
        // set the field null
        dossier.setRefBF(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossierRepository.findAll().size();
        // set the field null
        dossier.setCin(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommentaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = dossierRepository.findAll().size();
        // set the field null
        dossier.setCommentaire(null);

        // Create the Dossier, which fails.

        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isBadRequest());

        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDossiers() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].refBF").value(hasItem(DEFAULT_REF_BF)))
            .andExpect(jsonPath("$.[*].datedesicion").value(hasItem(DEFAULT_DATEDESICION.toString())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].passeport").value(hasItem(DEFAULT_PASSEPORT)))
            .andExpect(jsonPath("$.[*].importateur").value(hasItem(DEFAULT_IMPORTATEUR)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].numAvisArrive").value(hasItem(DEFAULT_NUM_AVIS_ARRIVE)))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(DEFAULT_DATE_VALIDATION.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }

    @Test
    @Transactional
    void getDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.refBF").value(DEFAULT_REF_BF))
            .andExpect(jsonPath("$.datedesicion").value(DEFAULT_DATEDESICION.toString()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.passeport").value(DEFAULT_PASSEPORT))
            .andExpect(jsonPath("$.importateur").value(DEFAULT_IMPORTATEUR))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.numAvisArrive").value(DEFAULT_NUM_AVIS_ARRIVE))
            .andExpect(jsonPath("$.dateValidation").value(DEFAULT_DATE_VALIDATION.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    @Transactional
    void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findById(dossier.getId()).get();
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .refBF(UPDATED_REF_BF)
            .datedesicion(UPDATED_DATEDESICION)
            .cin(UPDATED_CIN)
            .passeport(UPDATED_PASSEPORT)
            .importateur(UPDATED_IMPORTATEUR)
            .marque(UPDATED_MARQUE)
            .statut(UPDATED_STATUT)
            .numAvisArrive(UPDATED_NUM_AVIS_ARRIVE)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaire(UPDATED_COMMENTAIRE);

        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRefBF()).isEqualTo(UPDATED_REF_BF);
        assertThat(testDossier.getDatedesicion()).isEqualTo(UPDATED_DATEDESICION);
        assertThat(testDossier.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testDossier.getPasseport()).isEqualTo(UPDATED_PASSEPORT);
        assertThat(testDossier.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDossier.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testDossier.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossier.getNumAvisArrive()).isEqualTo(UPDATED_NUM_AVIS_ARRIVE);
        assertThat(testDossier.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testDossier.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void putNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .cin(UPDATED_CIN)
            .importateur(UPDATED_IMPORTATEUR)
            .statut(UPDATED_STATUT)
            .numAvisArrive(UPDATED_NUM_AVIS_ARRIVE)
            .commentaire(UPDATED_COMMENTAIRE);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRefBF()).isEqualTo(DEFAULT_REF_BF);
        assertThat(testDossier.getDatedesicion()).isEqualTo(DEFAULT_DATEDESICION);
        assertThat(testDossier.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testDossier.getPasseport()).isEqualTo(DEFAULT_PASSEPORT);
        assertThat(testDossier.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDossier.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testDossier.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossier.getNumAvisArrive()).isEqualTo(UPDATED_NUM_AVIS_ARRIVE);
        assertThat(testDossier.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testDossier.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void fullUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .refBF(UPDATED_REF_BF)
            .datedesicion(UPDATED_DATEDESICION)
            .cin(UPDATED_CIN)
            .passeport(UPDATED_PASSEPORT)
            .importateur(UPDATED_IMPORTATEUR)
            .marque(UPDATED_MARQUE)
            .statut(UPDATED_STATUT)
            .numAvisArrive(UPDATED_NUM_AVIS_ARRIVE)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .commentaire(UPDATED_COMMENTAIRE);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getRefBF()).isEqualTo(UPDATED_REF_BF);
        assertThat(testDossier.getDatedesicion()).isEqualTo(UPDATED_DATEDESICION);
        assertThat(testDossier.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testDossier.getPasseport()).isEqualTo(UPDATED_PASSEPORT);
        assertThat(testDossier.getImportateur()).isEqualTo(UPDATED_IMPORTATEUR);
        assertThat(testDossier.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testDossier.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossier.getNumAvisArrive()).isEqualTo(UPDATED_NUM_AVIS_ARRIVE);
        assertThat(testDossier.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testDossier.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dossier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeDelete = dossierRepository.findAll().size();

        // Delete the dossier
        restDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
