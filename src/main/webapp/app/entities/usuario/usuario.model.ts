import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IEstado } from 'app/entities/estado/estado.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IUsuario {
  id: number;
  cpf?: string | null;
  birthDate?: dayjs.Dayjs | null;
  internalUser?: Pick<IUser, 'id'> | null;
  estado?: Pick<IEstado, 'id'> | null;
  cidade?: Pick<ICidade, 'id'> | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
