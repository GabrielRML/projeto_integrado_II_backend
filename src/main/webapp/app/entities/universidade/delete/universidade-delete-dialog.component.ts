import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUniversidade } from '../universidade.model';
import { UniversidadeService } from '../service/universidade.service';

@Component({
  standalone: true,
  templateUrl: './universidade-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UniversidadeDeleteDialogComponent {
  universidade?: IUniversidade;

  constructor(
    protected universidadeService: UniversidadeService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.universidadeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
