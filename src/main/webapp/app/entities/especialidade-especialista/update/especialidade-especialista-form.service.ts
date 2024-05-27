import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEspecialidadeEspecialista, NewEspecialidadeEspecialista } from '../especialidade-especialista.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEspecialidadeEspecialista for edit and NewEspecialidadeEspecialistaFormGroupInput for create.
 */
type EspecialidadeEspecialistaFormGroupInput = IEspecialidadeEspecialista | PartialWithRequiredKeyOf<NewEspecialidadeEspecialista>;

type EspecialidadeEspecialistaFormDefaults = Pick<NewEspecialidadeEspecialista, 'id'>;

type EspecialidadeEspecialistaFormGroupContent = {
  id: FormControl<IEspecialidadeEspecialista['id'] | NewEspecialidadeEspecialista['id']>;
  especialidade: FormControl<IEspecialidadeEspecialista['especialidade']>;
  especialista: FormControl<IEspecialidadeEspecialista['especialista']>;
};

export type EspecialidadeEspecialistaFormGroup = FormGroup<EspecialidadeEspecialistaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EspecialidadeEspecialistaFormService {
  createEspecialidadeEspecialistaFormGroup(
    especialidadeEspecialista: EspecialidadeEspecialistaFormGroupInput = { id: null },
  ): EspecialidadeEspecialistaFormGroup {
    const especialidadeEspecialistaRawValue = {
      ...this.getFormDefaults(),
      ...especialidadeEspecialista,
    };
    return new FormGroup<EspecialidadeEspecialistaFormGroupContent>({
      id: new FormControl(
        { value: especialidadeEspecialistaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      especialidade: new FormControl(especialidadeEspecialistaRawValue.especialidade),
      especialista: new FormControl(especialidadeEspecialistaRawValue.especialista),
    });
  }

  getEspecialidadeEspecialista(form: EspecialidadeEspecialistaFormGroup): IEspecialidadeEspecialista | NewEspecialidadeEspecialista {
    return form.getRawValue() as IEspecialidadeEspecialista | NewEspecialidadeEspecialista;
  }

  resetForm(form: EspecialidadeEspecialistaFormGroup, especialidadeEspecialista: EspecialidadeEspecialistaFormGroupInput): void {
    const especialidadeEspecialistaRawValue = { ...this.getFormDefaults(), ...especialidadeEspecialista };
    form.reset(
      {
        ...especialidadeEspecialistaRawValue,
        id: { value: especialidadeEspecialistaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EspecialidadeEspecialistaFormDefaults {
    return {
      id: null,
    };
  }
}
