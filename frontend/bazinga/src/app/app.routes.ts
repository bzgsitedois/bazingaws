import { Routes } from '@angular/router';
import {environment} from "../environments/environment";

export const APP_ROUTES: Routes = [
  // {path : 'publico' , loadChildren: () => import("./publico/publico.module").then((n) =>n.PublicoModule) },
  {
    path: environment.PREFIX_BASE,
    loadChildren: () => import("./bazinga/bazinga.module").then((m) => m.BazingaModule),
    // canActivate: [AuthGuard, PermissoesGuard, RedirecionarPrimeiroAcessoGuard],
    // data: {permissoes: ['FUNCIONARIO', 'ADMIN']}
  },
];
