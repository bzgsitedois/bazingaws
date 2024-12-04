import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {InputTextComponent} from '../../../shared/components/input-text/input-text.component';
import {InputPasswordComponent} from '../../../shared/components/input-password/input-password.component';
import {DropdownModule} from 'primeng/dropdown';
import {MultiSelectModule} from 'primeng/multiselect';
import {NgForOf, NgIf} from '@angular/common';
import {ButtonDirective} from 'primeng/button';
import {ActivatedRoute, Router} from '@angular/router';
import {JogadorService} from '../../../service/jogador/jogador.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthService} from '../../../service/auth/auth.service';
import {TokenService} from '../../../service/token/token.service';

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
  private authService: AuthService = inject(AuthService);
  private tokenService: TokenService = inject(TokenService);
  private router: Router = inject(Router)
  constructor(private snackBar: MatSnackBar) {
  }

  isRightPanelActive = false;

  togglePanel(isActive: boolean): void {
    this.isRightPanelActive = isActive;
  }

  cadastrar = new FormGroup({
    nome: new FormControl<string | undefined>('', {nonNullable: true, validators: [Validators.required]}),
    email: new FormControl<string | undefined>('', {nonNullable: true, validators: [Validators.required]}),
    senha: new FormControl<string | undefined>('', {nonNullable: true, validators: [Validators.required]}),
    classesId: new FormControl<number[] | undefined>([], {nonNullable: true}),
  })

  login = new FormGroup({
    login: new FormControl<string | undefined>('', {nonNullable: true, validators: [Validators.required]}),
    senha: new FormControl<string | undefined>('', {nonNullable: true, validators: [Validators.required]})
  })


  classes = [
    {label: 'Scout', value: 1},
    {label: 'Heavy', value: 2},
    {label: 'Engineer', value: 3},
    {label: 'Pyro', value: 4},
    {label: 'Demoman', value: 5},
    {label: 'Soldier', value: 6},
    {label: 'Medic', value: 7},
    {label: 'Soldier', value: 8},
    {label: 'Spy', value: 9},
  ];

  salvar() {
    const formData = this.cadastrar.getRawValue();

    this.service.criarUsuario(formData).subscribe({
      next: (res) => {
        const jogador = {
          login: this.cadastrar.get('email')?.value,
          senha: this.cadastrar.get('senha')?.value,
        };

        this.authService.login(jogador).subscribe({
          next: (token: string) => {
            this.tokenService.saveToken(token);
            this.router.navigate(['/']);
            this.snackBar.open('Jogador criado e logado com sucesso!', 'Fechar', {
              duration: 3000,
              panelClass: ['custom-snackbar'],
            });
          },
          error: (err) => {
            console.error('Erro ao realizar login após cadastro:', err);
            this.snackBar.open('Erro ao logar após cadastro!', 'Fechar', {
              duration: 3000,
              panelClass: ['custom-snackbar'],
            });
          },
        });
      },
      error: (err) => {
        console.error('Erro ao criar jogador:', err);
        this.snackBar.open('Erro ao criar jogador!', 'Fechar', {
          duration: 3000,
          panelClass: ['custom-snackbar'],
        });
      },
    });
  }


  logar() {
    const formData = this.login.getRawValue();

    this.authService.login(formData).subscribe({
      next: (token: string) => {
        this.tokenService.saveToken(token);
        this.router.navigate(['/']);
        this.snackBar.open('Logado com sucesso!', 'Fechar', {
          duration: 3000,
          panelClass: ['custom-snackbarLogin'],
        });
      },
      error: (err) => {
        console.error('Erro ao logar:', err);
        this.snackBar.open('Erro ao logar!', 'Fechar', {
          duration: 3000,
          panelClass: ['custom-snackbarLogin'],
        });
      },
    });
  }


}


