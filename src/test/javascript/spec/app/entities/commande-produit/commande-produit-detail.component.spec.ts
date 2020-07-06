import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagasinFactureTestModule } from '../../../test.module';
import { CommandeProduitDetailComponent } from 'app/entities/commande-produit/commande-produit-detail.component';
import { CommandeProduit } from 'app/shared/model/commande-produit.model';

describe('Component Tests', () => {
  describe('CommandeProduit Management Detail Component', () => {
    let comp: CommandeProduitDetailComponent;
    let fixture: ComponentFixture<CommandeProduitDetailComponent>;
    const route = ({ data: of({ commandeProduit: new CommandeProduit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CommandeProduitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommandeProduitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeProduitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeProduit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeProduit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
