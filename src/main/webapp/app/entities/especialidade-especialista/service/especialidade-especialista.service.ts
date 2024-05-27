import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEspecialidadeEspecialista, NewEspecialidadeEspecialista } from '../especialidade-especialista.model';

export type PartialUpdateEspecialidadeEspecialista = Partial<IEspecialidadeEspecialista> & Pick<IEspecialidadeEspecialista, 'id'>;

export type EntityResponseType = HttpResponse<IEspecialidadeEspecialista>;
export type EntityArrayResponseType = HttpResponse<IEspecialidadeEspecialista[]>;

@Injectable({ providedIn: 'root' })
export class EspecialidadeEspecialistaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/especialidade-especialistas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(especialidadeEspecialista: NewEspecialidadeEspecialista): Observable<EntityResponseType> {
    return this.http.post<IEspecialidadeEspecialista>(this.resourceUrl, especialidadeEspecialista, { observe: 'response' });
  }

  update(especialidadeEspecialista: IEspecialidadeEspecialista): Observable<EntityResponseType> {
    return this.http.put<IEspecialidadeEspecialista>(
      `${this.resourceUrl}/${this.getEspecialidadeEspecialistaIdentifier(especialidadeEspecialista)}`,
      especialidadeEspecialista,
      { observe: 'response' },
    );
  }

  partialUpdate(especialidadeEspecialista: PartialUpdateEspecialidadeEspecialista): Observable<EntityResponseType> {
    return this.http.patch<IEspecialidadeEspecialista>(
      `${this.resourceUrl}/${this.getEspecialidadeEspecialistaIdentifier(especialidadeEspecialista)}`,
      especialidadeEspecialista,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEspecialidadeEspecialista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEspecialidadeEspecialista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEspecialidadeEspecialistaIdentifier(especialidadeEspecialista: Pick<IEspecialidadeEspecialista, 'id'>): number {
    return especialidadeEspecialista.id;
  }

  compareEspecialidadeEspecialista(
    o1: Pick<IEspecialidadeEspecialista, 'id'> | null,
    o2: Pick<IEspecialidadeEspecialista, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getEspecialidadeEspecialistaIdentifier(o1) === this.getEspecialidadeEspecialistaIdentifier(o2) : o1 === o2;
  }

  addEspecialidadeEspecialistaToCollectionIfMissing<Type extends Pick<IEspecialidadeEspecialista, 'id'>>(
    especialidadeEspecialistaCollection: Type[],
    ...especialidadeEspecialistasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const especialidadeEspecialistas: Type[] = especialidadeEspecialistasToCheck.filter(isPresent);
    if (especialidadeEspecialistas.length > 0) {
      const especialidadeEspecialistaCollectionIdentifiers = especialidadeEspecialistaCollection.map(
        especialidadeEspecialistaItem => this.getEspecialidadeEspecialistaIdentifier(especialidadeEspecialistaItem)!,
      );
      const especialidadeEspecialistasToAdd = especialidadeEspecialistas.filter(especialidadeEspecialistaItem => {
        const especialidadeEspecialistaIdentifier = this.getEspecialidadeEspecialistaIdentifier(especialidadeEspecialistaItem);
        if (especialidadeEspecialistaCollectionIdentifiers.includes(especialidadeEspecialistaIdentifier)) {
          return false;
        }
        especialidadeEspecialistaCollectionIdentifiers.push(especialidadeEspecialistaIdentifier);
        return true;
      });
      return [...especialidadeEspecialistasToAdd, ...especialidadeEspecialistaCollection];
    }
    return especialidadeEspecialistaCollection;
  }
}
