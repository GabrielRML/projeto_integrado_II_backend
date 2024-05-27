export interface IEspecialidade {
  id: number;
  name?: string | null;
}

export type NewEspecialidade = Omit<IEspecialidade, 'id'> & { id: null };
