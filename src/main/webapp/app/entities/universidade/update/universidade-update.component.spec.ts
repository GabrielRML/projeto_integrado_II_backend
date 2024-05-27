import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UniversidadeService } from '../service/universidade.service';
import { IUniversidade } from '../universidade.model';
import { UniversidadeFormService } from './universidade-form.service';

import { UniversidadeUpdateComponent } from './universidade-update.component';

describe('Universidade Management Update Component', () => {
  let comp: UniversidadeUpdateComponent;
  let fixture: ComponentFixture<UniversidadeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let universidadeFormService: UniversidadeFormService;
  let universidadeService: UniversidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), UniversidadeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UniversidadeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UniversidadeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    universidadeFormService = TestBed.inject(UniversidadeFormService);
    universidadeService = TestBed.inject(UniversidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const universidade: IUniversidade = { id: 456 };

      activatedRoute.data = of({ universidade });
      comp.ngOnInit();

      expect(comp.universidade).toEqual(universidade);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUniversidade>>();
      const universidade = { id: 123 };
      jest.spyOn(universidadeFormService, 'getUniversidade').mockReturnValue(universidade);
      jest.spyOn(universidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ universidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: universidade }));
      saveSubject.complete();

      // THEN
      expect(universidadeFormService.getUniversidade).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(universidadeService.update).toHaveBeenCalledWith(expect.objectContaining(universidade));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUniversidade>>();
      const universidade = { id: 123 };
      jest.spyOn(universidadeFormService, 'getUniversidade').mockReturnValue({ id: null });
      jest.spyOn(universidadeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ universidade: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: universidade }));
      saveSubject.complete();

      // THEN
      expect(universidadeFormService.getUniversidade).toHaveBeenCalled();
      expect(universidadeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUniversidade>>();
      const universidade = { id: 123 };
      jest.spyOn(universidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ universidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(universidadeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
