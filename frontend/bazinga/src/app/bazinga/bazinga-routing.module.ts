import {NgModule, Output} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {InicioComponent} from "./componentes/inicio/inicio.component";
import {HomeComponent} from "./componentes/home/home.component";
import {JogadoresComponent} from "./componentes/jogadores/jogadores.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {path: '', redirectTo: 'inicio', pathMatch: 'full'},
      {path: 'inicio', component: InicioComponent},
      {path: 'jogadores', component: JogadoresComponent}],

  }
  // { path: "error", component: ErrorPageComponent },
];

@NgModule({
  exports: [RouterModule],
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class LolRoutingModule {
  @Output() error = false;
}
