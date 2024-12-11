import {Component, OnInit} from '@angular/core';
import {SplitterModule} from 'primeng/splitter';
import {Classes} from '../../../../entity/enum/classes';
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {TimeService} from '../../../../service/time/time.service';
import {ActivatedRoute, Router} from '@angular/router';
import {JogadorProjectionDTO} from '../../../../entity/jogador';
import {TimeProjectionDTO} from '../../../../entity/time';
import {Jogos} from '../../../../entity/enum/jogos';
import {NgForOf, NgIf} from '@angular/common';
import {PaginatorModule} from 'primeng/paginator';
import {MatTooltip} from '@angular/material/tooltip';
import {Button} from 'primeng/button';
import {TokenService} from '../../../../service/token/token.service';

@Component({
  selector: 'app-times-details',
  standalone: true,
  imports: [
    SplitterModule,
    NgForOf,
    PaginatorModule,
    MatTooltip,
    Button,
    NgIf
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
  jogadores: any[] = [];
  totalRecords = 0;
  rows = 3;
  first = 0;

  constructor(private tokenService: TokenService , private jogadorService: JogadorService , private timeService: TimeService , private router: Router , private activatedRoute: ActivatedRoute) {}

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
          this.carregarJogadores(0, this.rows);
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
  carregarJogadores(page: number, size: number) {
    const filtro = {
      timeNome: this.nome
    };

    this.jogadorService.listarJogadores(page, size , filtro).subscribe((response) => {
      this.jogadores = response.data;
      this.totalRecords = response.meta.totalElements;
    });
  }

  onPageChange(event: any) {
    const page = event.page;
    this.rows = event.rows;
    this.carregarJogadores(page, this.rows);
  }

  getUsuarioLogado(info:string){
    return this.tokenService.getTokenAttribute(info)
  }


  expulsarJogador(id:any){
    console.log(id)
  }

  promoverJogador(id:any){
    console.log(id)
  }

  rebaixarJogador(id:any){
    console.log(id)
  }
}
