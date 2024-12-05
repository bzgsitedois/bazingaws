import {Component, inject, Input, OnInit, Renderer2} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {DefaultMenuList} from "../../../types/default-menu-list";
import {NgClass, NgOptimizedImage} from "@angular/common";
import {MatIcon} from '@angular/material/icon';
import {DropdownModule} from 'primeng/dropdown';
import {SplitButtonModule} from 'primeng/splitbutton';
import {MenuItem} from 'primeng/api';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {MenuModule} from 'primeng/menu';
import {TokenService} from '../../../../service/token/token.service';

@Component({
  selector: 'app-default-navbar',
  standalone: true,
  imports: [
    RouterLink,
    MatIcon,
    RouterLinkActive,
    NgClass,
    NgOptimizedImage,
    DropdownModule,
    SplitButtonModule,
    OverlayPanelModule,
    MenuModule
  ],
  templateUrl: './default-navbar.component.html',
  styleUrl: './default-navbar.component.scss'
})
export class DefaultNavbarComponent implements OnInit{
  private tokenService: TokenService = inject(TokenService);
  private router: Router = inject(Router)

  @Input() menuLists!: DefaultMenuList[];
  mode: 'light' | 'dark' = 'light';

  constructor(private renderer: Renderer2) {
    this.initializeTheme();
  }

  initializeTheme() {
    const prefersDarkScheme = window.matchMedia('(prefers-color-scheme: dark)').matches;

    if (prefersDarkScheme) {
      this.mode = 'dark';
      this.renderer.addClass(document.body, 'dark-mode');
      this.renderer.removeClass(document.body, 'light-mode');
    } else {
      this.mode = 'light';
      this.renderer.addClass(document.body, 'light-mode');
      this.renderer.removeClass(document.body, 'dark-mode');
    }
  }


  toggleBackground() {
    if (this.mode === 'light') {
      this.mode = 'dark';
      this.renderer.addClass(document.body, 'dark-mode');
      this.renderer.removeClass(document.body, 'light-mode');
    } else {
      this.mode = 'light';
      this.renderer.addClass(document.body, 'light-mode');
      this.renderer.removeClass(document.body, 'dark-mode');
    }
  }

  opcoesMenu: MenuItem[] = [];

  ngOnInit(): void {
    this.carregarOpcoesMenu();
  }

  carregarOpcoesMenu(): void {
    this.opcoesMenu = [
      { label: 'Perfil', icon: 'pi pi-user', command: () => this.abrirPerfil() },
      { label: 'Time', icon: 'pi pi-users', command: () => this.abrirConfiguracoes() },
      this.tokenService.getToken() === null
        ? { label: 'Logar', icon: 'pi pi-sign-in', command: () => this.logar() }
        : { label: 'Sair', icon: 'pi pi-sign-out', command: () => this.sair() }
    ];
  }


  toggleMenu(event: Event, menu: any) {
    menu.toggle(event);
  }

  abrirPerfil() {
    console.log('Abrir perfil');
  }

  abrirConfiguracoes() {
    console.log('Abrir configurações');
  }

  sair() {
    this.tokenService.clearToken();
    window.location.reload();
  }

  logar(){
    this.router.navigate(['bazinga/login']);
  }
}
