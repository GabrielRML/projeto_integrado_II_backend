import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';
import { AvaliacaoService } from 'app/entities/avaliacao/service/avaliacao.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';
import { StatusConsulta } from 'app/entities/enumerations/status-consulta.model';
import { ConsultaService } from '../service/consulta.service';
import { IConsulta } from '../consulta.model';
import { ConsultaFormService, ConsultaFormGroup } from './consulta-form.service';

@Component({
  standalone: true,
  selector: 'app-consulta-update',
  templateUrl: './consulta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ConsultaUpdateComponent implements OnInit {
  isSaving = false;
  consulta: IConsulta | null = null;
  statusConsultaValues = Object.keys(StatusConsulta);

  avaliacaosCollection: IAvaliacao[] = [];
  especialistasSharedCollection: IEspecialista[] = [];
  usuariosSharedCollection: IUsuario[] = [];

  editForm: ConsultaFormGroup = this.consultaFormService.createConsultaFormGroup();

  constructor(
    protected consultaService: ConsultaService,
    protected consultaFormService: ConsultaFormService,
    protected avaliacaoService: AvaliacaoService,
    protected especialistaService: EspecialistaService,
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareAvaliacao = (o1: IAvaliacao | null, o2: IAvaliacao | null): boolean => this.avaliacaoService.compareAvaliacao(o1, o2);

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  compareUsuario = (o1: IUsuario | null, o2: IUsuario | null): boolean => this.usuarioService.compareUsuario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consulta }) => {
      this.consulta = consulta;
      if (consulta) {
        this.updateForm(consulta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consulta = this.consultaFormService.getConsulta(this.editForm);
    if (consulta.id !== null) {
      this.subscribeToSaveResponse(this.consultaService.update(consulta));
    } else {
      this.subscribeToSaveResponse(this.consultaService.create(consulta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsulta>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(consulta: IConsulta): void {
    this.consulta = consulta;
    this.consultaFormService.resetForm(this.editForm, consulta);

    this.avaliacaosCollection = this.avaliacaoService.addAvaliacaoToCollectionIfMissing<IAvaliacao>(
      this.avaliacaosCollection,
      consulta.avaliacao,
    );
    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      consulta.prestador,
    );
    this.usuariosSharedCollection = this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(
      this.usuariosSharedCollection,
      consulta.cliente,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.avaliacaoService
      .query({ 'consultaId.specified': 'false' })
      .pipe(map((res: HttpResponse<IAvaliacao[]>) => res.body ?? []))
      .pipe(
        map((avaliacaos: IAvaliacao[]) =>
          this.avaliacaoService.addAvaliacaoToCollectionIfMissing<IAvaliacao>(avaliacaos, this.consulta?.avaliacao),
        ),
      )
      .subscribe((avaliacaos: IAvaliacao[]) => (this.avaliacaosCollection = avaliacaos));

    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(especialistas, this.consulta?.prestador),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));

    this.usuarioService
      .query()
      .pipe(map((res: HttpResponse<IUsuario[]>) => res.body ?? []))
      .pipe(map((usuarios: IUsuario[]) => this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(usuarios, this.consulta?.cliente)))
      .subscribe((usuarios: IUsuario[]) => (this.usuariosSharedCollection = usuarios));
  }
}
