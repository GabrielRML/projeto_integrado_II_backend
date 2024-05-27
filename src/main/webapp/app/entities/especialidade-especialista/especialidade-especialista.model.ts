import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { IEspecialista } from 'app/entities/especialista/especialista.model';

export interface IEspecialidadeEspecialista {
  id: number;
  especialidade?: Pick<IEspecialidade, 'id'> | null;
  especialista?: Pick<IEspecialista, 'id'> | null;
}

export type NewEspecialidadeEspecialista = Omit<IEspecialidadeEspecialista, 'id'> & { id: null };
