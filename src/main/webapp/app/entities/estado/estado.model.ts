export interface IEstado {
  id: number;
  nome?: string | null;
  sigla?: string | null;
}

export type NewEstado = Omit<IEstado, 'id'> & { id: null };
