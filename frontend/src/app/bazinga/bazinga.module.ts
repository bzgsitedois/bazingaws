import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HomeComponent} from "./componentes/home/home.component";
import {InicioComponent} from "./componentes/inicio/inicio.component";
import {BazingaRoutingModule} from "./bazinga-routing.module";
import {DefaultNavbarComponent} from "../shared/components/default-menu/default-navbar/default-navbar.component";
import {FooterComponent} from "../shared/components/footer/footer.component";



@NgModule({
  declarations: [HomeComponent],
    imports: [
        BazingaRoutingModule,
        CommonModule,
        DefaultNavbarComponent,
        FooterComponent,
    ]
})
export class BazingaModule { }
