import {Routes} from '@angular/router';
import {HomeComponent} from '../home/home.component';
import {TimesListComponent} from './times-list/times-list.component';

export const ROUTES: Routes = [

      {
        path: '',
        pathMatch: 'full',
        // canActivate: [authGuard],
        component: TimesListComponent
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

