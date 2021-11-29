package com.picosoft.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.picosoft.app.domain.enumeration.Statut;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ref_bf", nullable = false, unique = true)
    private String refBF;

    @Column(name = "datedesicion")
    private LocalDate datedesicion;

    @NotNull
    @Column(name = "cin", nullable = false, unique = true)
    private String cin;

    @Column(name = "passeport", unique = true)
    private String passeport;

    @Column(name = "importateur")
    private String importateur;

    @Column(name = "marque")
    private String marque;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Column(name = "num_avis_arrive")
    private String numAvisArrive;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @NotNull
    @Column(name = "commentaire", nullable = false)
    private String commentaire;

    @OneToMany(mappedBy = "dossier")
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Demande> demandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dossier id(Long id) {
        this.id = id;
        return this;
    }

    public String getRefBF() {
        return this.refBF;
    }

    public Dossier refBF(String refBF) {
        this.refBF = refBF;
        return this;
    }

    public void setRefBF(String refBF) {
        this.refBF = refBF;
    }

    public LocalDate getDatedesicion() {
        return this.datedesicion;
    }

    public Dossier datedesicion(LocalDate datedesicion) {
        this.datedesicion = datedesicion;
        return this;
    }

    public void setDatedesicion(LocalDate datedesicion) {
        this.datedesicion = datedesicion;
    }

    public String getCin() {
        return this.cin;
    }

    public Dossier cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPasseport() {
        return this.passeport;
    }

    public Dossier passeport(String passeport) {
        this.passeport = passeport;
        return this;
    }

    public void setPasseport(String passeport) {
        this.passeport = passeport;
    }

    public String getImportateur() {
        return this.importateur;
    }

    public Dossier importateur(String importateur) {
        this.importateur = importateur;
        return this;
    }

    public void setImportateur(String importateur) {
        this.importateur = importateur;
    }

    public String getMarque() {
        return this.marque;
    }

    public Dossier marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public Dossier statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getNumAvisArrive() {
        return this.numAvisArrive;
    }

    public Dossier numAvisArrive(String numAvisArrive) {
        this.numAvisArrive = numAvisArrive;
        return this;
    }

    public void setNumAvisArrive(String numAvisArrive) {
        this.numAvisArrive = numAvisArrive;
    }

    public LocalDate getDateValidation() {
        return this.dateValidation;
    }

    public Dossier dateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Dossier commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<Demande> getDemandes() {
        return this.demandes;
    }

    public Dossier demandes(Set<Demande> demandes) {
        this.setDemandes(demandes);
        return this;
    }

    public Dossier addDemande(Demande demande) {
        this.demandes.add(demande);
        demande.setDossier(this);
        return this;
    }

    public Dossier removeDemande(Demande demande) {
        this.demandes.remove(demande);
        demande.setDossier(null);
        return this;
    }

    public void setDemandes(Set<Demande> demandes) {
        if (this.demandes != null) {
            this.demandes.forEach(i -> i.setDossier(null));
        }
        if (demandes != null) {
            demandes.forEach(i -> i.setDossier(this));
        }
        this.demandes = demandes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return id != null && id.equals(((Dossier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", refBF='" + getRefBF() + "'" +
            ", datedesicion='" + getDatedesicion() + "'" +
            ", cin='" + getCin() + "'" +
            ", passeport='" + getPasseport() + "'" +
            ", importateur='" + getImportateur() + "'" +
            ", marque='" + getMarque() + "'" +
            ", statut='" + getStatut() + "'" +
            ", numAvisArrive='" + getNumAvisArrive() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
