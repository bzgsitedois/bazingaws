import {Component, OnInit} from '@angular/core';
import {MatTooltip} from "@angular/material/tooltip";
import {NgForOf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {JogadorService} from '../../../../service/jogador/jogador.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TimeService} from '../../../../service/time/time.service';

@Component({
  selector: 'app-times-list',
  standalone: true,
    imports: [
        MatTooltip,
        NgForOf,
        PaginatorModule
    ],
  templateUrl: './times-list.component.html',
  styleUrl: './times-list.component.scss'
})
export class TimesListComponent implements OnInit{
  times: any[] = [];
  totalRecords = 0;
  rows = 3;
  first = 0;

  constructor(private timeService: TimeService , private router: Router , private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.carregarTimes(0, this.rows);
  }

  carregarTimes(page: number, size: number) {
    this.timeService.listarTimes(page, size).subscribe((response) => {
      this.times = response.data;
      this.totalRecords = response.meta.totalElements;
    });
  }

  onPageChange(event: any) {
    const page = event.page;
    this.rows = event.rows;
    this.carregarTimes(page, this.rows);
  }

  onImageError(event: Event, type: string): void {
    const target = event.target as HTMLImageElement;
    if (type === 'time') {
      target.src = 'assets/Fallbacks/time.jpg';
    } else if (type === 'jogador') {
      target.src = 'assets/Fallbacks/avatar.png';
    }
  }

  detalhe(id:number) {
    this.router.navigate([id],
      {
        relativeTo: this.activatedRoute
      }
    ).then()
  }
}
