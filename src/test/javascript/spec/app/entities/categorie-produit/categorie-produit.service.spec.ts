import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CategorieProduitService } from 'app/entities/categorie-produit/categorie-produit.service';
import { ICategorieProduit, CategorieProduit } from 'app/shared/model/categorie-produit.model';

describe('Service Tests', () => {
  describe('CategorieProduit Service', () => {
    let injector: TestBed;
    let service: CategorieProduitService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorieProduit;
    let expectedResult: ICategorieProduit | ICategorieProduit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategorieProduitService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CategorieProduit(0, 'AAAAAAA', 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CategorieProduit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorieProduit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorieProduit', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            description: 'BBBBBB',
            tauxTaxe: 1,
            importe: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorieProduit', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            description: 'BBBBBB',
            tauxTaxe: 1,
            importe: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CategorieProduit', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
