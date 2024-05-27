import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../consulta.test-samples';

import { ConsultaFormService } from './consulta-form.service';

describe('Consulta Form Service', () => {
  let service: ConsultaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConsultaFormService);
  });

  describe('Service methods', () => {
    describe('createConsultaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConsultaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            reason: expect.any(Object),
            link: expect.any(Object),
            status: expect.any(Object),
            avaliacao: expect.any(Object),
            prestador: expect.any(Object),
            cliente: expect.any(Object),
          }),
        );
      });

      it('passing IConsulta should create a new form with FormGroup', () => {
        const formGroup = service.createConsultaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            reason: expect.any(Object),
            link: expect.any(Object),
            status: expect.any(Object),
            avaliacao: expect.any(Object),
            prestador: expect.any(Object),
            cliente: expect.any(Object),
          }),
        );
      });
    });

    describe('getConsulta', () => {
      it('should return NewConsulta for default Consulta initial value', () => {
        const formGroup = service.createConsultaFormGroup(sampleWithNewData);

        const consulta = service.getConsulta(formGroup) as any;

        expect(consulta).toMatchObject(sampleWithNewData);
      });

      it('should return NewConsulta for empty Consulta initial value', () => {
        const formGroup = service.createConsultaFormGroup();

        const consulta = service.getConsulta(formGroup) as any;

        expect(consulta).toMatchObject({});
      });

      it('should return IConsulta', () => {
        const formGroup = service.createConsultaFormGroup(sampleWithRequiredData);

        const consulta = service.getConsulta(formGroup) as any;

        expect(consulta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConsulta should not enable id FormControl', () => {
        const formGroup = service.createConsultaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConsulta should disable id FormControl', () => {
        const formGroup = service.createConsultaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
