import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { IUsuario } from 'app/entities/usuario/usuario.model';

export interface IAvaliacao {
  id: number;
  assessment?: string | null;
  note?: number | null;
  avaliado?: Pick<IEspecialista, 'id'> | null;
  avaliador?: Pick<IUsuario, 'id'> | null;
}

export type NewAvaliacao = Omit<IAvaliacao, 'id'> & { id: null };
