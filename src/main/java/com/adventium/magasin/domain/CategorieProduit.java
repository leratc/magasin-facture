package com.adventium.magasin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategorieProduit.
 */
@Entity
@Table(name = "categorie_produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieProduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "taux_taxe", precision = 21, scale = 2)
    private BigDecimal tauxTaxe;

    @Column(name = "importe")
    private Boolean importe;

    @OneToMany(mappedBy = "categorieProduit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public CategorieProduit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public CategorieProduit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTauxTaxe() {
        return tauxTaxe;
    }

    public CategorieProduit tauxTaxe(BigDecimal tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
        return this;
    }

    public void setTauxTaxe(BigDecimal tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
    }

    public Boolean isImporte() {
        return importe;
    }

    public CategorieProduit importe(Boolean importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(Boolean importe) {
        this.importe = importe;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public CategorieProduit produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public CategorieProduit addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setCategorieProduit(this);
        return this;
    }

    public CategorieProduit removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setCategorieProduit(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieProduit)) {
            return false;
        }
        return id != null && id.equals(((CategorieProduit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieProduit{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", tauxTaxe=" + getTauxTaxe() +
            ", importe='" + isImporte() + "'" +
            "}";
    }
}
