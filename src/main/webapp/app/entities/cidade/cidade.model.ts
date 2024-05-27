import { IEstado } from 'app/entities/estado/estado.model';

export interface ICidade {
  id: number;
  nome?: string | null;
  sigla?: string | null;
  estado?: Pick<IEstado, 'id'> | null;
}

export type NewCidade = Omit<ICidade, 'id'> & { id: null };
