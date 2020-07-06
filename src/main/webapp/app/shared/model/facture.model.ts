import { Moment } from 'moment';
import { StatutFacture } from 'app/shared/model/enumerations/statut-facture.model';
import { MethodePaiement } from 'app/shared/model/enumerations/methode-paiement.model';

export interface IFacture {
  id?: number;
  code?: string;
  date?: Moment;
  details?: string;
  statut?: StatutFacture;
  methodePaiement?: MethodePaiement;
  datePaiement?: Moment;
  montantPaiement?: number;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public code?: string,
    public date?: Moment,
    public details?: string,
    public statut?: StatutFacture,
    public methodePaiement?: MethodePaiement,
    public datePaiement?: Moment,
    public montantPaiement?: number
  ) {}
}
