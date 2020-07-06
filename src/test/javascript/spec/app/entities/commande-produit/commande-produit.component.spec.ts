import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { MagasinFactureTestModule } from '../../../test.module';
import { CommandeProduitComponent } from 'app/entities/commande-produit/commande-produit.component';
import { CommandeProduitService } from 'app/entities/commande-produit/commande-produit.service';
import { CommandeProduit } from 'app/shared/model/commande-produit.model';

describe('Component Tests', () => {
  describe('CommandeProduit Management Component', () => {
    let comp: CommandeProduitComponent;
    let fixture: ComponentFixture<CommandeProduitComponent>;
    let service: CommandeProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagasinFactureTestModule],
        declarations: [CommandeProduitComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(CommandeProduitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeProduitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeProduitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeProduit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeProduits && comp.commandeProduits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeProduit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeProduits && comp.commandeProduits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
