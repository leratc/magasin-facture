import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { CategorieProduitUpdateComponent } from 'app/entities/categorie-produit/categorie-produit-update.component';
import { CategorieProduitService } from 'app/entities/categorie-produit/categorie-produit.service';
import { CategorieProduit } from 'app/shared/model/categorie-produit.model';

describe('Component Tests', () => {
  describe('CategorieProduit Management Update Component', () => {
    let comp: CategorieProduitUpdateComponent;
    let fixture: ComponentFixture<CategorieProduitUpdateComponent>;
    let service: CategorieProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CategorieProduitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategorieProduitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieProduitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieProduitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieProduit(123);
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
        const entity = new CategorieProduit();
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
