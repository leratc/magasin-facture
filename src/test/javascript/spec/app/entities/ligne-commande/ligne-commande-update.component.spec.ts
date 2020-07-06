import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { LigneCommandeUpdateComponent } from 'app/entities/ligne-commande/ligne-commande-update.component';
import { LigneCommandeService } from 'app/entities/ligne-commande/ligne-commande.service';
import { LigneCommande } from 'app/shared/model/ligne-commande.model';

describe('Component Tests', () => {
  describe('LigneCommande Management Update Component', () => {
    let comp: LigneCommandeUpdateComponent;
    let fixture: ComponentFixture<LigneCommandeUpdateComponent>;
    let service: LigneCommandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [LigneCommandeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LigneCommandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LigneCommandeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LigneCommandeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LigneCommande(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LigneCommande();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
