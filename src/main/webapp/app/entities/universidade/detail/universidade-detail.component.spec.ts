import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UniversidadeDetailComponent } from './universidade-detail.component';

describe('Universidade Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UniversidadeDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: UniversidadeDetailComponent,
              resolve: { universidade: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UniversidadeDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load universidade on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UniversidadeDetailComponent);

      // THEN
      expect(instance.universidade).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
