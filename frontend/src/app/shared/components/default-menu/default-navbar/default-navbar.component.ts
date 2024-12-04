import {Component, Input, Renderer2} from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";
import {DefaultMenuList} from "../../../types/default-menu-list";
import {NgClass, NgOptimizedImage} from "@angular/common";
import {MatIcon} from '@angular/material/icon';
import {DropdownModule} from 'primeng/dropdown';
import {SplitButtonModule} from 'primeng/splitbutton';
import {MenuItem} from 'primeng/api';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {MenuModule} from 'primeng/menu';

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
export class DefaultNavbarComponent {
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

  opcoesMenu: MenuItem[] = [
    { label: 'Perfil', icon: 'pi pi-user', command: () => this.abrirPerfil() },
    { label: 'Configurações', icon: 'pi pi-cog', command: () => this.abrirConfiguracoes() },
    { label: 'Sair', icon: 'pi pi-sign-out', command: () => this.sair() },
  ];

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
    console.log('Sair');
  }
}
