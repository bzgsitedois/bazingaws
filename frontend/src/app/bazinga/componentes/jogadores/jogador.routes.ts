import {Routes} from '@angular/router';
import {HomeComponent} from '../home/home.component';
import {JogadorListComponent} from './jogador-list/jogador-list.component';
import {JogadorDetailsComponent} from './jogador-details/jogador-details.component';

export const ROUTES: Routes = [

      {
        path: '',
        pathMatch: 'full',
        // canActivate: [authGuard],
        component: JogadorListComponent
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
      {
        path: ':id',
        // canActivate: [authGuard],
        component: JogadorDetailsComponent
      }
    ]

