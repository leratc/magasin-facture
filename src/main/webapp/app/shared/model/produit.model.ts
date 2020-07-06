import { ICategorieProduit } from 'app/shared/model/categorie-produit.model';

export interface IProduit {
  id?: number;
  nom?: string;
  description?: string;
  prix?: number;
  categorieProduit?: ICategorieProduit;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public prix?: number,
    public categorieProduit?: ICategorieProduit
  ) {}
}
