import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../especialidade-especialista.test-samples';

import { EspecialidadeEspecialistaFormService } from './especialidade-especialista-form.service';

describe('EspecialidadeEspecialista Form Service', () => {
  let service: EspecialidadeEspecialistaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EspecialidadeEspecialistaFormService);
  });

  describe('Service methods', () => {
    describe('createEspecialidadeEspecialistaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            especialidade: expect.any(Object),
            especialista: expect.any(Object),
          }),
        );
      });

      it('passing IEspecialidadeEspecialista should create a new form with FormGroup', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            especialidade: expect.any(Object),
            especialista: expect.any(Object),
          }),
        );
      });
    });

    describe('getEspecialidadeEspecialista', () => {
      it('should return NewEspecialidadeEspecialista for default EspecialidadeEspecialista initial value', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup(sampleWithNewData);

        const especialidadeEspecialista = service.getEspecialidadeEspecialista(formGroup) as any;

        expect(especialidadeEspecialista).toMatchObject(sampleWithNewData);
      });

      it('should return NewEspecialidadeEspecialista for empty EspecialidadeEspecialista initial value', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup();

        const especialidadeEspecialista = service.getEspecialidadeEspecialista(formGroup) as any;

        expect(especialidadeEspecialista).toMatchObject({});
      });

      it('should return IEspecialidadeEspecialista', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup(sampleWithRequiredData);

        const especialidadeEspecialista = service.getEspecialidadeEspecialista(formGroup) as any;

        expect(especialidadeEspecialista).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEspecialidadeEspecialista should not enable id FormControl', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEspecialidadeEspecialista should disable id FormControl', () => {
        const formGroup = service.createEspecialidadeEspecialistaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
