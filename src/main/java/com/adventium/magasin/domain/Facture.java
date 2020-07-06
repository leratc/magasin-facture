package com.adventium.magasin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.adventium.magasin.domain.enumeration.StatutFacture;

import com.adventium.magasin.domain.enumeration.MethodePaiement;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "details")
    private String details;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutFacture statut;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "methode_paiement", nullable = false)
    private MethodePaiement methodePaiement;

    @NotNull
    @Column(name = "date_paiement", nullable = false)
    private Instant datePaiement;

    @NotNull
    @Column(name = "montant_paiement", precision = 21, scale = 2, nullable = false)
    private BigDecimal montantPaiement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Facture code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDate() {
        return date;
    }

    public Facture date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public Facture details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public Facture statut(StatutFacture statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public MethodePaiement getMethodePaiement() {
        return methodePaiement;
    }

    public Facture methodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
        return this;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public Facture datePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontantPaiement() {
        return montantPaiement;
    }

    public Facture montantPaiement(BigDecimal montantPaiement) {
        this.montantPaiement = montantPaiement;
        return this;
    }

    public void setMontantPaiement(BigDecimal montantPaiement) {
        this.montantPaiement = montantPaiement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", statut='" + getStatut() + "'" +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", montantPaiement=" + getMontantPaiement() +
            "}";
    }
}
