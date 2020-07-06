package com.adventium.magasin.service;

import com.adventium.magasin.MagasinFactureApp;
import com.adventium.magasin.domain.CategorieProduit;
import com.adventium.magasin.domain.Facture;
import com.adventium.magasin.domain.Produit;
import com.adventium.magasin.repository.FactureRepository;
import com.adventium.magasin.repository.LigneCommandeRepository;
import com.adventium.magasin.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christophe
 */
@SpringBootTest(classes = MagasinFactureApp.class)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan("com.adventium.magasin")
@EnableJpaRepositories("com.adventium.magasin")
@EnableAutoConfiguration
@ActiveProfiles("test")
public class FactureServiceTest {
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @TestConfiguration
    class FactureServiceImplTestContextConfiguration {

        @Bean
        public FactureService factureService() {
            return new FactureService(factureRepository, ligneCommandeRepository,produitRepository);
        }
    }
    @Autowired
    private FactureService factureService;

    @Test
    public void test_calculPrixTTCProduit() {

        Produit produit1 = new Produit();
        CategorieProduit cp =new CategorieProduit();
        cp.setNom("livre");
        cp.setTauxTaxe(BigDecimal.valueOf(0.10).setScale(2));
        cp.setImporte(false);
        produit1.setCategorieProduit(cp);
        produit1.setNom("livre");
        produit1.setPrix(BigDecimal.valueOf(18.00).setScale(2));
        BigDecimal montantTTC = FactureService.calculPrixTTCProduit(produit1);

        assertThat(montantTTC.setScale(2)).isEqualTo(BigDecimal.valueOf(19.80).setScale(2));
    }
    @Test
    public void test_calculPrixTTCProduitImporte() {

        Produit produit1 = new Produit();
        CategorieProduit cp =new CategorieProduit();
        cp.setNom("livre");
        cp.setTauxTaxe(BigDecimal.valueOf(0.10).setScale(2));
        cp.setImporte(true);
        produit1.setCategorieProduit(cp);
        produit1.setNom("livre");
        produit1.setPrix(BigDecimal.valueOf(10.00).setScale(2));
        BigDecimal montantTTC = FactureService.calculPrixTTCProduit(produit1);

        assertThat(montantTTC.setScale(2)).isEqualTo(BigDecimal.valueOf(11.50).setScale(2));
    }
    @Test
    public void test_generateFactureTTCFromCommandeProduitId() {
        Facture facture1 = factureService.generateFactureTTCFromCommandeProduitId(1L);
        Facture facture2 = factureService.generateFactureTTCFromCommandeProduitId(2L);
        Facture facture3 = factureService.generateFactureTTCFromCommandeProduitId(3L);
        assertThat(facture1.getMontantPaiement().setScale(2)).isEqualTo(BigDecimal.valueOf(48.05).setScale(2));
        assertThat(facture2.getMontantPaiement().setScale(2)).isEqualTo(BigDecimal.valueOf(199.15).setScale(2));
        assertThat(facture3.getMontantPaiement().setScale(2)).isEqualTo(BigDecimal.valueOf(145.70).setScale(2));
        System.out.println(facture1.getDetails());
        System.out.println(facture2.getDetails());
        System.out.println(facture3.getDetails());
    }
}
