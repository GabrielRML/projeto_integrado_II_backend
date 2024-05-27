import dayjs from 'dayjs/esm';
import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { StatusConsulta } from 'app/entities/enumerations/status-consulta.model';

export interface IConsulta {
  id: number;
  date?: dayjs.Dayjs | null;
  reason?: string | null;
  link?: string | null;
  status?: keyof typeof StatusConsulta | null;
  avaliacao?: Pick<IAvaliacao, 'id'> | null;
  prestador?: Pick<IEspecialista, 'id'> | null;
  cliente?: Pick<IUsuario, 'id'> | null;
}

export type NewConsulta = Omit<IConsulta, 'id'> & { id: null };
