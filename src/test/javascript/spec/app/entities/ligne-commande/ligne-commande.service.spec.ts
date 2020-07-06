import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LigneCommandeService } from 'app/entities/ligne-commande/ligne-commande.service';
import { ILigneCommande, LigneCommande } from 'app/shared/model/ligne-commande.model';
import { StatutLigneCommande } from 'app/shared/model/enumerations/statut-ligne-commande.model';

describe('Service Tests', () => {
  describe('LigneCommande Service', () => {
    let injector: TestBed;
    let service: LigneCommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: ILigneCommande;
    let expectedResult: ILigneCommande | ILigneCommande[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LigneCommandeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new LigneCommande(0, 0, 0, StatutLigneCommande.DISPONIBLE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LigneCommande', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LigneCommande()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LigneCommande', () => {
        const returnedFromService = Object.assign(
          {
            quantite: 1,
            prixTotalHT: 1,
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LigneCommande', () => {
        const returnedFromService = Object.assign(
          {
            quantite: 1,
            prixTotalHT: 1,
            statut: 'BBBBBB',
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

      it('should delete a LigneCommande', () => {
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
