package com.adventium.magasin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.adventium.magasin.domain.enumeration.StatutCommande;

/**
 * A CommandeProduit.
 */
@Entity
@Table(name = "commande_produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommandeProduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_commande", nullable = false)
    private Instant dateCommande;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutCommande statut;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "facture_id")
    private Long factureId;

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LigneCommande> ligneCommandes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public CommandeProduit dateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public CommandeProduit statut(StatutCommande statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public String getCode() {
        return code;
    }

    public CommandeProduit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getFactureId() {
        return factureId;
    }

    public CommandeProduit factureId(Long factureId) {
        this.factureId = factureId;
        return this;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public Set<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public CommandeProduit ligneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
        return this;
    }

    public CommandeProduit addLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.add(ligneCommande);
        ligneCommande.setCommande(this);
        return this;
    }

    public CommandeProduit removeLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.remove(ligneCommande);
        ligneCommande.setCommande(null);
        return this;
    }

    public void setLigneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public Client getClient() {
        return client;
    }

    public CommandeProduit client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeProduit)) {
            return false;
        }
        return id != null && id.equals(((CommandeProduit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeProduit{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", statut='" + getStatut() + "'" +
            ", code='" + getCode() + "'" +
            ", factureId=" + getFactureId() +
            "}";
    }
}
