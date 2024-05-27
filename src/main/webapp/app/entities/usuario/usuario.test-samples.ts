import dayjs from 'dayjs/esm';

import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 17696,
};

export const sampleWithPartialData: IUsuario = {
  id: 17455,
  cpf: 'duh naturally',
  birthDate: dayjs('2024-05-26T23:23'),
};

export const sampleWithFullData: IUsuario = {
  id: 1635,
  cpf: 'valuable yum',
  birthDate: dayjs('2024-05-27T08:06'),
};

export const sampleWithNewData: NewUsuario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
