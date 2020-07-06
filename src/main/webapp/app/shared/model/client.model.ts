import { IUser } from 'app/core/user/user.model';
import { ICommandeProduit } from 'app/shared/model/commande-produit.model';
import { Civilite } from 'app/shared/model/enumerations/civilite.model';

export interface IClient {
  id?: number;
  prenom?: string;
  nom?: string;
  civilite?: Civilite;
  email?: string;
  telephone?: string;
  addresseLigne1?: string;
  addresseLigne2?: string;
  ville?: string;
  pays?: string;
  user?: IUser;
  commandes?: ICommandeProduit[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string,
    public civilite?: Civilite,
    public email?: string,
    public telephone?: string,
    public addresseLigne1?: string,
    public addresseLigne2?: string,
    public ville?: string,
    public pays?: string,
    public user?: IUser,
    public commandes?: ICommandeProduit[]
  ) {}
}
