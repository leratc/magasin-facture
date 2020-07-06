import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { FactureDetailComponent } from 'app/entities/facture/facture-detail.component';
import { Facture } from 'app/shared/model/facture.model';

describe('Component Tests', () => {
  describe('Facture Management Detail Component', () => {
    let comp: FactureDetailComponent;
    let fixture: ComponentFixture<FactureDetailComponent>;
    const route = ({ data: of({ facture: new Facture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [FactureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FactureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FactureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
