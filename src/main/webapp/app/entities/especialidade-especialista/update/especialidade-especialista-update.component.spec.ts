import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';
import { EspecialidadeEspecialistaService } from '../service/especialidade-especialista.service';
import { EspecialidadeEspecialistaFormService } from './especialidade-especialista-form.service';

import { EspecialidadeEspecialistaUpdateComponent } from './especialidade-especialista-update.component';

describe('EspecialidadeEspecialista Management Update Component', () => {
  let comp: EspecialidadeEspecialistaUpdateComponent;
  let fixture: ComponentFixture<EspecialidadeEspecialistaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let especialidadeEspecialistaFormService: EspecialidadeEspecialistaFormService;
  let especialidadeEspecialistaService: EspecialidadeEspecialistaService;
  let especialidadeService: EspecialidadeService;
  let especialistaService: EspecialistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EspecialidadeEspecialistaUpdateComponent],
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
      .overrideTemplate(EspecialidadeEspecialistaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EspecialidadeEspecialistaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    especialidadeEspecialistaFormService = TestBed.inject(EspecialidadeEspecialistaFormService);
    especialidadeEspecialistaService = TestBed.inject(EspecialidadeEspecialistaService);
    especialidadeService = TestBed.inject(EspecialidadeService);
    especialistaService = TestBed.inject(EspecialistaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Especialidade query and add missing value', () => {
      const especialidadeEspecialista: IEspecialidadeEspecialista = { id: 456 };
      const especialidade: IEspecialidade = { id: 14626 };
      especialidadeEspecialista.especialidade = especialidade;

      const especialidadeCollection: IEspecialidade[] = [{ id: 26536 }];
      jest.spyOn(especialidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: especialidadeCollection })));
      const additionalEspecialidades = [especialidade];
      const expectedCollection: IEspecialidade[] = [...additionalEspecialidades, ...especialidadeCollection];
      jest.spyOn(especialidadeService, 'addEspecialidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialidadeEspecialista });
      comp.ngOnInit();

      expect(especialidadeService.query).toHaveBeenCalled();
      expect(especialidadeService.addEspecialidadeToCollectionIfMissing).toHaveBeenCalledWith(
        especialidadeCollection,
        ...additionalEspecialidades.map(expect.objectContaining),
      );
      expect(comp.especialidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Especialista query and add missing value', () => {
      const especialidadeEspecialista: IEspecialidadeEspecialista = { id: 456 };
      const especialista: IEspecialista = { id: 25868 };
      especialidadeEspecialista.especialista = especialista;

      const especialistaCollection: IEspecialista[] = [{ id: 29183 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [especialista];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialidadeEspecialista });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const especialidadeEspecialista: IEspecialidadeEspecialista = { id: 456 };
      const especialidade: IEspecialidade = { id: 12568 };
      especialidadeEspecialista.especialidade = especialidade;
      const especialista: IEspecialista = { id: 26857 };
      especialidadeEspecialista.especialista = especialista;

      activatedRoute.data = of({ especialidadeEspecialista });
      comp.ngOnInit();

      expect(comp.especialidadesSharedCollection).toContain(especialidade);
      expect(comp.especialistasSharedCollection).toContain(especialista);
      expect(comp.especialidadeEspecialista).toEqual(especialidadeEspecialista);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidadeEspecialista>>();
      const especialidadeEspecialista = { id: 123 };
      jest.spyOn(especialidadeEspecialistaFormService, 'getEspecialidadeEspecialista').mockReturnValue(especialidadeEspecialista);
      jest.spyOn(especialidadeEspecialistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidadeEspecialista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialidadeEspecialista }));
      saveSubject.complete();

      // THEN
      expect(especialidadeEspecialistaFormService.getEspecialidadeEspecialista).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(especialidadeEspecialistaService.update).toHaveBeenCalledWith(expect.objectContaining(especialidadeEspecialista));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidadeEspecialista>>();
      const especialidadeEspecialista = { id: 123 };
      jest.spyOn(especialidadeEspecialistaFormService, 'getEspecialidadeEspecialista').mockReturnValue({ id: null });
      jest.spyOn(especialidadeEspecialistaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidadeEspecialista: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialidadeEspecialista }));
      saveSubject.complete();

      // THEN
      expect(especialidadeEspecialistaFormService.getEspecialidadeEspecialista).toHaveBeenCalled();
      expect(especialidadeEspecialistaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidadeEspecialista>>();
      const especialidadeEspecialista = { id: 123 };
      jest.spyOn(especialidadeEspecialistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidadeEspecialista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(especialidadeEspecialistaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEspecialidade', () => {
      it('Should forward to especialidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(especialidadeService, 'compareEspecialidade');
        comp.compareEspecialidade(entity, entity2);
        expect(especialidadeService.compareEspecialidade).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEspecialista', () => {
      it('Should forward to especialistaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(especialistaService, 'compareEspecialista');
        comp.compareEspecialista(entity, entity2);
        expect(especialistaService.compareEspecialista).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
