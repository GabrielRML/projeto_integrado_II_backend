import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEspecialista, NewEspecialista } from '../especialista.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEspecialista for edit and NewEspecialistaFormGroupInput for create.
 */
type EspecialistaFormGroupInput = IEspecialista | PartialWithRequiredKeyOf<NewEspecialista>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEspecialista | NewEspecialista> = Omit<T, 'birthDate'> & {
  birthDate?: string | null;
};

type EspecialistaFormRawValue = FormValueOf<IEspecialista>;

type NewEspecialistaFormRawValue = FormValueOf<NewEspecialista>;

type EspecialistaFormDefaults = Pick<NewEspecialista, 'id' | 'birthDate'>;

type EspecialistaFormGroupContent = {
  id: FormControl<EspecialistaFormRawValue['id'] | NewEspecialista['id']>;
  cpf: FormControl<EspecialistaFormRawValue['cpf']>;
  identification: FormControl<EspecialistaFormRawValue['identification']>;
  birthDate: FormControl<EspecialistaFormRawValue['birthDate']>;
  description: FormControl<EspecialistaFormRawValue['description']>;
  price: FormControl<EspecialistaFormRawValue['price']>;
  timeSession: FormControl<EspecialistaFormRawValue['timeSession']>;
  specialistType: FormControl<EspecialistaFormRawValue['specialistType']>;
  internalUser: FormControl<EspecialistaFormRawValue['internalUser']>;
  estado: FormControl<EspecialistaFormRawValue['estado']>;
  cidade: FormControl<EspecialistaFormRawValue['cidade']>;
  universidade: FormControl<EspecialistaFormRawValue['universidade']>;
  supervisorId: FormControl<EspecialistaFormRawValue['supervisorId']>;
};

export type EspecialistaFormGroup = FormGroup<EspecialistaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EspecialistaFormService {
  createEspecialistaFormGroup(especialista: EspecialistaFormGroupInput = { id: null }): EspecialistaFormGroup {
    const especialistaRawValue = this.convertEspecialistaToEspecialistaRawValue({
      ...this.getFormDefaults(),
      ...especialista,
    });
    return new FormGroup<EspecialistaFormGroupContent>({
      id: new FormControl(
        { value: especialistaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cpf: new FormControl(especialistaRawValue.cpf),
      identification: new FormControl(especialistaRawValue.identification),
      birthDate: new FormControl(especialistaRawValue.birthDate),
      description: new FormControl(especialistaRawValue.description),
      price: new FormControl(especialistaRawValue.price),
      timeSession: new FormControl(especialistaRawValue.timeSession),
      specialistType: new FormControl(especialistaRawValue.specialistType),
      internalUser: new FormControl(especialistaRawValue.internalUser),
      estado: new FormControl(especialistaRawValue.estado),
      cidade: new FormControl(especialistaRawValue.cidade),
      universidade: new FormControl(especialistaRawValue.universidade),
      supervisorId: new FormControl(especialistaRawValue.supervisorId),
    });
  }

  getEspecialista(form: EspecialistaFormGroup): IEspecialista | NewEspecialista {
    return this.convertEspecialistaRawValueToEspecialista(form.getRawValue() as EspecialistaFormRawValue | NewEspecialistaFormRawValue);
  }

  resetForm(form: EspecialistaFormGroup, especialista: EspecialistaFormGroupInput): void {
    const especialistaRawValue = this.convertEspecialistaToEspecialistaRawValue({ ...this.getFormDefaults(), ...especialista });
    form.reset(
      {
        ...especialistaRawValue,
        id: { value: especialistaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EspecialistaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      birthDate: currentTime,
    };
  }

  private convertEspecialistaRawValueToEspecialista(
    rawEspecialista: EspecialistaFormRawValue | NewEspecialistaFormRawValue,
  ): IEspecialista | NewEspecialista {
    return {
      ...rawEspecialista,
      birthDate: dayjs(rawEspecialista.birthDate, DATE_TIME_FORMAT),
    };
  }

  private convertEspecialistaToEspecialistaRawValue(
    especialista: IEspecialista | (Partial<NewEspecialista> & EspecialistaFormDefaults),
  ): EspecialistaFormRawValue | PartialWithRequiredKeyOf<NewEspecialistaFormRawValue> {
    return {
      ...especialista,
      birthDate: especialista.birthDate ? especialista.birthDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
