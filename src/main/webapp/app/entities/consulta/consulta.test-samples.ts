import dayjs from 'dayjs/esm';

import { IConsulta, NewConsulta } from './consulta.model';

export const sampleWithRequiredData: IConsulta = {
  id: 10558,
};

export const sampleWithPartialData: IConsulta = {
  id: 22610,
  link: 'inasmuch',
  status: 'CONCLUIDA',
};

export const sampleWithFullData: IConsulta = {
  id: 15162,
  date: dayjs('2024-05-27T15:45'),
  reason: 'beneath anenst pushy',
  link: 'wildly',
  status: 'CONCLUIDA',
};

export const sampleWithNewData: NewConsulta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
