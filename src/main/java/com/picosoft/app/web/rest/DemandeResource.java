package com.picosoft.app.web.rest;

import com.picosoft.app.domain.Demande;
import com.picosoft.app.repository.DemandeRepository;
import com.picosoft.app.service.DemandeService;
import com.picosoft.app.service.impl.DemandeServiceImpl;
import com.picosoft.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * REST controller for managing {@link com.picosoft.app.domain.Demande}.
 */
@RestController
@RequestMapping("/api")
public class DemandeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeResource.class);

    private static final String ENTITY_NAME = "demande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private final DemandeService demandeService;


    private final DemandeRepository demandeRepository;

    public DemandeResource(DemandeService demandeService, DemandeRepository demandeRepository,DemandeServiceImpl demandeServiceImpl) {
        this.demandeService = demandeService;
        this.demandeRepository = demandeRepository;

    }
    @PutMapping("/update-demAccepter/{annId}")
    @ResponseBody
    public void updateDemandeAccepted(@PathVariable("annId")Long annId) {

        demandeRepository.acceptAnnonceJPQL(annId);
       // return new ResponseEntity<String>("rent annonce updated successfully",HttpStatus.OK);

    }
    @PutMapping("/update-demDenied/{annId}")
    @ResponseBody
    public void updateDemandeDenied(@PathVariable("annId")Long annId) {

        demandeRepository.DeniedAnnonceJPQL(annId);
       // return new ResponseEntity<String>("rent annonce updated successfully",HttpStatus.OK);

    }
    @GetMapping("/countrefuser")
	public int countRefuser() {
		return demandeRepository.countRefuser();
	}
    @GetMapping("/countEncours")
	public int countAccepter() {
		return demandeRepository.countEncours();
	}
    @GetMapping("/countAccpter")
	public int countEncours() {
		return demandeRepository.countAccpter();
	}
    @GetMapping("/countAll")
	public int countAll() {
		return demandeRepository.countAccpter() + demandeRepository.countEncours() + demandeRepository.countRefuser() ;
	}
    


    @GetMapping("/datedepotsiteweb/{id}")
	public LocalDate dateDepotSiteWeb(@PathVariable("id") Long id) {

		return demandeRepository.dateDepotSiteWeb(id);
	}


   /* @GetMapping("/calculateinterval/{id}")
	public int nbjours(@PathVariable("id") Long id) {
		return calculateDateInterval(demandeRepository.ExporDate(id), demandeRepository.dateDepotSiteWeb(id));
	}*/
    @GetMapping("DHN/{id}")
	public List<Object> DHN(@PathVariable("id") Long id) {

		return demandeRepository.InfoDhn(id);
	}
    @GetMapping(value = "/searchdemande/{keyword}")
    public List<Demande> dynamicSearch(@PathVariable String keyword){
       return demandeRepository.search(keyword);

    }
    @GetMapping("Demande/{id}")
	public List<Object> Demande(@PathVariable("id") Long id) {

		return demandeRepository.InfoDemande(id);
	}

    /**
     * {@code POST  /demandes} : Create a new demande.
     *
     * @param demande the demande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demande, or with status {@code 400 (Bad Request)} if the demande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demandes")
    public ResponseEntity<Demande> createDemande(@Valid @RequestBody Demande demande) throws URISyntaxException {
        log.debug("REST request to save Demande : {}", demande);
        if (demande.getId() != null) {
            throw new BadRequestAlertException("A new demande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Demande result = demandeService.save(demande);
        return ResponseEntity
            .created(new URI("/api/demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /demandes/:id} : Updates an existing demande.
     *
     * @param id the id of the demande to save.
     * @param demande the demande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demande,
     * or with status {@code 400 (Bad Request)} if the demande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demandes/{id}")
    public ResponseEntity<Demande> updateDemande(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Demande demande
    ) throws URISyntaxException {
        log.debug("REST request to update Demande : {}, {}", id, demande);
        if (demande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Demande result = demandeService.save(demande);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demande.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demandes/:id} : Partial updates given fields of an existing demande, field will ignore if it is null
     *
     * @param id the id of the demande to save.
     * @param demande the demande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demande,
     * or with status {@code 400 (Bad Request)} if the demande is not valid,
     * or with status {@code 404 (Not Found)} if the demande is not found,
     * or with status {@code 500 (Internal Server Error)} if the demande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demandes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Demande> partialUpdateDemande(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Demande demande
    ) throws URISyntaxException {
        log.debug("REST request to partial update Demande partially : {}, {}", id, demande);
        if (demande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Demande> result = demandeService.partialUpdate(demande);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demande.getId().toString())
        );
    }

    /**
     * {@code GET  /demandes} : get all the demandes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandes in body.
     */
    @GetMapping("/demandes")
    public List<Demande> getAllDemandes() {
        log.debug("REST request to get all Demandes");
        return demandeService.findAll();
    }

    /**
     * {@code GET  /demandes/:id} : get the "id" demande.
     *
     * @param id the id of the demande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demandes/{id}")
    public ResponseEntity<Demande> getDemande(@PathVariable Long id) {
        log.debug("REST request to get Demande : {}", id);
        Optional<Demande> demande = demandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demande);
    }

    /**
     * {@code DELETE  /demandes/:id} : delete the "id" demande.
     *
     * @param id the id of the demande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        log.debug("REST request to delete Demande : {}", id);
        demandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
