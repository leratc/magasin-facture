import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { CommandeProduitUpdateComponent } from 'app/entities/commande-produit/commande-produit-update.component';
import { CommandeProduitService } from 'app/entities/commande-produit/commande-produit.service';
import { CommandeProduit } from 'app/shared/model/commande-produit.model';

describe('Component Tests', () => {
  describe('CommandeProduit Management Update Component', () => {
    let comp: CommandeProduitUpdateComponent;
    let fixture: ComponentFixture<CommandeProduitUpdateComponent>;
    let service: CommandeProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CommandeProduitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommandeProduitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeProduitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeProduitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeProduit(123);
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
        const entity = new CommandeProduit();
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
