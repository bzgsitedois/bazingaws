import { Routes } from '@angular/router';
import {environment} from "../environments/environment-dev";

export const APP_ROUTES: Routes = [
  {
    path: '',
    redirectTo: '/bazinga',
    pathMatch: 'full' // Certifique-se de usar pathMatch: 'full' para redirecionar quando o caminho for exatamente vazio.
  },
  {
    path: environment.PREFIX_BASE,
    loadChildren: () => import("./bazinga/bazinga.module").then((m) => m.BazingaModule),
    // canActivate: [AuthGuard, PermissoesGuard, RedirecionarPrimeiroAcessoGuard],
    // data: {permissoes: ['JOGADOR', 'ADMIN']}
  },
];
