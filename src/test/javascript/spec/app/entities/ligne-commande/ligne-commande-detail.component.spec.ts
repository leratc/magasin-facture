import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { LigneCommandeDetailComponent } from 'app/entities/ligne-commande/ligne-commande-detail.component';
import { LigneCommande } from 'app/shared/model/ligne-commande.model';

describe('Component Tests', () => {
  describe('LigneCommande Management Detail Component', () => {
    let comp: LigneCommandeDetailComponent;
    let fixture: ComponentFixture<LigneCommandeDetailComponent>;
    const route = ({ data: of({ ligneCommande: new LigneCommande(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [LigneCommandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LigneCommandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LigneCommandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ligneCommande on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ligneCommande).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
