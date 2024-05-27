import { IEspecialidade, NewEspecialidade } from './especialidade.model';

export const sampleWithRequiredData: IEspecialidade = {
  id: 19956,
};

export const sampleWithPartialData: IEspecialidade = {
  id: 19215,
  name: 'gregarious',
};

export const sampleWithFullData: IEspecialidade = {
  id: 29235,
  name: 'imaginative razz sermonise',
};

export const sampleWithNewData: NewEspecialidade = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
