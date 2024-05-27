import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEspecialista } from '../especialista.model';
import { EspecialistaService } from '../service/especialista.service';

export const especialistaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEspecialista> => {
  const id = route.params['id'];
  if (id) {
    return inject(EspecialistaService)
      .find(id)
      .pipe(
        mergeMap((especialista: HttpResponse<IEspecialista>) => {
          if (especialista.body) {
            return of(especialista.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default especialistaResolve;
