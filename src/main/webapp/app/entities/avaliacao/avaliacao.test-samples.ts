import { IAvaliacao, NewAvaliacao } from './avaliacao.model';

export const sampleWithRequiredData: IAvaliacao = {
  id: 15574,
};

export const sampleWithPartialData: IAvaliacao = {
  id: 11666,
  assessment: 'dolor',
  note: 20974,
};

export const sampleWithFullData: IAvaliacao = {
  id: 11716,
  assessment: 'meh',
  note: 18055,
};

export const sampleWithNewData: NewAvaliacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
