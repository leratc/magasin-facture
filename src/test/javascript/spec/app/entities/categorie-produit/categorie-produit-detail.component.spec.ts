import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { CategorieProduitDetailComponent } from 'app/entities/categorie-produit/categorie-produit-detail.component';
import { CategorieProduit } from 'app/shared/model/categorie-produit.model';

describe('Component Tests', () => {
  describe('CategorieProduit Management Detail Component', () => {
    let comp: CategorieProduitDetailComponent;
    let fixture: ComponentFixture<CategorieProduitDetailComponent>;
    const route = ({ data: of({ categorieProduit: new CategorieProduit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CategorieProduitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategorieProduitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieProduitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieProduit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieProduit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
