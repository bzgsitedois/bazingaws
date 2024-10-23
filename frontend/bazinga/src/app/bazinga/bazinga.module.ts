import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HomeComponent} from "./componentes/home/home.component";
import {InicioComponent} from "./componentes/inicio/inicio.component";
import {BazingaRoutingModule} from "./bazinga-routing.module";
import {DefaultNavbarComponent} from "../shared/components/default-menu/default-navbar/default-navbar.component";



@NgModule({
  declarations: [HomeComponent,InicioComponent],
  imports: [
    BazingaRoutingModule,
    CommonModule,
    DefaultNavbarComponent
    ]
})
export class BazingaModule { }
