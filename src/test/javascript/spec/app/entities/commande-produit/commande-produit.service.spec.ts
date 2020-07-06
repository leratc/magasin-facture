import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeProduitService } from 'app/entities/commande-produit/commande-produit.service';
import { ICommandeProduit, CommandeProduit } from 'app/shared/model/commande-produit.model';
import { StatutCommande } from 'app/shared/model/enumerations/statut-commande.model';

describe('Service Tests', () => {
  describe('CommandeProduit Service', () => {
    let injector: TestBed;
    let service: CommandeProduitService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeProduit;
    let expectedResult: ICommandeProduit | ICommandeProduit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeProduitService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommandeProduit(0, currentDate, StatutCommande.TERMINEE, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommandeProduit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.create(new CommandeProduit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommandeProduit', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            statut: 'BBBBBB',
            code: 'BBBBBB',
            factureId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommandeProduit', () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: currentDate.format(DATE_TIME_FORMAT),
            statut: 'BBBBBB',
            code: 'BBBBBB',
            factureId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommandeProduit', () => {
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
