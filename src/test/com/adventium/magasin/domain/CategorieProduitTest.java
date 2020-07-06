package com.adventium.magasin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adventium.magasin.web.rest.TestUtil;

public class CategorieProduitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieProduit.class);
        CategorieProduit categorieProduit1 = new CategorieProduit();
        categorieProduit1.setId(1L);
        CategorieProduit categorieProduit2 = new CategorieProduit();
        categorieProduit2.setId(categorieProduit1.getId());
        assertThat(categorieProduit1).isEqualTo(categorieProduit2);
        categorieProduit2.setId(2L);
        assertThat(categorieProduit1).isNotEqualTo(categorieProduit2);
        categorieProduit1.setId(null);
        assertThat(categorieProduit1).isNotEqualTo(categorieProduit2);
    }
}
