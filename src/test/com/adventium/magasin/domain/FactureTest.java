package com.adventium.magasin.domain;

import com.adventium.magasin.service.FactureService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adventium.magasin.web.rest.TestUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;

public class FactureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facture.class);
        Facture facture1 = new Facture();
        facture1.setId(1L);
        Facture facture2 = new Facture();
        facture2.setId(facture1.getId());
        assertThat(facture1).isEqualTo(facture2);
        facture2.setId(2L);
        assertThat(facture1).isNotEqualTo(facture2);
        facture1.setId(null);
        assertThat(facture1).isNotEqualTo(facture2);
    }
    @Test
    public void testArrondi() {
        assertThat (FactureService.arrondiAuCinqCentimesSuperieur(BigDecimal.valueOf(2.50))).isEqualTo(BigDecimal.valueOf(2.50).setScale(2));
        assertThat (FactureService.arrondiAuCinqCentimesSuperieur(BigDecimal.valueOf(2.54444))).isEqualTo(BigDecimal.valueOf(2.55).setScale(2));
        assertThat (FactureService.arrondiAuCinqCentimesSuperieur(BigDecimal.valueOf(2.55))).isEqualTo(BigDecimal.valueOf(2.55).setScale(2));
        assertThat (FactureService.arrondiAuCinqCentimesSuperieur(BigDecimal.valueOf(2.56))).isEqualTo(BigDecimal.valueOf(2.60).setScale(2));
        assertThat(FactureService.arrondiAuCinqCentimesSuperieur(BigDecimal.valueOf(17.99))).isEqualTo(BigDecimal.valueOf(18.00).setScale(2));
    }
    @Test
    public void testRegex() {
        String strReplaced= "pluriel du premier mot".replaceFirst("\\s|$","s ");
        assertThat(strReplaced).isEqualTo("pluriels du premier mot");
        String strReplacedUnMotSansBlanc= "livre".replaceFirst("\\s|$","s");
        assertThat(strReplacedUnMotSansBlanc).isEqualTo("livres");
    }
}
