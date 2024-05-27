import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';
import { IAvaliacao } from '../avaliacao.model';
import { AvaliacaoService } from '../service/avaliacao.service';
import { AvaliacaoFormService } from './avaliacao-form.service';

import { AvaliacaoUpdateComponent } from './avaliacao-update.component';

describe('Avaliacao Management Update Component', () => {
  let comp: AvaliacaoUpdateComponent;
  let fixture: ComponentFixture<AvaliacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avaliacaoFormService: AvaliacaoFormService;
  let avaliacaoService: AvaliacaoService;
  let especialistaService: EspecialistaService;
  let usuarioService: UsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), AvaliacaoUpdateComponent],
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
      .overrideTemplate(AvaliacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvaliacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avaliacaoFormService = TestBed.inject(AvaliacaoFormService);
    avaliacaoService = TestBed.inject(AvaliacaoService);
    especialistaService = TestBed.inject(EspecialistaService);
    usuarioService = TestBed.inject(UsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Especialista query and add missing value', () => {
      const avaliacao: IAvaliacao = { id: 456 };
      const avaliado: IEspecialista = { id: 11637 };
      avaliacao.avaliado = avaliado;

      const especialistaCollection: IEspecialista[] = [{ id: 21351 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [avaliado];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Usuario query and add missing value', () => {
      const avaliacao: IAvaliacao = { id: 456 };
      const avaliador: IUsuario = { id: 29393 };
      avaliacao.avaliador = avaliador;

      const usuarioCollection: IUsuario[] = [{ id: 11346 }];
      jest.spyOn(usuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioCollection })));
      const additionalUsuarios = [avaliador];
      const expectedCollection: IUsuario[] = [...additionalUsuarios, ...usuarioCollection];
      jest.spyOn(usuarioService, 'addUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      expect(usuarioService.query).toHaveBeenCalled();
      expect(usuarioService.addUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioCollection,
        ...additionalUsuarios.map(expect.objectContaining),
      );
      expect(comp.usuariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avaliacao: IAvaliacao = { id: 456 };
      const avaliado: IEspecialista = { id: 12274 };
      avaliacao.avaliado = avaliado;
      const avaliador: IUsuario = { id: 10010 };
      avaliacao.avaliador = avaliador;

      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      expect(comp.especialistasSharedCollection).toContain(avaliado);
      expect(comp.usuariosSharedCollection).toContain(avaliador);
      expect(comp.avaliacao).toEqual(avaliacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoFormService, 'getAvaliacao').mockReturnValue(avaliacao);
      jest.spyOn(avaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacao }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoFormService.getAvaliacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avaliacaoService.update).toHaveBeenCalledWith(expect.objectContaining(avaliacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoFormService, 'getAvaliacao').mockReturnValue({ id: null });
      jest.spyOn(avaliacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacao }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoFormService.getAvaliacao).toHaveBeenCalled();
      expect(avaliacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avaliacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
