import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IConsulta } from '../consulta.model';
import { ConsultaService } from '../service/consulta.service';

@Component({
  standalone: true,
  templateUrl: './consulta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ConsultaDeleteDialogComponent {
  consulta?: IConsulta;

  constructor(
    protected consultaService: ConsultaService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.consultaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
