import { Component, OnInit } from '@angular/core';
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {CardModule} from 'primeng/card';
import {NgForOf} from '@angular/common';
import {Button} from 'primeng/button';
import {PaginatorModule} from 'primeng/paginator';

@Component({
  selector: 'app-jogador-list',
  templateUrl: './jogador-list.component.html',
  styleUrls: ['./jogador-list.component.scss'],
  imports: [
    CardModule,
    NgForOf,
    Button,
    PaginatorModule
  ],
  standalone: true
})
export class JogadorListComponent implements OnInit {
  jogadores: any[] = [];
  totalRecords = 0;
  rows = 10;
  first = 0;

  constructor(private jogadorService: JogadorService) {}

  ngOnInit() {
    this.carregarJogadores(0, this.rows);
  }

  carregarJogadores(page: number, size: number) {
    this.jogadorService.listarJogadores(page, size).subscribe((response) => {
      this.jogadores = response.data;
      this.totalRecords = response.meta.totalElements;
    });
  }

  onPageChange(event: any) {
    const page = event.page;
    this.rows = event.rows;
    this.carregarJogadores(page, this.rows);
  }
}
