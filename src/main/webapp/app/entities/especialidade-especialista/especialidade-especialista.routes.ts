import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EspecialidadeEspecialistaComponent } from './list/especialidade-especialista.component';
import { EspecialidadeEspecialistaDetailComponent } from './detail/especialidade-especialista-detail.component';
import { EspecialidadeEspecialistaUpdateComponent } from './update/especialidade-especialista-update.component';
import EspecialidadeEspecialistaResolve from './route/especialidade-especialista-routing-resolve.service';

const especialidadeEspecialistaRoute: Routes = [
  {
    path: '',
    component: EspecialidadeEspecialistaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EspecialidadeEspecialistaDetailComponent,
    resolve: {
      especialidadeEspecialista: EspecialidadeEspecialistaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EspecialidadeEspecialistaUpdateComponent,
    resolve: {
      especialidadeEspecialista: EspecialidadeEspecialistaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EspecialidadeEspecialistaUpdateComponent,
    resolve: {
      especialidadeEspecialista: EspecialidadeEspecialistaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default especialidadeEspecialistaRoute;
