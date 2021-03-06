package com.picosoft.app.service;

import com.picosoft.app.domain.Dossier;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Dossier}.
 */
public interface DossierService {
    /**
     * Save a dossier.
     *
     * @param dossier the entity to save.
     * @return the persisted entity.
     */
    Dossier save(Dossier dossier);

    /**
     * Partially updates a dossier.
     *
     * @param dossier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Dossier> partialUpdate(Dossier dossier);

    /**
     * Get all the dossiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Dossier> findAll(Pageable pageable);

    /**
     * Get the "id" dossier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dossier> findOne(Long id);

    /**
     * Delete the "id" dossier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
