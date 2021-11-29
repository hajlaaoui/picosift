package com.picosoft.app.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import com.picosoft.app.domain.Dossier;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    @Query("SELECT o FROM Dossier o WHERE CONCAT(o.refBF, o.cin, o.importateur) LIKE %?1% ")

    public List<Dossier> search(String keyword);
    @Modifying
	@Transactional
	@Query("UPDATE Dossier e SET e.statut='ACCEPTER' where e.id=:annonceId")
	public void acceptAnnonceJPQL(@Param("annonceId") Long annonceId);
    @Modifying
	@Transactional
	@Query("UPDATE Dossier e SET e.statut='REFUSER' where e.id=:annonceId")
	public void DeniedAnnonceJPQL(@Param("annonceId") Long annonceId);
    @Query("SELECT dateDepotSiteWeb FROM Demande WHERE id = ?1")
	public LocalDate dateDepotSiteWeb(Long id);
    @Query("SELECT dateExport FROM Demande WHERE id = ?1")
	public LocalDate ExporDate(Long id);
}
