import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MagasinFactureTestModule } from '../../../test.module';
import { CategorieProduitComponent } from 'app/entities/categorie-produit/categorie-produit.component';
import { CategorieProduitService } from 'app/entities/categorie-produit/categorie-produit.service';
import { CategorieProduit } from 'app/shared/model/categorie-produit.model';

describe('Component Tests', () => {
  describe('CategorieProduit Management Component', () => {
    let comp: CategorieProduitComponent;
    let fixture: ComponentFixture<CategorieProduitComponent>;
    let service: CategorieProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CategorieProduitComponent],
      })
        .overrideTemplate(CategorieProduitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieProduitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieProduitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategorieProduit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categorieProduits && comp.categorieProduits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
