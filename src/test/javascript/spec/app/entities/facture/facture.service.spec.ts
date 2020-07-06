import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FactureService } from 'app/entities/facture/facture.service';
import { IFacture, Facture } from 'app/shared/model/facture.model';
import { StatutFacture } from 'app/shared/model/enumerations/statut-facture.model';
import { MethodePaiement } from 'app/shared/model/enumerations/methode-paiement.model';

describe('Service Tests', () => {
  describe('Facture Service', () => {
    let injector: TestBed;
    let service: FactureService;
    let httpMock: HttpTestingController;
    let elemDefault: IFacture;
    let expectedResult: IFacture | IFacture[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FactureService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Facture(0, 'AAAAAAA', currentDate, 'AAAAAAA', StatutFacture.PAYE, MethodePaiement.CARTE_CREDIT, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Facture', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT),
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.create(new Facture()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Facture', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            details: 'BBBBBB',
            statut: 'BBBBBB',
            methodePaiement: 'BBBBBB',
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
            montantPaiement: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Facture', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            details: 'BBBBBB',
            statut: 'BBBBBB',
            methodePaiement: 'BBBBBB',
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
            montantPaiement: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Facture', () => {
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
