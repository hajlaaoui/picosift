package com.picosoft.app.service.impl;

import com.picosoft.app.domain.Demande;
import com.picosoft.app.domain.enumeration.Statut;
import com.picosoft.app.repository.DemandeRepository;
import com.picosoft.app.service.DemandeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Service Implementation for managing {@link Demande}.
 */
@Service
@Transactional
public class DemandeServiceImpl implements DemandeService {

    private final Logger log = LoggerFactory.getLogger(DemandeServiceImpl.class);

    private final DemandeRepository demandeRepository;
@Autowired
    public DemandeServiceImpl(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    public long calculateDateInterval(LocalDate startDate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}

    @Override
    public Demande save(Demande demande) {
        log.debug("Request to save Demande : {}", demande);
        demande.setStatut(Statut.EN_COURS);
        LocalDate web = demande.getDateDepotSiteWeb();
        LocalDate export =demande.getDateExport();
        long rapp = calculateDateInterval(web, export);
        demande.setRapport_date(rapp);

        return demandeRepository.save(demande);
    }

    @Override
    public Optional<Demande> partialUpdate(Demande demande) {
        log.debug("Request to partially update Demande : {}", demande);

        return demandeRepository
            .findById(demande.getId())
            .map(
                existingDemande -> {
                    if (demande.getRefSiteWeb() != null) {
                        existingDemande.setRefSiteWeb(demande.getRefSiteWeb());
                    }
                    if (demande.getDatedesicion() != null) {
                        existingDemande.setDatedesicion(demande.getDatedesicion());
                    }
                    if (demande.getImportateur() != null) {
                        existingDemande.setImportateur(demande.getImportateur());
                    }
                    if (demande.getRefBF() != null) {
                        existingDemande.setRefBF(demande.getRefBF());
                    }
                    if (demande.getMarque() != null) {
                        existingDemande.setMarque(demande.getMarque());
                    }
                    if (demande.getModele() != null) {
                        existingDemande.setModele(demande.getModele());
                    }
                    if (demande.getNumeroSerie() != null) {
                        existingDemande.setNumeroSerie(demande.getNumeroSerie());
                    }
                    if (demande.getImei1() != null) {
                        existingDemande.setImei1(demande.getImei1());
                    }
                    if (demande.getImei2() != null) {
                        existingDemande.setImei2(demande.getImei2());
                    }
                    if (demande.getImei3() != null) {
                        existingDemande.setImei3(demande.getImei3());
                    }
                    if (demande.getStatut() != null) {
                        existingDemande.setStatut(demande.getStatut());
                    }
                    if (demande.getDateCreation() != null) {
                        existingDemande.setDateCreation(demande.getDateCreation());
                    }
                    if (demande.getDateDepotSiteWeb() != null) {
                        existingDemande.setDateDepotSiteWeb(demande.getDateDepotSiteWeb());
                    }
                    if (demande.getDateValidation() != null) {
                        existingDemande.setDateValidation(demande.getDateValidation());
                    }
                    if (demande.getDateExport() != null) {
                        existingDemande.setDateExport(demande.getDateExport());
                    }
                    if (demande.getCommentaire() != null) {
                        existingDemande.setCommentaire(demande.getCommentaire());
                    }

                    return existingDemande;
                }
            )
            .map(demandeRepository::save);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Demande> findAll() {
        log.debug("Request to get all Demandes");
        return demandeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Demande> findOne(Long id) {
        log.debug("Request to get Demande : {}", id);
        return demandeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demande : {}", id);
        demandeRepository.deleteById(id);
    }


}
