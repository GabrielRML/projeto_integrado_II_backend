import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';
import { AvaliacaoService } from '../service/avaliacao.service';
import { IAvaliacao } from '../avaliacao.model';
import { AvaliacaoFormService, AvaliacaoFormGroup } from './avaliacao-form.service';

@Component({
  standalone: true,
  selector: 'app-avaliacao-update',
  templateUrl: './avaliacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AvaliacaoUpdateComponent implements OnInit {
  isSaving = false;
  avaliacao: IAvaliacao | null = null;

  especialistasSharedCollection: IEspecialista[] = [];
  usuariosSharedCollection: IUsuario[] = [];

  editForm: AvaliacaoFormGroup = this.avaliacaoFormService.createAvaliacaoFormGroup();

  constructor(
    protected avaliacaoService: AvaliacaoService,
    protected avaliacaoFormService: AvaliacaoFormService,
    protected especialistaService: EspecialistaService,
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  compareUsuario = (o1: IUsuario | null, o2: IUsuario | null): boolean => this.usuarioService.compareUsuario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avaliacao }) => {
      this.avaliacao = avaliacao;
      if (avaliacao) {
        this.updateForm(avaliacao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const avaliacao = this.avaliacaoFormService.getAvaliacao(this.editForm);
    if (avaliacao.id !== null) {
      this.subscribeToSaveResponse(this.avaliacaoService.update(avaliacao));
    } else {
      this.subscribeToSaveResponse(this.avaliacaoService.create(avaliacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvaliacao>>): void {
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

  protected updateForm(avaliacao: IAvaliacao): void {
    this.avaliacao = avaliacao;
    this.avaliacaoFormService.resetForm(this.editForm, avaliacao);

    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      avaliacao.avaliado,
    );
    this.usuariosSharedCollection = this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(
      this.usuariosSharedCollection,
      avaliacao.avaliador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(especialistas, this.avaliacao?.avaliado),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));

    this.usuarioService
      .query()
      .pipe(map((res: HttpResponse<IUsuario[]>) => res.body ?? []))
      .pipe(
        map((usuarios: IUsuario[]) => this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(usuarios, this.avaliacao?.avaliador)),
      )
      .subscribe((usuarios: IUsuario[]) => (this.usuariosSharedCollection = usuarios));
  }
}
