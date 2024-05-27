import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';
import { AvaliacaoService } from 'app/entities/avaliacao/service/avaliacao.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';
import { IConsulta } from '../consulta.model';
import { ConsultaService } from '../service/consulta.service';
import { ConsultaFormService } from './consulta-form.service';

import { ConsultaUpdateComponent } from './consulta-update.component';

describe('Consulta Management Update Component', () => {
  let comp: ConsultaUpdateComponent;
  let fixture: ComponentFixture<ConsultaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let consultaFormService: ConsultaFormService;
  let consultaService: ConsultaService;
  let avaliacaoService: AvaliacaoService;
  let especialistaService: EspecialistaService;
  let usuarioService: UsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ConsultaUpdateComponent],
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
      .overrideTemplate(ConsultaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConsultaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    consultaFormService = TestBed.inject(ConsultaFormService);
    consultaService = TestBed.inject(ConsultaService);
    avaliacaoService = TestBed.inject(AvaliacaoService);
    especialistaService = TestBed.inject(EspecialistaService);
    usuarioService = TestBed.inject(UsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call avaliacao query and add missing value', () => {
      const consulta: IConsulta = { id: 456 };
      const avaliacao: IAvaliacao = { id: 7744 };
      consulta.avaliacao = avaliacao;

      const avaliacaoCollection: IAvaliacao[] = [{ id: 6777 }];
      jest.spyOn(avaliacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: avaliacaoCollection })));
      const expectedCollection: IAvaliacao[] = [avaliacao, ...avaliacaoCollection];
      jest.spyOn(avaliacaoService, 'addAvaliacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      expect(avaliacaoService.query).toHaveBeenCalled();
      expect(avaliacaoService.addAvaliacaoToCollectionIfMissing).toHaveBeenCalledWith(avaliacaoCollection, avaliacao);
      expect(comp.avaliacaosCollection).toEqual(expectedCollection);
    });

    it('Should call Especialista query and add missing value', () => {
      const consulta: IConsulta = { id: 456 };
      const prestador: IEspecialista = { id: 26764 };
      consulta.prestador = prestador;

      const especialistaCollection: IEspecialista[] = [{ id: 32106 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [prestador];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Usuario query and add missing value', () => {
      const consulta: IConsulta = { id: 456 };
      const cliente: IUsuario = { id: 7424 };
      consulta.cliente = cliente;

      const usuarioCollection: IUsuario[] = [{ id: 26948 }];
      jest.spyOn(usuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioCollection })));
      const additionalUsuarios = [cliente];
      const expectedCollection: IUsuario[] = [...additionalUsuarios, ...usuarioCollection];
      jest.spyOn(usuarioService, 'addUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      expect(usuarioService.query).toHaveBeenCalled();
      expect(usuarioService.addUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioCollection,
        ...additionalUsuarios.map(expect.objectContaining),
      );
      expect(comp.usuariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const consulta: IConsulta = { id: 456 };
      const avaliacao: IAvaliacao = { id: 29296 };
      consulta.avaliacao = avaliacao;
      const prestador: IEspecialista = { id: 30134 };
      consulta.prestador = prestador;
      const cliente: IUsuario = { id: 16260 };
      consulta.cliente = cliente;

      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      expect(comp.avaliacaosCollection).toContain(avaliacao);
      expect(comp.especialistasSharedCollection).toContain(prestador);
      expect(comp.usuariosSharedCollection).toContain(cliente);
      expect(comp.consulta).toEqual(consulta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConsulta>>();
      const consulta = { id: 123 };
      jest.spyOn(consultaFormService, 'getConsulta').mockReturnValue(consulta);
      jest.spyOn(consultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consulta }));
      saveSubject.complete();

      // THEN
      expect(consultaFormService.getConsulta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(consultaService.update).toHaveBeenCalledWith(expect.objectContaining(consulta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConsulta>>();
      const consulta = { id: 123 };
      jest.spyOn(consultaFormService, 'getConsulta').mockReturnValue({ id: null });
      jest.spyOn(consultaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consulta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: consulta }));
      saveSubject.complete();

      // THEN
      expect(consultaFormService.getConsulta).toHaveBeenCalled();
      expect(consultaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConsulta>>();
      const consulta = { id: 123 };
      jest.spyOn(consultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ consulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(consultaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAvaliacao', () => {
      it('Should forward to avaliacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(avaliacaoService, 'compareAvaliacao');
        comp.compareAvaliacao(entity, entity2);
        expect(avaliacaoService.compareAvaliacao).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareUsuario', () => {
      it('Should forward to usuarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioService, 'compareUsuario');
        comp.compareUsuario(entity, entity2);
        expect(usuarioService.compareUsuario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
