import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {InputTextComponent} from '../../../shared/components/input-text/input-text.component';
import {InputPasswordComponent} from '../../../shared/components/input-password/input-password.component';
import {DropdownModule} from 'primeng/dropdown';
import {MultiSelectModule} from 'primeng/multiselect';
import {NgForOf, NgIf} from '@angular/common';
import {ButtonDirective} from 'primeng/button';
import {ActivatedRoute} from '@angular/router';
import {JogadorService} from '../../../service/jogador/jogador.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextComponent,
    InputPasswordComponent,
    DropdownModule,
    MultiSelectModule,
    NgForOf,
    NgIf,
    ButtonDirective
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  private route: ActivatedRoute = inject(ActivatedRoute);
  private service: JogadorService = inject(JogadorService);



  isRightPanelActive = false;

  togglePanel(isActive: boolean): void {
    this.isRightPanelActive = isActive;
  }

  cadastrar = new FormGroup({
    nome: new FormControl<string|undefined>('',  {nonNullable: true, validators: [Validators.required]}),
    email: new FormControl<string|undefined>('', {nonNullable: true, validators: [Validators.required]}),
    senha: new FormControl<string|undefined>('', {nonNullable: true, validators: [Validators.required]}),
    classesId: new FormControl<number[]|undefined>(undefined, {nonNullable: true}),
  })

  login = new FormGroup({
    email: new FormControl('', [Validators.required]),
    senha: new FormControl('', [Validators.required])
  })


  classes = [
    {label: 'Scout', value: 1},
    {label: 'Heavy', value: 2},
    {label: 'Engineer', value: 3},
    {label: 'Pyro', value: 4}  ,
    {label: 'Demoman', value:5 },
    {label: 'Soldier', value:6 },
    {label: 'Medic', value:7 },
    {label: 'Soldier', value:8 },
    {label: 'Spy', value:9 },
  ];

salvar(){
  const formData = this.cadastrar.getRawValue();

  console.log("Criado com dados formatados:");
  console.log(formData);

  this.service.criarUsuario(formData).subscribe({
    next: (res) => {
      console.log("Preço criado com sucesso:", res);
    },
    error: (err) => {
      console.error("Erro ao criar preço:", err);
    }
  });

}
}
