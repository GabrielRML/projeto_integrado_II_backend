import dayjs from 'dayjs/esm';

import { IEspecialista, NewEspecialista } from './especialista.model';

export const sampleWithRequiredData: IEspecialista = {
  id: 4808,
};

export const sampleWithPartialData: IEspecialista = {
  id: 1730,
  birthDate: dayjs('2024-05-27T04:53'),
  description: 'until after corner',
  specialistType: 'PSICOLOGO',
};

export const sampleWithFullData: IEspecialista = {
  id: 21781,
  cpf: 'attach',
  identification: 'yet excluding',
  birthDate: dayjs('2024-05-27T19:18'),
  description: 'now',
  price: 20685.66,
  timeSession: 19485,
  specialistType: 'ALUNO',
};

export const sampleWithNewData: NewEspecialista = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
