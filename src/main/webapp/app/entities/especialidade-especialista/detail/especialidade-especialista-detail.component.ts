import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';

@Component({
  standalone: true,
  selector: 'app-especialidade-especialista-detail',
  templateUrl: './especialidade-especialista-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EspecialidadeEspecialistaDetailComponent {
  @Input() especialidadeEspecialista: IEspecialidadeEspecialista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
