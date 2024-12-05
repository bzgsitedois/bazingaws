import {NgModule, Output} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {InicioComponent} from "./componentes/inicio/inicio.component";
import {HomeComponent} from "./componentes/home/home.component";
import {TimesComponent} from "./componentes/times/times.component";
import {LoginComponent} from "./componentes/login/login.component";
import {JogadorListComponent} from './componentes/jogadores/jogador-list/jogador-list.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {path: '', redirectTo: 'inicio', pathMatch: 'full'},
      {path: 'inicio', component: InicioComponent},
      {
        path: 'jogadores',
        // canActivate: [authGuard],
        loadChildren: () => import('./componentes/jogadores/jogador.routes').then((r) => r.ROUTES)
      },
      {path: 'times', component: TimesComponent}],

  },
  {
    path: 'login',
    component: LoginComponent,
  }
  // { path: "error", component: ErrorPageComponent },
];

@NgModule({
  exports: [RouterModule],
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class BazingaRoutingModule {
  @Output() error = false;
}
