import { IUniversidade, NewUniversidade } from './universidade.model';

export const sampleWithRequiredData: IUniversidade = {
  id: 1559,
};

export const sampleWithPartialData: IUniversidade = {
  id: 20943,
  name: 'up excepting',
  cep: 'jaw',
};

export const sampleWithFullData: IUniversidade = {
  id: 5890,
  cnpj: 'greatness kindheartedly',
  name: 'whoa gee',
  cep: 'coo on',
};

export const sampleWithNewData: NewUniversidade = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
