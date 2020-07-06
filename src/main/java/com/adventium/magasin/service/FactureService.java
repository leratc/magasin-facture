package com.adventium.magasin.service;

import com.adventium.magasin.domain.CommandeProduit;
import com.adventium.magasin.domain.Facture;
import com.adventium.magasin.domain.LigneCommande;
import com.adventium.magasin.domain.Produit;
import com.adventium.magasin.repository.CommandeProduitRepository;
import com.adventium.magasin.repository.FactureRepository;
import com.adventium.magasin.repository.LigneCommandeRepository;
import com.adventium.magasin.repository.ProduitRepository;
import liquibase.pro.packaged.B;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link Facture}.
 */
@Service
@Transactional
public class FactureService {
    public static BigDecimal TAXE_IMPORT = new BigDecimal(0.05);
    private final Logger log = LoggerFactory.getLogger(FactureService.class);

    private final FactureRepository factureRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;

    public FactureService(FactureRepository factureRepository,
                          LigneCommandeRepository ligneCommandeRepository,
                          ProduitRepository produitRepository) {

        this.factureRepository = factureRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
    }

    public Facture generateFactureTTCFromCommandeProduitId(Long commandeProduitId) {
       List<LigneCommande> lignesCommande= ligneCommandeRepository.findAllByCommandeId(commandeProduitId);
        List<Long> produitIds = lignesCommande.stream().map(lc -> lc.getProduit().getId()).collect(Collectors.toList());
        Map<Long,Integer> mapProduitsQteCmde= lignesCommande.stream().
            collect(Collectors.toMap(lc->lc.getProduit().getId(), lc-> lc.getQuantite()));
       List<Produit> produits= produitRepository.findAllByProduitsId(produitIds);

        Facture facture= buildFacture(produits,mapProduitsQteCmde);
        facture.setDetails("#### Output "+commandeProduitId.toString()+"\n"+facture.getDetails());
        return facture;
    }
    public static BigDecimal calculPrixTTCProduit(Produit produit) {
        BigDecimal montantHt=produit.getPrix();
        BigDecimal montantTTC=BigDecimal.ZERO;
        BigDecimal tauxTaxe = produit.getCategorieProduit().getTauxTaxe().setScale(2);
        // D'après les résultats attendus, on ajoute le taux au taux de base.
        // Normalement, on devrait le cumuler en cascade => montantTTC = montantHT*(1+Taux)*(1+tauximport)
        if (produit.getCategorieProduit().isImporte()) {
            tauxTaxe=tauxTaxe.add(TAXE_IMPORT);
        }
        if (tauxTaxe.intValue() == 0) {
            montantTTC=montantHt.multiply(tauxTaxe.add(BigDecimal.ONE));
        }
        else {
            montantTTC=montantHt;
        }

        return montantTTC;
    }
    public Facture buildFacture(List<Produit> produitList,Map<Long,Integer> mapProduitsQteCmde) {
        Facture facture = new Facture();
        String detailsString="";
        BigDecimal montantPaiement = BigDecimal.ZERO.setScale(2);
        BigDecimal montantHT = BigDecimal.ZERO.setScale(2);
        for (Produit produit : produitList) {
            BigDecimal montantUnitaireTTC= calculPrixTTCProduit(produit);
            Integer qte = mapProduitsQteCmde.get(produit.getId());
            BigDecimal montantTotalTTC = arrondiAuCinqCentimesSuperieur(montantUnitaireTTC.multiply(BigDecimal.valueOf(qte)));
            String descriptionAvecS=qte>1?produit.getDescription().replaceFirst("\\s|$","s "):produit.getDescription();
            detailsString= detailsString+ "* "+qte.toString()+" "+descriptionAvecS+" à "+displayFormatEuro(montantTotalTTC)+"\n";
            montantPaiement=montantPaiement.add(montantTotalTTC);
            montantHT=montantHT.add(produit.getPrix().multiply(BigDecimal.valueOf(qte)));
        };
        detailsString=detailsString+"Montant des taxes : " + displayFormatEuro(montantPaiement.subtract(montantHT))+"\n";
        detailsString=detailsString+"Total : " + displayFormatEuro(montantPaiement)+"\n";
        facture.setDetails(detailsString);
        facture.setMontantPaiement(montantPaiement);

        return facture;
    }
    public static BigDecimal arrondiAuCinqCentimesSuperieur(BigDecimal bd) {
        int reste = bd.multiply(BigDecimal.valueOf(100)).remainder(BigDecimal.valueOf(10)).intValue();
        if (reste==0){
            return bd.setScale(2,RoundingMode.HALF_UP);
        }

        if (bd.add(BigDecimal.valueOf(0.05)).multiply(BigDecimal.valueOf(100)).remainder(BigDecimal.valueOf(10)).intValue()==0){
            return bd.setScale(2,RoundingMode.HALF_UP);
        }
        BigDecimal result = bd.setScale(1,RoundingMode.UP);
        result = result.setScale(2,RoundingMode.HALF_UP);
        if (reste < 5) {
            result = result.subtract(BigDecimal.valueOf(0.05));
        }
        return result;
    }
    public static String displayFormatEuro(BigDecimal result) {
        Currency cur = Currency.getInstance("EUR");
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setCurrency(cur);
        nf.setMaximumFractionDigits(cur.getDefaultFractionDigits());
        return nf.format(result.doubleValue());
    }
    /**
     * Save a facture.
     *
     * @param facture the entity to save.
     * @return the persisted entity.
     */
    public Facture save(Facture facture) {
        log.debug("Request to save Facture : {}", facture);
        return factureRepository.save(facture);
    }

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Facture> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable);
    }


    /**
     * Get one facture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Facture> findOne(Long id) {
        log.debug("Request to get Facture : {}", id);
        return factureRepository.findById(id);
    }

    /**
     * Delete the facture by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Facture : {}", id);
        factureRepository.deleteById(id);
    }
}
