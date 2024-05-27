import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { UniversidadeComponent } from './list/universidade.component';
import { UniversidadeDetailComponent } from './detail/universidade-detail.component';
import { UniversidadeUpdateComponent } from './update/universidade-update.component';
import UniversidadeResolve from './route/universidade-routing-resolve.service';

const universidadeRoute: Routes = [
  {
    path: '',
    component: UniversidadeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UniversidadeDetailComponent,
    resolve: {
      universidade: UniversidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UniversidadeUpdateComponent,
    resolve: {
      universidade: UniversidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UniversidadeUpdateComponent,
    resolve: {
      universidade: UniversidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default universidadeRoute;
