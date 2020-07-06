import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MagasinFactureTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CategorieProduitDeleteDialogComponent } from 'app/entities/categorie-produit/categorie-produit-delete-dialog.component';
import { CategorieProduitService } from 'app/entities/categorie-produit/categorie-produit.service';

describe('Component Tests', () => {
  describe('CategorieProduit Management Delete Component', () => {
    let comp: CategorieProduitDeleteDialogComponent;
    let fixture: ComponentFixture<CategorieProduitDeleteDialogComponent>;
    let service: CategorieProduitService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CategorieProduitDeleteDialogComponent],
      })
        .overrideTemplate(CategorieProduitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieProduitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieProduitService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
