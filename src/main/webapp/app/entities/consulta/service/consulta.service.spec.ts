import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IConsulta } from '../consulta.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../consulta.test-samples';

import { ConsultaService, RestConsulta } from './consulta.service';

const requireRestSample: RestConsulta = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('Consulta Service', () => {
  let service: ConsultaService;
  let httpMock: HttpTestingController;
  let expectedResult: IConsulta | IConsulta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConsultaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Consulta', () => {
      const consulta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(consulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Consulta', () => {
      const consulta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(consulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Consulta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Consulta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Consulta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConsultaToCollectionIfMissing', () => {
      it('should add a Consulta to an empty array', () => {
        const consulta: IConsulta = sampleWithRequiredData;
        expectedResult = service.addConsultaToCollectionIfMissing([], consulta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consulta);
      });

      it('should not add a Consulta to an array that contains it', () => {
        const consulta: IConsulta = sampleWithRequiredData;
        const consultaCollection: IConsulta[] = [
          {
            ...consulta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConsultaToCollectionIfMissing(consultaCollection, consulta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Consulta to an array that doesn't contain it", () => {
        const consulta: IConsulta = sampleWithRequiredData;
        const consultaCollection: IConsulta[] = [sampleWithPartialData];
        expectedResult = service.addConsultaToCollectionIfMissing(consultaCollection, consulta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consulta);
      });

      it('should add only unique Consulta to an array', () => {
        const consultaArray: IConsulta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const consultaCollection: IConsulta[] = [sampleWithRequiredData];
        expectedResult = service.addConsultaToCollectionIfMissing(consultaCollection, ...consultaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const consulta: IConsulta = sampleWithRequiredData;
        const consulta2: IConsulta = sampleWithPartialData;
        expectedResult = service.addConsultaToCollectionIfMissing([], consulta, consulta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consulta);
        expect(expectedResult).toContain(consulta2);
      });

      it('should accept null and undefined values', () => {
        const consulta: IConsulta = sampleWithRequiredData;
        expectedResult = service.addConsultaToCollectionIfMissing([], null, consulta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consulta);
      });

      it('should return initial array if no Consulta is added', () => {
        const consultaCollection: IConsulta[] = [sampleWithRequiredData];
        expectedResult = service.addConsultaToCollectionIfMissing(consultaCollection, undefined, null);
        expect(expectedResult).toEqual(consultaCollection);
      });
    });

    describe('compareConsulta', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConsulta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareConsulta(entity1, entity2);
        const compareResult2 = service.compareConsulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareConsulta(entity1, entity2);
        const compareResult2 = service.compareConsulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareConsulta(entity1, entity2);
        const compareResult2 = service.compareConsulta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
