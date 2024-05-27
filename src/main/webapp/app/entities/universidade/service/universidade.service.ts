import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUniversidade, NewUniversidade } from '../universidade.model';

export type PartialUpdateUniversidade = Partial<IUniversidade> & Pick<IUniversidade, 'id'>;

export type EntityResponseType = HttpResponse<IUniversidade>;
export type EntityArrayResponseType = HttpResponse<IUniversidade[]>;

@Injectable({ providedIn: 'root' })
export class UniversidadeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/universidades');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(universidade: NewUniversidade): Observable<EntityResponseType> {
    return this.http.post<IUniversidade>(this.resourceUrl, universidade, { observe: 'response' });
  }

  update(universidade: IUniversidade): Observable<EntityResponseType> {
    return this.http.put<IUniversidade>(`${this.resourceUrl}/${this.getUniversidadeIdentifier(universidade)}`, universidade, {
      observe: 'response',
    });
  }

  partialUpdate(universidade: PartialUpdateUniversidade): Observable<EntityResponseType> {
    return this.http.patch<IUniversidade>(`${this.resourceUrl}/${this.getUniversidadeIdentifier(universidade)}`, universidade, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniversidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniversidade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUniversidadeIdentifier(universidade: Pick<IUniversidade, 'id'>): number {
    return universidade.id;
  }

  compareUniversidade(o1: Pick<IUniversidade, 'id'> | null, o2: Pick<IUniversidade, 'id'> | null): boolean {
    return o1 && o2 ? this.getUniversidadeIdentifier(o1) === this.getUniversidadeIdentifier(o2) : o1 === o2;
  }

  addUniversidadeToCollectionIfMissing<Type extends Pick<IUniversidade, 'id'>>(
    universidadeCollection: Type[],
    ...universidadesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const universidades: Type[] = universidadesToCheck.filter(isPresent);
    if (universidades.length > 0) {
      const universidadeCollectionIdentifiers = universidadeCollection.map(
        universidadeItem => this.getUniversidadeIdentifier(universidadeItem)!,
      );
      const universidadesToAdd = universidades.filter(universidadeItem => {
        const universidadeIdentifier = this.getUniversidadeIdentifier(universidadeItem);
        if (universidadeCollectionIdentifiers.includes(universidadeIdentifier)) {
          return false;
        }
        universidadeCollectionIdentifiers.push(universidadeIdentifier);
        return true;
      });
      return [...universidadesToAdd, ...universidadeCollection];
    }
    return universidadeCollection;
  }
}
