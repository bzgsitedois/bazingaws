import {NgModule, Output} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {InicioComponent} from "./componentes/inicio/inicio.component";
import {HomeComponent} from "./componentes/home/home.component";
import {LoginComponent} from "./componentes/login/login.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {path: '', redirectTo: 'inicio', pathMatch: 'full'},
      {path: 'inicio', component: InicioComponent},
      {
        path: 'jogadores',
        loadChildren: () => import('./componentes/jogadores/jogador.routes').then((r) => r.ROUTES)
      },
      {
        path: 'produtos',
        loadChildren: () => import('./componentes/produtos/produtos.routes').then((r) => r.ROUTES)
      },

      {
        path: 'times',
        loadChildren: () => import('./componentes/times/times.routes').then((r) => r.ROUTES)
      }


    ],

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
