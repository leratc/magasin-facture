import { Moment } from 'moment';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { IClient } from 'app/shared/model/client.model';
import { StatutCommande } from 'app/shared/model/enumerations/statut-commande.model';

export interface ICommandeProduit {
  id?: number;
  dateCommande?: Moment;
  statut?: StatutCommande;
  code?: string;
  factureId?: number;
  ligneCommandes?: ILigneCommande[];
  client?: IClient;
}

export class CommandeProduit implements ICommandeProduit {
  constructor(
    public id?: number,
    public dateCommande?: Moment,
    public statut?: StatutCommande,
    public code?: string,
    public factureId?: number,
    public ligneCommandes?: ILigneCommande[],
    public client?: IClient
  ) {}
}
