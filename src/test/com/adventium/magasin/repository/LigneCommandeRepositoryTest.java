package com.adventium.magasin.repository;

import com.adventium.magasin.domain.LigneCommande;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryConfiguration;
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
public class LigneCommandeRepositoryTest{

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    @Test
    public void test_findByCommandeProduit() {
        List<LigneCommande> commandeList = ligneCommandeRepository.findAllByCommandeId(1L);
        assertThat(commandeList.size()).isEqualTo(3);
        assertThat(commandeList.get(0).getProduit()).isNotNull();
    }

}
