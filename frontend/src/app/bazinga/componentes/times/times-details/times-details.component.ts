import {Component, OnInit} from '@angular/core';
import {SplitterModule} from 'primeng/splitter';
import {Classes} from '../../../../entity/enum/classes';
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {TimeService} from '../../../../service/time/time.service';
import {ActivatedRoute, Router} from '@angular/router';
import {JogadorProjectionDTO} from '../../../../entity/jogador';
import {TimeProjectionDTO} from '../../../../entity/time';
import {Jogos} from '../../../../entity/enum/jogos';

@Component({
  selector: 'app-times-details',
  standalone: true,
  imports: [
    SplitterModule
  ],
  templateUrl: './times-details.component.html',
  styleUrl: './times-details.component.scss'
})
export class TimesDetailsComponent implements OnInit{
  id: number | undefined;
  nome: string | undefined;
  descricao:string = 'Sem descrição';
  jogadoresId: number[] | undefined;
  jogos: Jogos[] | undefined;

  constructor(private jogadorService: JogadorService , private timeService: TimeService , private router: Router , private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    let id = idParam ? +idParam : undefined;
    if(id){
      this.timeService.buscar(id).subscribe((time: TimeProjectionDTO | undefined) => {
        if(time){
          this.id = id;
          this.descricao = time.descricao;
          this.nome =time.nome;
          this.jogadoresId = time.jogadoresId;
          this.jogos = time.jogos;
        }
      })}
  }

  onImageError(event: Event, type: string): void {
    const target = event.target as HTMLImageElement;
    if (type === 'time') {
      target.src = 'assets/Fallbacks/time.jpg';
    } else if (type === 'jogador') {
      target.src = 'assets/Fallbacks/avatar.png';
    }
  }

}
