package com.picosoft.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.picosoft.app.domain.enumeration.Statut;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Demande.
 */
@Entity
@Table(name = "demande")
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "ref_site_web", nullable = false, unique = true)
    private String refSiteWeb;

    @Column(name = "datedesicion")
    private LocalDate datedesicion;

    @Column(name = "importateur")
    private String importateur;

    @Column(name = "ref_bf", unique = true)
    private String refBF;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(name = "rapport_date")
    private long rapport_date;

    @Column(name = "numero_serie", unique = true)
    private String numeroSerie;

    @Column(name = "imei_1", unique = true)
    private String imei1;

    @Column(name = "imei_2", unique = true)
    private String imei2;

    @Column(name = "imei_3", unique = true)
    private String imei3;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_depot_site_web")
    private LocalDate dateDepotSiteWeb;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "date_export")
    private LocalDate dateExport;

    @NotNull
    @Column(name = "commentaire", nullable = false)
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandes" }, allowSetters = true)
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Demande id(Long id) {
        this.id = id;
        return this;
    }

    public String getRefSiteWeb() {
        return this.refSiteWeb;
    }

    public Demande refSiteWeb(String refSiteWeb) {
        this.refSiteWeb = refSiteWeb;
        return this;
    }

    public void setRefSiteWeb(String refSiteWeb) {
        this.refSiteWeb = refSiteWeb;
    }

    public LocalDate getDatedesicion() {
        return this.datedesicion;
    }

    public Demande datedesicion(LocalDate datedesicion) {
        this.datedesicion = datedesicion;
        return this;
    }

    public void setDatedesicion(LocalDate datedesicion) {
        this.datedesicion = datedesicion;
    }

    public String getImportateur() {
        return this.importateur;
    }

    public Demande importateur(String importateur) {
        this.importateur = importateur;
        return this;
    }

    public void setImportateur(String importateur) {
        this.importateur = importateur;
    }

    public String getRefBF() {
        return this.refBF;
    }

    public Demande refBF(String refBF) {
        this.refBF = refBF;
        return this;
    }

    public void setRefBF(String refBF) {
        this.refBF = refBF;
    }

    public String getMarque() {
        return this.marque;
    }

    public Demande marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return this.modele;
    }

    public Demande modele(String modele) {
        this.modele = modele;
        return this;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Demande numeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getImei1() {
        return this.imei1;
    }

    public Demande imei1(String imei1) {
        this.imei1 = imei1;
        return this;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return this.imei2;
    }

    public Demande imei2(String imei2) {
        this.imei2 = imei2;
        return this;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImei3() {
        return this.imei3;
    }

    public Demande imei3(String imei3) {
        this.imei3 = imei3;
        return this;
    }

    public void setImei3(String imei3) {
        this.imei3 = imei3;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public Demande statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Demande dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateDepotSiteWeb() {
        return this.dateDepotSiteWeb;
    }

    public Demande dateDepotSiteWeb(LocalDate dateDepotSiteWeb) {
        this.dateDepotSiteWeb = dateDepotSiteWeb;
        return this;
    }

    public void setDateDepotSiteWeb(LocalDate dateDepotSiteWeb) {
        this.dateDepotSiteWeb = dateDepotSiteWeb;
    }

    public LocalDate getDateValidation() {
        return this.dateValidation;
    }

    public Demande dateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public LocalDate getDateExport() {
        return this.dateExport;
    }

    public Demande dateExport(LocalDate dateExport) {
        this.dateExport = dateExport;
        return this;
    }

    public void setDateExport(LocalDate dateExport) {
        this.dateExport = dateExport;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Demande commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public Demande dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demande)) {
            return false;
        }
        return id != null && id.equals(((Demande) o).id);
    }


    public long getRapport_date() {
        return rapport_date;
    }

    public void setRapport_date(long rapport_date) {
        this.rapport_date = rapport_date;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demande{" +
            "id=" + getId() +
            ", refSiteWeb='" + getRefSiteWeb() + "'" +
            ", datedesicion='" + getDatedesicion() + "'" +
            ", importateur='" + getImportateur() + "'" +
            ", refBF='" + getRefBF() + "'" +
            ", marque='" + getMarque() + "'" +
            ", modele='" + getModele() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", imei1='" + getImei1() + "'" +
            ", imei2='" + getImei2() + "'" +
            ", imei3='" + getImei3() + "'" +
            ", statut='" + getStatut() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateDepotSiteWeb='" + getDateDepotSiteWeb() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", dateExport='" + getDateExport() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", rapportdate='" + getRapport_date() + "'" +
            "}";
    }
}
