import { Component , CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {FooterComponent} from "../../../shared/components/footer/footer.component";

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.scss',
  standalone: true,
  imports:[
    FooterComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})


export class InicioComponent {

}
