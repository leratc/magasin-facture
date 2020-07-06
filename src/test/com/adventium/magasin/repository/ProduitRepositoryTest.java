package com.adventium.magasin.repository;

import com.adventium.magasin.domain.LigneCommande;
import com.adventium.magasin.domain.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christophe
 */
@SpringBootTest(classes = { RepositoryConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan("com.adventium.magasin")
@EnableJpaRepositories("com.adventium.magasin")
@EnableAutoConfiguration
public class ProduitRepositoryTest {

    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void test_findAllByProduitsId() {
        List<Long> produitIds = Arrays.asList(1L,2L,3L);
        List<Produit> produits = produitRepository.findAllByProduitsId(produitIds);
        assertThat(produits.size()).isEqualTo(3);
        assertThat(produits.get(0).getCategorieProduit()).isNotNull();
    }

}
