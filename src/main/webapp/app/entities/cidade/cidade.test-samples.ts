import { ICidade, NewCidade } from './cidade.model';

export const sampleWithRequiredData: ICidade = {
  id: 15231,
};

export const sampleWithPartialData: ICidade = {
  id: 24892,
  nome: 'whether ram',
  sigla: 'questioningly',
};

export const sampleWithFullData: ICidade = {
  id: 10890,
  nome: 'sweaty worth',
  sigla: 'ew for guide',
};

export const sampleWithNewData: NewCidade = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
