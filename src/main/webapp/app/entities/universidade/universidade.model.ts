export interface IUniversidade {
  id: number;
  cnpj?: string | null;
  name?: string | null;
  cep?: string | null;
}

export type NewUniversidade = Omit<IUniversidade, 'id'> & { id: null };
