import {Component, inject, input} from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {ControlContainer, FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-input-text',
  standalone: true,
  imports: [
    InputTextModule,
    ReactiveFormsModule,

  ],viewProviders: [
    {
      provide: ControlContainer,
      useFactory: () => inject(ControlContainer,{skipSelf:true})
    }],
  templateUrl: './input-text.component.html',
  styleUrl: './input-text.component.scss'
})
export class InputTextComponent {
  id = input('')
  label = input('')
  formaControlName = input('')
  placeholder = input('')
  variant = input<'outlined' | 'filled'>('outlined')

}
