import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../especialidade-especialista.test-samples';

import { EspecialidadeEspecialistaService } from './especialidade-especialista.service';

const requireRestSample: IEspecialidadeEspecialista = {
  ...sampleWithRequiredData,
};

describe('EspecialidadeEspecialista Service', () => {
  let service: EspecialidadeEspecialistaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEspecialidadeEspecialista | IEspecialidadeEspecialista[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EspecialidadeEspecialistaService);
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

    it('should create a EspecialidadeEspecialista', () => {
      const especialidadeEspecialista = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(especialidadeEspecialista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EspecialidadeEspecialista', () => {
      const especialidadeEspecialista = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(especialidadeEspecialista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EspecialidadeEspecialista', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EspecialidadeEspecialista', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EspecialidadeEspecialista', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEspecialidadeEspecialistaToCollectionIfMissing', () => {
      it('should add a EspecialidadeEspecialista to an empty array', () => {
        const especialidadeEspecialista: IEspecialidadeEspecialista = sampleWithRequiredData;
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing([], especialidadeEspecialista);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(especialidadeEspecialista);
      });

      it('should not add a EspecialidadeEspecialista to an array that contains it', () => {
        const especialidadeEspecialista: IEspecialidadeEspecialista = sampleWithRequiredData;
        const especialidadeEspecialistaCollection: IEspecialidadeEspecialista[] = [
          {
            ...especialidadeEspecialista,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing(
          especialidadeEspecialistaCollection,
          especialidadeEspecialista,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EspecialidadeEspecialista to an array that doesn't contain it", () => {
        const especialidadeEspecialista: IEspecialidadeEspecialista = sampleWithRequiredData;
        const especialidadeEspecialistaCollection: IEspecialidadeEspecialista[] = [sampleWithPartialData];
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing(
          especialidadeEspecialistaCollection,
          especialidadeEspecialista,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(especialidadeEspecialista);
      });

      it('should add only unique EspecialidadeEspecialista to an array', () => {
        const especialidadeEspecialistaArray: IEspecialidadeEspecialista[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const especialidadeEspecialistaCollection: IEspecialidadeEspecialista[] = [sampleWithRequiredData];
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing(
          especialidadeEspecialistaCollection,
          ...especialidadeEspecialistaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const especialidadeEspecialista: IEspecialidadeEspecialista = sampleWithRequiredData;
        const especialidadeEspecialista2: IEspecialidadeEspecialista = sampleWithPartialData;
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing(
          [],
          especialidadeEspecialista,
          especialidadeEspecialista2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(especialidadeEspecialista);
        expect(expectedResult).toContain(especialidadeEspecialista2);
      });

      it('should accept null and undefined values', () => {
        const especialidadeEspecialista: IEspecialidadeEspecialista = sampleWithRequiredData;
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing([], null, especialidadeEspecialista, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(especialidadeEspecialista);
      });

      it('should return initial array if no EspecialidadeEspecialista is added', () => {
        const especialidadeEspecialistaCollection: IEspecialidadeEspecialista[] = [sampleWithRequiredData];
        expectedResult = service.addEspecialidadeEspecialistaToCollectionIfMissing(especialidadeEspecialistaCollection, undefined, null);
        expect(expectedResult).toEqual(especialidadeEspecialistaCollection);
      });
    });

    describe('compareEspecialidadeEspecialista', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEspecialidadeEspecialista(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEspecialidadeEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialidadeEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEspecialidadeEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialidadeEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEspecialidadeEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialidadeEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
