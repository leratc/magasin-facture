package com.adventium.magasin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.adventium.magasin.domain.enumeration.Civilite;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "civilite", nullable = false)
    private Civilite civilite;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "addresse_ligne_1", nullable = false)
    private String addresseLigne1;

    @Column(name = "addresse_ligne_2")
    private String addresseLigne2;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @NotNull
    @Column(name = "pays", nullable = false)
    private String pays;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeProduit> commandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Client prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Client nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public Client civilite(Civilite civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Client telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddresseLigne1() {
        return addresseLigne1;
    }

    public Client addresseLigne1(String addresseLigne1) {
        this.addresseLigne1 = addresseLigne1;
        return this;
    }

    public void setAddresseLigne1(String addresseLigne1) {
        this.addresseLigne1 = addresseLigne1;
    }

    public String getAddresseLigne2() {
        return addresseLigne2;
    }

    public Client addresseLigne2(String addresseLigne2) {
        this.addresseLigne2 = addresseLigne2;
        return this;
    }

    public void setAddresseLigne2(String addresseLigne2) {
        this.addresseLigne2 = addresseLigne2;
    }

    public String getVille() {
        return ville;
    }

    public Client ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public Client pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public User getUser() {
        return user;
    }

    public Client user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CommandeProduit> getCommandes() {
        return commandes;
    }

    public Client commandes(Set<CommandeProduit> commandeProduits) {
        this.commandes = commandeProduits;
        return this;
    }

    public Client addCommande(CommandeProduit commandeProduit) {
        this.commandes.add(commandeProduit);
        commandeProduit.setClient(this);
        return this;
    }

    public Client removeCommande(CommandeProduit commandeProduit) {
        this.commandes.remove(commandeProduit);
        commandeProduit.setClient(null);
        return this;
    }

    public void setCommandes(Set<CommandeProduit> commandeProduits) {
        this.commandes = commandeProduits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", civilite='" + getCivilite() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", addresseLigne1='" + getAddresseLigne1() + "'" +
            ", addresseLigne2='" + getAddresseLigne2() + "'" +
            ", ville='" + getVille() + "'" +
            ", pays='" + getPays() + "'" +
            "}";
    }
}
