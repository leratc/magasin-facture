package com.adventium.magasin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.adventium.magasin.domain.enumeration.StatutLigneCommande;

/**
 * A LigneCommande.
 */
@Entity
@Table(name = "ligne_commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LigneCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix_total_ht", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixTotalHT;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutLigneCommande statut;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ligneCommandes", allowSetters = true)
    private Produit produit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ligneCommandes", allowSetters = true)
    private CommandeProduit commande;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public LigneCommande quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixTotalHT() {
        return prixTotalHT;
    }

    public LigneCommande prixTotalHT(BigDecimal prixTotalHT) {
        this.prixTotalHT = prixTotalHT;
        return this;
    }

    public void setPrixTotalHT(BigDecimal prixTotalHT) {
        this.prixTotalHT = prixTotalHT;
    }

    public StatutLigneCommande getStatut() {
        return statut;
    }

    public LigneCommande statut(StatutLigneCommande statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatutLigneCommande statut) {
        this.statut = statut;
    }

    public Produit getProduit() {
        return produit;
    }

    public LigneCommande produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public CommandeProduit getCommande() {
        return commande;
    }

    public LigneCommande commande(CommandeProduit commandeProduit) {
        this.commande = commandeProduit;
        return this;
    }

    public void setCommande(CommandeProduit commandeProduit) {
        this.commande = commandeProduit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCommande)) {
            return false;
        }
        return id != null && id.equals(((LigneCommande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneCommande{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", prixTotalHT=" + getPrixTotalHT() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
