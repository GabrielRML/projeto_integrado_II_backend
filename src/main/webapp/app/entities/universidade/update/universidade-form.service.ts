import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUniversidade, NewUniversidade } from '../universidade.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUniversidade for edit and NewUniversidadeFormGroupInput for create.
 */
type UniversidadeFormGroupInput = IUniversidade | PartialWithRequiredKeyOf<NewUniversidade>;

type UniversidadeFormDefaults = Pick<NewUniversidade, 'id'>;

type UniversidadeFormGroupContent = {
  id: FormControl<IUniversidade['id'] | NewUniversidade['id']>;
  cnpj: FormControl<IUniversidade['cnpj']>;
  name: FormControl<IUniversidade['name']>;
  cep: FormControl<IUniversidade['cep']>;
};

export type UniversidadeFormGroup = FormGroup<UniversidadeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UniversidadeFormService {
  createUniversidadeFormGroup(universidade: UniversidadeFormGroupInput = { id: null }): UniversidadeFormGroup {
    const universidadeRawValue = {
      ...this.getFormDefaults(),
      ...universidade,
    };
    return new FormGroup<UniversidadeFormGroupContent>({
      id: new FormControl(
        { value: universidadeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cnpj: new FormControl(universidadeRawValue.cnpj),
      name: new FormControl(universidadeRawValue.name),
      cep: new FormControl(universidadeRawValue.cep),
    });
  }

  getUniversidade(form: UniversidadeFormGroup): IUniversidade | NewUniversidade {
    return form.getRawValue() as IUniversidade | NewUniversidade;
  }

  resetForm(form: UniversidadeFormGroup, universidade: UniversidadeFormGroupInput): void {
    const universidadeRawValue = { ...this.getFormDefaults(), ...universidade };
    form.reset(
      {
        ...universidadeRawValue,
        id: { value: universidadeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UniversidadeFormDefaults {
    return {
      id: null,
    };
  }
}
