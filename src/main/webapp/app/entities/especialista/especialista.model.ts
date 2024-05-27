import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IEstado } from 'app/entities/estado/estado.model';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { IUniversidade } from 'app/entities/universidade/universidade.model';
import { SpecialistType } from 'app/entities/enumerations/specialist-type.model';

export interface IEspecialista {
  id: number;
  cpf?: string | null;
  identification?: string | null;
  birthDate?: dayjs.Dayjs | null;
  description?: string | null;
  price?: number | null;
  timeSession?: number | null;
  specialistType?: keyof typeof SpecialistType | null;
  internalUser?: Pick<IUser, 'id'> | null;
  estado?: Pick<IEstado, 'id'> | null;
  cidade?: Pick<ICidade, 'id'> | null;
  universidade?: Pick<IUniversidade, 'id'> | null;
  supervisorId?: Pick<IEspecialista, 'id'> | null;
}

export type NewEspecialista = Omit<IEspecialista, 'id'> & { id: null };
