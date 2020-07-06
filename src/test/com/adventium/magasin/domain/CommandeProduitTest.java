package com.adventium.magasin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adventium.magasin.web.rest.TestUtil;

public class CommandeProduitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeProduit.class);
        CommandeProduit commandeProduit1 = new CommandeProduit();
        commandeProduit1.setId(1L);
        CommandeProduit commandeProduit2 = new CommandeProduit();
        commandeProduit2.setId(commandeProduit1.getId());
        assertThat(commandeProduit1).isEqualTo(commandeProduit2);
        commandeProduit2.setId(2L);
        assertThat(commandeProduit1).isNotEqualTo(commandeProduit2);
        commandeProduit1.setId(null);
        assertThat(commandeProduit1).isNotEqualTo(commandeProduit2);
    }
}
