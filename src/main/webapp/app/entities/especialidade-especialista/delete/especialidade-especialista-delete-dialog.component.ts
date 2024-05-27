import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEspecialidadeEspecialista } from '../especialidade-especialista.model';
import { EspecialidadeEspecialistaService } from '../service/especialidade-especialista.service';

@Component({
  standalone: true,
  templateUrl: './especialidade-especialista-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EspecialidadeEspecialistaDeleteDialogComponent {
  especialidadeEspecialista?: IEspecialidadeEspecialista;

  constructor(
    protected especialidadeEspecialistaService: EspecialidadeEspecialistaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.especialidadeEspecialistaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
