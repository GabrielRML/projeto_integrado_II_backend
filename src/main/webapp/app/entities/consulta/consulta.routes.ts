import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ConsultaComponent } from './list/consulta.component';
import { ConsultaDetailComponent } from './detail/consulta-detail.component';
import { ConsultaUpdateComponent } from './update/consulta-update.component';
import ConsultaResolve from './route/consulta-routing-resolve.service';

const consultaRoute: Routes = [
  {
    path: '',
    component: ConsultaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsultaDetailComponent,
    resolve: {
      consulta: ConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsultaUpdateComponent,
    resolve: {
      consulta: ConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsultaUpdateComponent,
    resolve: {
      consulta: ConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default consultaRoute;
