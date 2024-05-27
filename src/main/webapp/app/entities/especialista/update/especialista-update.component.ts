import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IEstado } from 'app/entities/estado/estado.model';
import { EstadoService } from 'app/entities/estado/service/estado.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IUniversidade } from 'app/entities/universidade/universidade.model';
import { UniversidadeService } from 'app/entities/universidade/service/universidade.service';
import { SpecialistType } from 'app/entities/enumerations/specialist-type.model';
import { EspecialistaService } from '../service/especialista.service';
import { IEspecialista } from '../especialista.model';
import { EspecialistaFormService, EspecialistaFormGroup } from './especialista-form.service';

@Component({
  standalone: true,
  selector: 'app-especialista-update',
  templateUrl: './especialista-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EspecialistaUpdateComponent implements OnInit {
  isSaving = false;
  especialista: IEspecialista | null = null;
  specialistTypeValues = Object.keys(SpecialistType);

  usersSharedCollection: IUser[] = [];
  especialistasSharedCollection: IEspecialista[] = [];
  estadosSharedCollection: IEstado[] = [];
  cidadesSharedCollection: ICidade[] = [];
  universidadesSharedCollection: IUniversidade[] = [];

  editForm: EspecialistaFormGroup = this.especialistaFormService.createEspecialistaFormGroup();

  constructor(
    protected especialistaService: EspecialistaService,
    protected especialistaFormService: EspecialistaFormService,
    protected userService: UserService,
    protected estadoService: EstadoService,
    protected cidadeService: CidadeService,
    protected universidadeService: UniversidadeService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  compareEstado = (o1: IEstado | null, o2: IEstado | null): boolean => this.estadoService.compareEstado(o1, o2);

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  compareUniversidade = (o1: IUniversidade | null, o2: IUniversidade | null): boolean =>
    this.universidadeService.compareUniversidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ especialista }) => {
      this.especialista = especialista;
      if (especialista) {
        this.updateForm(especialista);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const especialista = this.especialistaFormService.getEspecialista(this.editForm);
    if (especialista.id !== null) {
      this.subscribeToSaveResponse(this.especialistaService.update(especialista));
    } else {
      this.subscribeToSaveResponse(this.especialistaService.create(especialista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspecialista>>): void {
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

  protected updateForm(especialista: IEspecialista): void {
    this.especialista = especialista;
    this.especialistaFormService.resetForm(this.editForm, especialista);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      especialista.internalUser,
    );
    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      especialista.supervisorId,
    );
    this.estadosSharedCollection = this.estadoService.addEstadoToCollectionIfMissing<IEstado>(
      this.estadosSharedCollection,
      especialista.estado,
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      especialista.cidade,
    );
    this.universidadesSharedCollection = this.universidadeService.addUniversidadeToCollectionIfMissing<IUniversidade>(
      this.universidadesSharedCollection,
      especialista.universidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.especialista?.internalUser)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(especialistas, this.especialista?.supervisorId),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));

    this.estadoService
      .query()
      .pipe(map((res: HttpResponse<IEstado[]>) => res.body ?? []))
      .pipe(map((estados: IEstado[]) => this.estadoService.addEstadoToCollectionIfMissing<IEstado>(estados, this.especialista?.estado)))
      .subscribe((estados: IEstado[]) => (this.estadosSharedCollection = estados));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.especialista?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));

    this.universidadeService
      .query()
      .pipe(map((res: HttpResponse<IUniversidade[]>) => res.body ?? []))
      .pipe(
        map((universidades: IUniversidade[]) =>
          this.universidadeService.addUniversidadeToCollectionIfMissing<IUniversidade>(universidades, this.especialista?.universidade),
        ),
      )
      .subscribe((universidades: IUniversidade[]) => (this.universidadesSharedCollection = universidades));
  }
}
