package com.picosoft.app.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.picosoft.app.domain.Demande;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Demande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    @Modifying
	@Transactional
	@Query("UPDATE Demande e SET e.statut='ACCEPTER' where e.id=:annonceId")
	public void acceptAnnonceJPQL(@Param("annonceId") Long annonceId);
    @Modifying
	@Transactional
	@Query("UPDATE Demande e SET e.statut='REFUSER' where e.id=:annonceId")
	public void DeniedAnnonceJPQL(@Param("annonceId") Long annonceId);
    @Query("SELECT COUNT(e.statut) FROM Demande e WHERE e.statut='REFUSER'")
	int countRefuser();

    @Query("SELECT COUNT(e.statut) FROM Demande e WHERE e.statut='ACCEPTER'")
	int countAccpter();
    
    @Query("SELECT COUNT(e.statut) FROM Demande e WHERE e.statut='EN_COURS'")
	int countEncours();

    @Query("SELECT dateDepotSiteWeb FROM Demande WHERE id = ?1")
	public LocalDate dateDepotSiteWeb(Long id);
    @Query("SELECT dateExport FROM Demande WHERE id = ?1")
	public LocalDate ExporDate(Long id);
    @Query("SELECT refSiteWeb,datedesicion,importateur,marque,modele FROM Demande WHERE id = ?1")
	public List<Object> InfoDhn(Long id);
    @Query("SELECT refSiteWeb,statut,dateDepotSiteWeb,dateValidation,dateExport,marque,modele,imei1,imei2,imei3 FROM Demande WHERE id = ?1")
	public List<Object> InfoDemande(Long id);
    @Query("SELECT o FROM Demande o WHERE CONCAT(o.refSiteWeb, o.numeroSerie, o.imei1) LIKE %?1% ")
	public List<Demande> search(String keyword);


}
