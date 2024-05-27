import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConsulta, NewConsulta } from '../consulta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConsulta for edit and NewConsultaFormGroupInput for create.
 */
type ConsultaFormGroupInput = IConsulta | PartialWithRequiredKeyOf<NewConsulta>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IConsulta | NewConsulta> = Omit<T, 'date'> & {
  date?: string | null;
};

type ConsultaFormRawValue = FormValueOf<IConsulta>;

type NewConsultaFormRawValue = FormValueOf<NewConsulta>;

type ConsultaFormDefaults = Pick<NewConsulta, 'id' | 'date'>;

type ConsultaFormGroupContent = {
  id: FormControl<ConsultaFormRawValue['id'] | NewConsulta['id']>;
  date: FormControl<ConsultaFormRawValue['date']>;
  reason: FormControl<ConsultaFormRawValue['reason']>;
  link: FormControl<ConsultaFormRawValue['link']>;
  status: FormControl<ConsultaFormRawValue['status']>;
  avaliacao: FormControl<ConsultaFormRawValue['avaliacao']>;
  prestador: FormControl<ConsultaFormRawValue['prestador']>;
  cliente: FormControl<ConsultaFormRawValue['cliente']>;
};

export type ConsultaFormGroup = FormGroup<ConsultaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConsultaFormService {
  createConsultaFormGroup(consulta: ConsultaFormGroupInput = { id: null }): ConsultaFormGroup {
    const consultaRawValue = this.convertConsultaToConsultaRawValue({
      ...this.getFormDefaults(),
      ...consulta,
    });
    return new FormGroup<ConsultaFormGroupContent>({
      id: new FormControl(
        { value: consultaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      date: new FormControl(consultaRawValue.date),
      reason: new FormControl(consultaRawValue.reason),
      link: new FormControl(consultaRawValue.link),
      status: new FormControl(consultaRawValue.status),
      avaliacao: new FormControl(consultaRawValue.avaliacao),
      prestador: new FormControl(consultaRawValue.prestador),
      cliente: new FormControl(consultaRawValue.cliente),
    });
  }

  getConsulta(form: ConsultaFormGroup): IConsulta | NewConsulta {
    return this.convertConsultaRawValueToConsulta(form.getRawValue() as ConsultaFormRawValue | NewConsultaFormRawValue);
  }

  resetForm(form: ConsultaFormGroup, consulta: ConsultaFormGroupInput): void {
    const consultaRawValue = this.convertConsultaToConsultaRawValue({ ...this.getFormDefaults(), ...consulta });
    form.reset(
      {
        ...consultaRawValue,
        id: { value: consultaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ConsultaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertConsultaRawValueToConsulta(rawConsulta: ConsultaFormRawValue | NewConsultaFormRawValue): IConsulta | NewConsulta {
    return {
      ...rawConsulta,
      date: dayjs(rawConsulta.date, DATE_TIME_FORMAT),
    };
  }

  private convertConsultaToConsultaRawValue(
    consulta: IConsulta | (Partial<NewConsulta> & ConsultaFormDefaults),
  ): ConsultaFormRawValue | PartialWithRequiredKeyOf<NewConsultaFormRawValue> {
    return {
      ...consulta,
      date: consulta.date ? consulta.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
