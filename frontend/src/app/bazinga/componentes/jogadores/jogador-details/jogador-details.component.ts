import { Component } from '@angular/core';
import {MatTooltip} from '@angular/material/tooltip';

@Component({
  selector: 'app-jogador-details',
  standalone: true,
  imports: [
    MatTooltip
  ],
  templateUrl: './jogador-details.component.html',
  styleUrl: './jogador-details.component.scss'
})
export class JogadorDetailsComponent {

}
