import { IEspecialidadeEspecialista, NewEspecialidadeEspecialista } from './especialidade-especialista.model';

export const sampleWithRequiredData: IEspecialidadeEspecialista = {
  id: 27579,
};

export const sampleWithPartialData: IEspecialidadeEspecialista = {
  id: 32432,
};

export const sampleWithFullData: IEspecialidadeEspecialista = {
  id: 5397,
};

export const sampleWithNewData: NewEspecialidadeEspecialista = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
