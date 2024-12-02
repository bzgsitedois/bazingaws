import {AfterViewInit, Component, CUSTOM_ELEMENTS_SCHEMA, OnInit} from '@angular/core';
import {FooterComponent} from "../../../shared/components/footer/footer.component";
import {NgClass, NgOptimizedImage} from "@angular/common";
import {CarouselModule} from "primeng/carousel";

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss',
  standalone: true,
  imports: [
    FooterComponent,
    NgClass,
    CarouselModule,
    NgOptimizedImage
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})


export class InicioComponent implements AfterViewInit{
  maskApplied = false;
  filterApplied = false;
  products = [
    { name: 'Camisa Modelo 1', image: 'assets/imagemcamisa2.png' },
    { name: 'Camisa Modelo 2', image: 'assets/imagemcamisa2.png' }
  ];
  ngAfterViewInit() {
    setTimeout(() => {
      this.maskApplied = true;
    }, 3000);
  }

  onCarouselChange() {
    this.maskApplied = false;

    setTimeout(() => {
      this.maskApplied = true;
    }, 3000);
  }




  constructor() {}
}
