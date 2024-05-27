import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConsulta, NewConsulta } from '../consulta.model';

export type PartialUpdateConsulta = Partial<IConsulta> & Pick<IConsulta, 'id'>;

type RestOf<T extends IConsulta | NewConsulta> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestConsulta = RestOf<IConsulta>;

export type NewRestConsulta = RestOf<NewConsulta>;

export type PartialUpdateRestConsulta = RestOf<PartialUpdateConsulta>;

export type EntityResponseType = HttpResponse<IConsulta>;
export type EntityArrayResponseType = HttpResponse<IConsulta[]>;

@Injectable({ providedIn: 'root' })
export class ConsultaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/consultas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(consulta: NewConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consulta);
    return this.http
      .post<RestConsulta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(consulta: IConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consulta);
    return this.http
      .put<RestConsulta>(`${this.resourceUrl}/${this.getConsultaIdentifier(consulta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(consulta: PartialUpdateConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consulta);
    return this.http
      .patch<RestConsulta>(`${this.resourceUrl}/${this.getConsultaIdentifier(consulta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestConsulta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestConsulta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConsultaIdentifier(consulta: Pick<IConsulta, 'id'>): number {
    return consulta.id;
  }

  compareConsulta(o1: Pick<IConsulta, 'id'> | null, o2: Pick<IConsulta, 'id'> | null): boolean {
    return o1 && o2 ? this.getConsultaIdentifier(o1) === this.getConsultaIdentifier(o2) : o1 === o2;
  }

  addConsultaToCollectionIfMissing<Type extends Pick<IConsulta, 'id'>>(
    consultaCollection: Type[],
    ...consultasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const consultas: Type[] = consultasToCheck.filter(isPresent);
    if (consultas.length > 0) {
      const consultaCollectionIdentifiers = consultaCollection.map(consultaItem => this.getConsultaIdentifier(consultaItem)!);
      const consultasToAdd = consultas.filter(consultaItem => {
        const consultaIdentifier = this.getConsultaIdentifier(consultaItem);
        if (consultaCollectionIdentifiers.includes(consultaIdentifier)) {
          return false;
        }
        consultaCollectionIdentifiers.push(consultaIdentifier);
        return true;
      });
      return [...consultasToAdd, ...consultaCollection];
    }
    return consultaCollection;
  }

  protected convertDateFromClient<T extends IConsulta | NewConsulta | PartialUpdateConsulta>(consulta: T): RestOf<T> {
    return {
      ...consulta,
      date: consulta.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restConsulta: RestConsulta): IConsulta {
    return {
      ...restConsulta,
      date: restConsulta.date ? dayjs(restConsulta.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestConsulta>): HttpResponse<IConsulta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestConsulta[]>): HttpResponse<IConsulta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
