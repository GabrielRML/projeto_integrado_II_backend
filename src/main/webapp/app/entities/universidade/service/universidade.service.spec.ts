import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUniversidade } from '../universidade.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../universidade.test-samples';

import { UniversidadeService } from './universidade.service';

const requireRestSample: IUniversidade = {
  ...sampleWithRequiredData,
};

describe('Universidade Service', () => {
  let service: UniversidadeService;
  let httpMock: HttpTestingController;
  let expectedResult: IUniversidade | IUniversidade[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UniversidadeService);
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

    it('should create a Universidade', () => {
      const universidade = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(universidade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Universidade', () => {
      const universidade = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(universidade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Universidade', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Universidade', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Universidade', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUniversidadeToCollectionIfMissing', () => {
      it('should add a Universidade to an empty array', () => {
        const universidade: IUniversidade = sampleWithRequiredData;
        expectedResult = service.addUniversidadeToCollectionIfMissing([], universidade);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(universidade);
      });

      it('should not add a Universidade to an array that contains it', () => {
        const universidade: IUniversidade = sampleWithRequiredData;
        const universidadeCollection: IUniversidade[] = [
          {
            ...universidade,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUniversidadeToCollectionIfMissing(universidadeCollection, universidade);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Universidade to an array that doesn't contain it", () => {
        const universidade: IUniversidade = sampleWithRequiredData;
        const universidadeCollection: IUniversidade[] = [sampleWithPartialData];
        expectedResult = service.addUniversidadeToCollectionIfMissing(universidadeCollection, universidade);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(universidade);
      });

      it('should add only unique Universidade to an array', () => {
        const universidadeArray: IUniversidade[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const universidadeCollection: IUniversidade[] = [sampleWithRequiredData];
        expectedResult = service.addUniversidadeToCollectionIfMissing(universidadeCollection, ...universidadeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const universidade: IUniversidade = sampleWithRequiredData;
        const universidade2: IUniversidade = sampleWithPartialData;
        expectedResult = service.addUniversidadeToCollectionIfMissing([], universidade, universidade2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(universidade);
        expect(expectedResult).toContain(universidade2);
      });

      it('should accept null and undefined values', () => {
        const universidade: IUniversidade = sampleWithRequiredData;
        expectedResult = service.addUniversidadeToCollectionIfMissing([], null, universidade, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(universidade);
      });

      it('should return initial array if no Universidade is added', () => {
        const universidadeCollection: IUniversidade[] = [sampleWithRequiredData];
        expectedResult = service.addUniversidadeToCollectionIfMissing(universidadeCollection, undefined, null);
        expect(expectedResult).toEqual(universidadeCollection);
      });
    });

    describe('compareUniversidade', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUniversidade(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUniversidade(entity1, entity2);
        const compareResult2 = service.compareUniversidade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUniversidade(entity1, entity2);
        const compareResult2 = service.compareUniversidade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUniversidade(entity1, entity2);
        const compareResult2 = service.compareUniversidade(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
