import { IProduit } from 'app/shared/model/produit.model';

export interface ICategorieProduit {
  id?: number;
  nom?: string;
  description?: string;
  tauxTaxe?: number;
  importe?: boolean;
  produits?: IProduit[];
}

export class CategorieProduit implements ICategorieProduit {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public tauxTaxe?: number,
    public importe?: boolean,
    public produits?: IProduit[]
  ) {
    this.importe = this.importe || false;
  }
}
