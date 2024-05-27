import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'usuario',
        data: { pageTitle: 'clinicalLinkApp.usuario.home.title' },
        loadChildren: () => import('./usuario/usuario.routes'),
      },
      {
        path: 'especialista',
        data: { pageTitle: 'clinicalLinkApp.especialista.home.title' },
        loadChildren: () => import('./especialista/especialista.routes'),
      },
      {
        path: 'universidade',
        data: { pageTitle: 'clinicalLinkApp.universidade.home.title' },
        loadChildren: () => import('./universidade/universidade.routes'),
      },
      {
        path: 'especialidade',
        data: { pageTitle: 'clinicalLinkApp.especialidade.home.title' },
        loadChildren: () => import('./especialidade/especialidade.routes'),
      },
      {
        path: 'especialidade-especialista',
        data: { pageTitle: 'clinicalLinkApp.especialidadeEspecialista.home.title' },
        loadChildren: () => import('./especialidade-especialista/especialidade-especialista.routes'),
      },
      {
        path: 'avaliacao',
        data: { pageTitle: 'clinicalLinkApp.avaliacao.home.title' },
        loadChildren: () => import('./avaliacao/avaliacao.routes'),
      },
      {
        path: 'consulta',
        data: { pageTitle: 'clinicalLinkApp.consulta.home.title' },
        loadChildren: () => import('./consulta/consulta.routes'),
      },
      {
        path: 'estado',
        data: { pageTitle: 'clinicalLinkApp.estado.home.title' },
        loadChildren: () => import('./estado/estado.routes'),
      },
      {
        path: 'cidade',
        data: { pageTitle: 'clinicalLinkApp.cidade.home.title' },
        loadChildren: () => import('./cidade/cidade.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
