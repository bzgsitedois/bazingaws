import {Component, inject, input} from '@angular/core';
import {InputTextModule} from 'primeng/inputtext';
import {ControlContainer, ReactiveFormsModule} from '@angular/forms';
import {PasswordModule} from 'primeng/password';

@Component({
  selector: 'app-input-password',
  standalone: true,
  imports: [
    InputTextModule,
    ReactiveFormsModule,
    PasswordModule
  ],viewProviders: [
    {
      provide: ControlContainer,
      useFactory: () => inject(ControlContainer,{skipSelf:true})
    }]
,
    templateUrl: './input-password.component.html',
  styleUrl: './input-password.component.scss'
})
export class InputPasswordComponent {
  formaControlName = input('')
  placeholder = input('')
}
