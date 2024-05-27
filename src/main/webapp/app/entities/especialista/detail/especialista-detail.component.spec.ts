import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EspecialistaDetailComponent } from './especialista-detail.component';

describe('Especialista Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspecialistaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EspecialistaDetailComponent,
              resolve: { especialista: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EspecialistaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load especialista on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EspecialistaDetailComponent);

      // THEN
      expect(instance.especialista).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
