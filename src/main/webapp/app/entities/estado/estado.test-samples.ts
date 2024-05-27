import { IEstado, NewEstado } from './estado.model';

export const sampleWithRequiredData: IEstado = {
  id: 20127,
};

export const sampleWithPartialData: IEstado = {
  id: 20240,
  nome: 'to',
  sigla: 'helplessly coordinate',
};

export const sampleWithFullData: IEstado = {
  id: 26709,
  nome: 'out',
  sigla: 'oversee leer',
};

export const sampleWithNewData: NewEstado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
