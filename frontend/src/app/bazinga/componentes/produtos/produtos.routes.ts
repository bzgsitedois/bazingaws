import {Routes} from '@angular/router';
import {HomeComponent} from '../home/home.component';
import {ProdutosListComponent} from './produtos-list/produtos-list.component';

export const ROUTES: Routes = [

      {
        path: '',
        pathMatch: 'full',
        // canActivate: [authGuard],
        component: ProdutosListComponent
      },
      // {
      //   path: 'form',
      //   canActivate: [authGuard],
      //   component: ConsultasAnterioresFormComponent
      // },
      // {path: 'form/:id',
      //   pathMatch: 'full',
      //   canActivate: [authGuard],
      //   component: ConsultasAnterioresFormComponent},
      // {
      //   path: ':id',
      //   // canActivate: [authGuard],
      //   component: JogadorDetailsComponent
      // }
    ]

