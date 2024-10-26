import {AfterViewInit, Component, CUSTOM_ELEMENTS_SCHEMA, OnInit} from '@angular/core';
import {FooterComponent} from "../../../shared/components/footer/footer.component";
import {NgClass} from "@angular/common";
import Swiper from "swiper";

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss',
  standalone: true,
  imports: [
    FooterComponent,
    NgClass
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})


export class InicioComponent implements AfterViewInit {
  maskApplied = false;
  filterApplied = false;
  ngAfterViewInit() {
    new Swiper('.slider-mask', {
      autoplay: {
        delay: 3000,
      },
      loop: true,
    });

    // Aplicando a máscara após um tempo
    setTimeout(() => {
      this.maskApplied = true;
      this.filterApplied = true;
    }, 3000);
  }
}
