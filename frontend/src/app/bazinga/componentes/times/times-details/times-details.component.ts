import {Component, OnInit} from '@angular/core';
import {SplitterModule} from 'primeng/splitter';
import {Classes} from '../../../../entity/enum/classes';
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {TimeService} from '../../../../service/time/time.service';
import {ActivatedRoute, Router} from '@angular/router';
import {JogadorProjectionDTO} from '../../../../entity/jogador';
import {TimeProjectionDTO} from '../../../../entity/time';
import {Jogos} from '../../../../entity/enum/jogos';
import {NgForOf, NgIf, NgStyle} from '@angular/common';
import {PaginatorModule} from 'primeng/paginator';
import {MatTooltip} from '@angular/material/tooltip';
import {Button} from 'primeng/button';
import {TokenService} from '../../../../service/token/token.service';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ToastModule} from 'primeng/toast';

@Component({
  selector: 'app-times-details',
  standalone: true,
  imports: [
    SplitterModule,
    NgForOf,
    PaginatorModule,
    MatTooltip,
    Button,
    NgIf,
    ConfirmDialogModule,
    ToastModule,
    NgStyle
  ],
  providers: [ConfirmationService, MessageService],
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
  liderTimeJogador: boolean | undefined = false;
  timeIdJogador: number | undefined = undefined;
  constructor(private confirmationService: ConfirmationService, private messageService: MessageService, private tokenService: TokenService , private jogadorService: JogadorService , private timeService: TimeService , private router: Router , private activatedRoute: ActivatedRoute) {}

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
    this.jogadorService.buscar(this.getUsuarioLogado('id')).subscribe((jogador: JogadorProjectionDTO|undefined)=> {
      this.liderTimeJogador = jogador?.liderTime;
      this.timeIdJogador = jogador?.time_id;
    })
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


  expulsarJogador(idJogador: number) {
    this.confirmationService.confirm({
      message: 'Você tem certeza que quer expulsar esse jogador?',
      header: 'Expulsar Jogador',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.timeService.removerJogadoresDoTime(idJogador, this.id).subscribe({
          next: (response:string) => {
            this.messageService.add({
              severity: 'info',
              summary: '',
              detail: 'O jogador foi expulso do Time'
            });
            this.carregarJogadores(0, this.rows);
          },
          error: (err) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Erro',
              detail: `Erro ao expulsar jogador: ${err.message}`
            });
          }
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: 'A ação foi cancelada.',
          life: 3000
        });
      }
    });
  }

  promoverJogador(id:any){
    this.confirmationService.confirm({
      message: 'Você tem certeza que quer promover esse jogador?',
      header: 'Promover Jogador',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.jogadorService.novoLider(id).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: '',
              detail: 'O jogador foi promovido a lider'
            });
            this.carregarJogadores(0, this.rows);
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro',
              detail: `Erro ao promover jogador`
            });
          }
        });
        this.carregarJogadores(0, this.rows);
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: 'A ação foi cancelada.',
          life: 3000
        });
      }
    });
  }

  rebaixarJogador(id:any){
    this.confirmationService.confirm({
      message: 'Você tem certeza que quer rebaixar esse jogador?',
      header: 'Rebaixar Jogador',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.jogadorService.exLider(id).subscribe({
          next:() => {
            this.messageService.add({
              severity: 'success',
              summary: '',
              detail: 'O jogador foi rebaixado a membro'
            });
            this.carregarJogadores(0, this.rows);
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro',
              detail: `Erro ao rebaixar jogador`
            });
          }
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: 'A ação foi cancelada.',
          life: 3000
        });
      }
    });
  }

  excluirTime(){
    this.confirmationService.confirm({
      message: 'Você tem certeza que quer excluir esse time?',
      header: 'Excluir Time',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.timeService.deletarTime(this.id).subscribe({
          next:() => {
            this.router.navigate(['bazinga/times'])
            this.messageService.add({
              severity: 'success',
              summary: '',
              detail: 'O time foi excluido'
            });
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro',
              detail: `Erro ao excluir time`
            });
          }
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: 'A ação foi cancelada.',
          life: 3000
        });
      }
    });
  }

  sairTime(){
    this.confirmationService.confirm({
      message: 'Você tem certeza que quer sair deste time?',
      header: 'Sair do Time',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.jogadorService.sairTime().subscribe({
          next:() => {
            this.messageService.add({
              severity: 'success',
              summary: '',
              detail: 'Você saiu do time'
            });
            this.carregarJogadores(0, this.rows);
            window.location.reload()
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro',
              detail: `Erro ao sair do time`
            });
          }
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: 'A ação foi cancelada.',
          life: 3000
        });
      }
    });
  }
}
