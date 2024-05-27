import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUniversidade } from '../universidade.model';
import { UniversidadeService } from '../service/universidade.service';

export const universidadeResolve = (route: ActivatedRouteSnapshot): Observable<null | IUniversidade> => {
  const id = route.params['id'];
  if (id) {
    return inject(UniversidadeService)
      .find(id)
      .pipe(
        mergeMap((universidade: HttpResponse<IUniversidade>) => {
          if (universidade.body) {
            return of(universidade.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default universidadeResolve;
