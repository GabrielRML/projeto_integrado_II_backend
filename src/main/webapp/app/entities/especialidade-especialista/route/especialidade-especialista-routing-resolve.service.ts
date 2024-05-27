import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';
import { EspecialidadeEspecialistaService } from '../service/especialidade-especialista.service';

export const especialidadeEspecialistaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEspecialidadeEspecialista> => {
  const id = route.params['id'];
  if (id) {
    return inject(EspecialidadeEspecialistaService)
      .find(id)
      .pipe(
        mergeMap((especialidadeEspecialista: HttpResponse<IEspecialidadeEspecialista>) => {
          if (especialidadeEspecialista.body) {
            return of(especialidadeEspecialista.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default especialidadeEspecialistaResolve;
