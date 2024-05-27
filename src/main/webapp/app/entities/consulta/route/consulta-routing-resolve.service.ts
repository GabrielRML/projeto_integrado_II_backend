import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConsulta } from '../consulta.model';
import { ConsultaService } from '../service/consulta.service';

export const consultaResolve = (route: ActivatedRouteSnapshot): Observable<null | IConsulta> => {
  const id = route.params['id'];
  if (id) {
    return inject(ConsultaService)
      .find(id)
      .pipe(
        mergeMap((consulta: HttpResponse<IConsulta>) => {
          if (consulta.body) {
            return of(consulta.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default consultaResolve;
