import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ConsultaDetailComponent } from './consulta-detail.component';

describe('Consulta Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ConsultaDetailComponent,
              resolve: { consulta: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ConsultaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load consulta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ConsultaDetailComponent);

      // THEN
      expect(instance.consulta).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
