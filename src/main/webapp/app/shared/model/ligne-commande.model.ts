import { IProduit } from 'app/shared/model/produit.model';
import { ICommandeProduit } from 'app/shared/model/commande-produit.model';
import { StatutLigneCommande } from 'app/shared/model/enumerations/statut-ligne-commande.model';

export interface ILigneCommande {
  id?: number;
  quantite?: number;
  prixTotalHT?: number;
  statut?: StatutLigneCommande;
  produit?: IProduit;
  commande?: ICommandeProduit;
}

export class LigneCommande implements ILigneCommande {
  constructor(
    public id?: number,
    public quantite?: number,
    public prixTotalHT?: number,
    public statut?: StatutLigneCommande,
    public produit?: IProduit,
    public commande?: ICommandeProduit
  ) {}
}
