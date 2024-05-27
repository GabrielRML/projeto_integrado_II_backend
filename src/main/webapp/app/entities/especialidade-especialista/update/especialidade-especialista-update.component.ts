import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { EspecialidadeEspecialistaService } from '../service/especialidade-especialista.service';
import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';
import { EspecialidadeEspecialistaFormService, EspecialidadeEspecialistaFormGroup } from './especialidade-especialista-form.service';

@Component({
  standalone: true,
  selector: 'app-especialidade-especialista-update',
  templateUrl: './especialidade-especialista-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EspecialidadeEspecialistaUpdateComponent implements OnInit {
  isSaving = false;
  especialidadeEspecialista: IEspecialidadeEspecialista | null = null;

  especialidadesSharedCollection: IEspecialidade[] = [];
  especialistasSharedCollection: IEspecialista[] = [];

  editForm: EspecialidadeEspecialistaFormGroup = this.especialidadeEspecialistaFormService.createEspecialidadeEspecialistaFormGroup();

  constructor(
    protected especialidadeEspecialistaService: EspecialidadeEspecialistaService,
    protected especialidadeEspecialistaFormService: EspecialidadeEspecialistaFormService,
    protected especialidadeService: EspecialidadeService,
    protected especialistaService: EspecialistaService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEspecialidade = (o1: IEspecialidade | null, o2: IEspecialidade | null): boolean =>
    this.especialidadeService.compareEspecialidade(o1, o2);

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ especialidadeEspecialista }) => {
      this.especialidadeEspecialista = especialidadeEspecialista;
      if (especialidadeEspecialista) {
        this.updateForm(especialidadeEspecialista);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const especialidadeEspecialista = this.especialidadeEspecialistaFormService.getEspecialidadeEspecialista(this.editForm);
    if (especialidadeEspecialista.id !== null) {
      this.subscribeToSaveResponse(this.especialidadeEspecialistaService.update(especialidadeEspecialista));
    } else {
      this.subscribeToSaveResponse(this.especialidadeEspecialistaService.create(especialidadeEspecialista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspecialidadeEspecialista>>): void {
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

  protected updateForm(especialidadeEspecialista: IEspecialidadeEspecialista): void {
    this.especialidadeEspecialista = especialidadeEspecialista;
    this.especialidadeEspecialistaFormService.resetForm(this.editForm, especialidadeEspecialista);

    this.especialidadesSharedCollection = this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
      this.especialidadesSharedCollection,
      especialidadeEspecialista.especialidade,
    );
    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      especialidadeEspecialista.especialista,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.especialidadeService
      .query()
      .pipe(map((res: HttpResponse<IEspecialidade[]>) => res.body ?? []))
      .pipe(
        map((especialidades: IEspecialidade[]) =>
          this.especialidadeService.addEspecialidadeToCollectionIfMissing<IEspecialidade>(
            especialidades,
            this.especialidadeEspecialista?.especialidade,
          ),
        ),
      )
      .subscribe((especialidades: IEspecialidade[]) => (this.especialidadesSharedCollection = especialidades));

    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
            especialistas,
            this.especialidadeEspecialista?.especialista,
          ),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));
  }
}
