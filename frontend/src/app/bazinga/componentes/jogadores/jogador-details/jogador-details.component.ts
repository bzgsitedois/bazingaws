import {Component, inject, OnInit} from '@angular/core';
import {MatTooltip} from '@angular/material/tooltip';
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {ActivatedRoute, Router} from '@angular/router';
import {JogadorProjectionDTO} from '../../../../entity/jogador';
import {Classes} from '../../../../entity/enum/classes';
import {TimeService} from '../../../../service/time/time.service';
import {TimeProjectionDTO} from '../../../../entity/time';
import {of} from 'rxjs';

@Component({
  selector: 'app-jogador-details',
  standalone: true,
  imports: [
    MatTooltip
  ],
  templateUrl: './jogador-details.component.html',
  styleUrl: './jogador-details.component.scss'
})
export class JogadorDetailsComponent implements OnInit{
    classe : Classes[] = []
    nome: string = ''
    id: number| undefined = undefined ;
    time_id: number| undefined = undefined ;
    liderTime: boolean = false;
    timeNome: string|undefined = 'Sem Time'

  constructor(private jogadorService: JogadorService , private timeService: TimeService , private router: Router , private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    let id = idParam ? +idParam : undefined;
    if(id){
      this.jogadorService.buscar(id).subscribe((jogador: JogadorProjectionDTO | undefined) => {
            if(jogador){
                      this.id = id;
                      this.classe = jogador.classes;
                      this.nome =jogador.nome;
                      this.liderTime = jogador.liderTime;

                      if(jogador.time_id != null){
                        this.timeService.buscar(jogador.time_id).subscribe((time: TimeProjectionDTO | undefined) =>{
                          this.timeNome = time?.nome
                        })
                      }
            }
      })}
  }


  protected readonly of = of;
}
