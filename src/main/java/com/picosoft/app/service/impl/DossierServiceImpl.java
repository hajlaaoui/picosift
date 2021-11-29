package com.picosoft.app.service.impl;

import com.picosoft.app.domain.Dossier;
import com.picosoft.app.domain.enumeration.Statut;
import com.picosoft.app.repository.DossierRepository;
import com.picosoft.app.service.DossierService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dossier}.
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private final Logger log = LoggerFactory.getLogger(DossierServiceImpl.class);

    private final DossierRepository dossierRepository;

    public DossierServiceImpl(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }


    @Override
    public Dossier save(Dossier dossier) {
        log.debug("Request to save Dossier : {}", dossier);
        dossier.setStatut(Statut.EN_COURS);
        return dossierRepository.save(dossier);
    }

    @Override
    public Optional<Dossier> partialUpdate(Dossier dossier) {
        log.debug("Request to partially update Dossier : {}", dossier);

        return dossierRepository
            .findById(dossier.getId())
            .map(
                existingDossier -> {
                    if (dossier.getRefBF() != null) {
                        existingDossier.setRefBF(dossier.getRefBF());
                    }
                    if (dossier.getDatedesicion() != null) {
                        existingDossier.setDatedesicion(dossier.getDatedesicion());
                    }
                    if (dossier.getCin() != null) {
                        existingDossier.setCin(dossier.getCin());
                    }
                    if (dossier.getPasseport() != null) {
                        existingDossier.setPasseport(dossier.getPasseport());
                    }
                    if (dossier.getImportateur() != null) {
                        existingDossier.setImportateur(dossier.getImportateur());
                    }
                    if (dossier.getMarque() != null) {
                        existingDossier.setMarque(dossier.getMarque());
                    }
                    if (dossier.getStatut() != null) {
                        existingDossier.setStatut(dossier.getStatut());
                    }
                    if (dossier.getNumAvisArrive() != null) {
                        existingDossier.setNumAvisArrive(dossier.getNumAvisArrive());
                    }
                    if (dossier.getDateValidation() != null) {
                        existingDossier.setDateValidation(dossier.getDateValidation());
                    }
                    if (dossier.getCommentaire() != null) {
                        existingDossier.setCommentaire(dossier.getCommentaire());
                    }

                    return existingDossier;
                }
            )
            .map(dossierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dossier> findAll(Pageable pageable) {
        log.debug("Request to get all Dossiers");
        return dossierRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dossier> findOne(Long id) {
        log.debug("Request to get Dossier : {}", id);
        return dossierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dossier : {}", id);
        dossierRepository.deleteById(id);
    }
}
