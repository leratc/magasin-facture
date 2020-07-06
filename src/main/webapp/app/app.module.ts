import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MagasinFactureSharedModule } from 'app/shared/shared.module';
import { MagasinFactureCoreModule } from 'app/core/core.module';
import { MagasinFactureAppRoutingModule } from './app-routing.module';
import { MagasinFactureHomeModule } from './home/home.module';
import { MagasinFactureEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MagasinFactureSharedModule,
    MagasinFactureCoreModule,
    MagasinFactureHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MagasinFactureEntityModule,
    MagasinFactureAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class MagasinFactureAppModule {}
