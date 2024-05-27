import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEstado } from 'app/entities/estado/estado.model';
import { EstadoService } from 'app/entities/estado/service/estado.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IUniversidade } from 'app/entities/universidade/universidade.model';
import { UniversidadeService } from 'app/entities/universidade/service/universidade.service';
import { IEspecialista } from '../especialista.model';
import { EspecialistaService } from '../service/especialista.service';
import { EspecialistaFormService } from './especialista-form.service';

import { EspecialistaUpdateComponent } from './especialista-update.component';

describe('Especialista Management Update Component', () => {
  let comp: EspecialistaUpdateComponent;
  let fixture: ComponentFixture<EspecialistaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let especialistaFormService: EspecialistaFormService;
  let especialistaService: EspecialistaService;
  let userService: UserService;
  let estadoService: EstadoService;
  let cidadeService: CidadeService;
  let universidadeService: UniversidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), EspecialistaUpdateComponent],
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
      .overrideTemplate(EspecialistaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EspecialistaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    especialistaFormService = TestBed.inject(EspecialistaFormService);
    especialistaService = TestBed.inject(EspecialistaService);
    userService = TestBed.inject(UserService);
    estadoService = TestBed.inject(EstadoService);
    cidadeService = TestBed.inject(CidadeService);
    universidadeService = TestBed.inject(UniversidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const especialista: IEspecialista = { id: 456 };
      const internalUser: IUser = { id: 1144 };
      especialista.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 24741 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Especialista query and add missing value', () => {
      const especialista: IEspecialista = { id: 456 };
      const supervisorId: IEspecialista = { id: 27466 };
      especialista.supervisorId = supervisorId;

      const especialistaCollection: IEspecialista[] = [{ id: 25230 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [supervisorId];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Estado query and add missing value', () => {
      const especialista: IEspecialista = { id: 456 };
      const estado: IEstado = { id: 24687 };
      especialista.estado = estado;

      const estadoCollection: IEstado[] = [{ id: 17926 }];
      jest.spyOn(estadoService, 'query').mockReturnValue(of(new HttpResponse({ body: estadoCollection })));
      const additionalEstados = [estado];
      const expectedCollection: IEstado[] = [...additionalEstados, ...estadoCollection];
      jest.spyOn(estadoService, 'addEstadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(estadoService.query).toHaveBeenCalled();
      expect(estadoService.addEstadoToCollectionIfMissing).toHaveBeenCalledWith(
        estadoCollection,
        ...additionalEstados.map(expect.objectContaining),
      );
      expect(comp.estadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cidade query and add missing value', () => {
      const especialista: IEspecialista = { id: 456 };
      const cidade: ICidade = { id: 13247 };
      especialista.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 3927 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Universidade query and add missing value', () => {
      const especialista: IEspecialista = { id: 456 };
      const universidade: IUniversidade = { id: 10489 };
      especialista.universidade = universidade;

      const universidadeCollection: IUniversidade[] = [{ id: 1670 }];
      jest.spyOn(universidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: universidadeCollection })));
      const additionalUniversidades = [universidade];
      const expectedCollection: IUniversidade[] = [...additionalUniversidades, ...universidadeCollection];
      jest.spyOn(universidadeService, 'addUniversidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(universidadeService.query).toHaveBeenCalled();
      expect(universidadeService.addUniversidadeToCollectionIfMissing).toHaveBeenCalledWith(
        universidadeCollection,
        ...additionalUniversidades.map(expect.objectContaining),
      );
      expect(comp.universidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const especialista: IEspecialista = { id: 456 };
      const internalUser: IUser = { id: 32257 };
      especialista.internalUser = internalUser;
      const supervisorId: IEspecialista = { id: 20361 };
      especialista.supervisorId = supervisorId;
      const estado: IEstado = { id: 4160 };
      especialista.estado = estado;
      const cidade: ICidade = { id: 5119 };
      especialista.cidade = cidade;
      const universidade: IUniversidade = { id: 8391 };
      especialista.universidade = universidade;

      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(internalUser);
      expect(comp.especialistasSharedCollection).toContain(supervisorId);
      expect(comp.estadosSharedCollection).toContain(estado);
      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.universidadesSharedCollection).toContain(universidade);
      expect(comp.especialista).toEqual(especialista);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialista>>();
      const especialista = { id: 123 };
      jest.spyOn(especialistaFormService, 'getEspecialista').mockReturnValue(especialista);
      jest.spyOn(especialistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialista }));
      saveSubject.complete();

      // THEN
      expect(especialistaFormService.getEspecialista).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(especialistaService.update).toHaveBeenCalledWith(expect.objectContaining(especialista));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialista>>();
      const especialista = { id: 123 };
      jest.spyOn(especialistaFormService, 'getEspecialista').mockReturnValue({ id: null });
      jest.spyOn(especialistaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialista: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialista }));
      saveSubject.complete();

      // THEN
      expect(especialistaFormService.getEspecialista).toHaveBeenCalled();
      expect(especialistaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialista>>();
      const especialista = { id: 123 };
      jest.spyOn(especialistaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialista });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(especialistaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareEstado', () => {
      it('Should forward to estadoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(estadoService, 'compareEstado');
        comp.compareEstado(entity, entity2);
        expect(estadoService.compareEstado).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUniversidade', () => {
      it('Should forward to universidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(universidadeService, 'compareUniversidade');
        comp.compareUniversidade(entity, entity2);
        expect(universidadeService.compareUniversidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
