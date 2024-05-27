import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUniversidade } from '../universidade.model';
import { UniversidadeService } from '../service/universidade.service';
import { UniversidadeFormService, UniversidadeFormGroup } from './universidade-form.service';

@Component({
  standalone: true,
  selector: 'app-universidade-update',
  templateUrl: './universidade-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UniversidadeUpdateComponent implements OnInit {
  isSaving = false;
  universidade: IUniversidade | null = null;

  editForm: UniversidadeFormGroup = this.universidadeFormService.createUniversidadeFormGroup();

  constructor(
    protected universidadeService: UniversidadeService,
    protected universidadeFormService: UniversidadeFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ universidade }) => {
      this.universidade = universidade;
      if (universidade) {
        this.updateForm(universidade);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const universidade = this.universidadeFormService.getUniversidade(this.editForm);
    if (universidade.id !== null) {
      this.subscribeToSaveResponse(this.universidadeService.update(universidade));
    } else {
      this.subscribeToSaveResponse(this.universidadeService.create(universidade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniversidade>>): void {
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

  protected updateForm(universidade: IUniversidade): void {
    this.universidade = universidade;
    this.universidadeFormService.resetForm(this.editForm, universidade);
  }
}
