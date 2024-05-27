import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EspecialidadeEspecialistaDetailComponent } from './especialidade-especialista-detail.component';

describe('EspecialidadeEspecialista Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspecialidadeEspecialistaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EspecialidadeEspecialistaDetailComponent,
              resolve: { especialidadeEspecialista: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EspecialidadeEspecialistaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load especialidadeEspecialista on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EspecialidadeEspecialistaDetailComponent);

      // THEN
      expect(instance.especialidadeEspecialista).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
