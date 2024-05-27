import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../universidade.test-samples';

import { UniversidadeFormService } from './universidade-form.service';

describe('Universidade Form Service', () => {
  let service: UniversidadeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UniversidadeFormService);
  });

  describe('Service methods', () => {
    describe('createUniversidadeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUniversidadeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cnpj: expect.any(Object),
            name: expect.any(Object),
            cep: expect.any(Object),
          }),
        );
      });

      it('passing IUniversidade should create a new form with FormGroup', () => {
        const formGroup = service.createUniversidadeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cnpj: expect.any(Object),
            name: expect.any(Object),
            cep: expect.any(Object),
          }),
        );
      });
    });

    describe('getUniversidade', () => {
      it('should return NewUniversidade for default Universidade initial value', () => {
        const formGroup = service.createUniversidadeFormGroup(sampleWithNewData);

        const universidade = service.getUniversidade(formGroup) as any;

        expect(universidade).toMatchObject(sampleWithNewData);
      });

      it('should return NewUniversidade for empty Universidade initial value', () => {
        const formGroup = service.createUniversidadeFormGroup();

        const universidade = service.getUniversidade(formGroup) as any;

        expect(universidade).toMatchObject({});
      });

      it('should return IUniversidade', () => {
        const formGroup = service.createUniversidadeFormGroup(sampleWithRequiredData);

        const universidade = service.getUniversidade(formGroup) as any;

        expect(universidade).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUniversidade should not enable id FormControl', () => {
        const formGroup = service.createUniversidadeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUniversidade should disable id FormControl', () => {
        const formGroup = service.createUniversidadeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
