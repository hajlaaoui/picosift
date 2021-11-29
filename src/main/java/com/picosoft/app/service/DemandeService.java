package com.picosoft.app.service;

import com.picosoft.app.domain.Demande;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Demande}.
 */
@Service
public interface DemandeService {
    /**
     * Save a demande.
     *
     * @param demande the entity to save.
     * @return the persisted entity.
     */
    Demande save(Demande demande);

    /**
     * Partially updates a demande.
     *
     * @param demande the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Demande> partialUpdate(Demande demande);

    /**
     * Get all the demandes.
     *
     * @return the list of entities.
     */
    List<Demande> findAll();


    /**
     * Get the "id" demande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Demande> findOne(Long id);

    /**
     * Delete the "id" demande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
     * refuser demande
     */

}
